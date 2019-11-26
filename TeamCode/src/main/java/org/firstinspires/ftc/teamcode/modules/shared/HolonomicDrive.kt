package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.modules.shared.base.Direction
import org.firstinspires.ftc.teamcode.modules.shared.base.Direction.*
import org.firstinspires.ftc.teamcode.modules.shared.base.DriveController
import org.firstinspires.ftc.teamcode.modules.shared.base.Robot
import kotlin.math.roundToInt

/** A drive component for a holonomic drivetrain
 * @param robot The robot class the controller is controlling
 */
class HolonomicDrive(override val robot: Robot) : DriveController {
    /** Base Drive Speeed */
    var driveSpeed: Double = 0.5

    override fun driveByTime(seconds: Long, direction: Direction) {
        when (direction) {
            NORTH -> drive(driveSpeed, 0.0, 0.0)
            SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            EAST -> drive(0.0, 0.0,-driveSpeed)
            WEST -> drive(0.0,0.0, driveSpeed)
        }
        // Pause program execution for the amount of seconds to drive
        robot.sleep(seconds)

        brake()
    }

    override fun driveByDistance(inches: Int, direction: Direction) {
        var fLeftEncoder = inches * (UNITSPERREVOLUTION*(WHEELDIAMETER*Math.PI) * GEARRATIO)

        // Reverses the direction if motor goes reverse
        if (direction == SOUTH ||direction == EAST) {
            fLeftEncoder *= -1
        }

        robot.leftMotors[Robot.FRONT].mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        robot.leftMotors[Robot.FRONT].mode = DcMotor.RunMode.RUN_TO_POSITION
        robot.leftMotors[Robot.FRONT].targetPosition = fLeftEncoder.roundToInt()

        when (direction) {
            NORTH -> drive(driveSpeed, 0.0, 0.0)
            SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            EAST -> drive(0.0, 0.0,-driveSpeed)
            WEST -> drive(0.0,0.0, driveSpeed)
        }

        while (robot.leftMotors[Robot.FRONT].isBusy) {
            robot.telemetry.addData("Encoder Position", robot.leftMotors[Robot.FRONT].currentPosition)
        }

        brake()

        robot.leftMotors[Robot.FRONT].mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    override fun driveByController() {
        val drive = -robot.gamepad1.left_stick_y.toDouble() * MULTIPLIER
        val strafe = robot.gamepad1.right_stick_x.toDouble() * MULTIPLIER
        var turn = 0.0
        if (robot.gamepad1.right_bumper) {
            turn -= 1.0 * MULTIPLIER
        }
        if (robot.gamepad1.left_bumper) {
           turn += 1.0 * MULTIPLIER
        }

        if (robot.gamepad1.dpad_up) {
            driveSpeed += 0.1
        }

        if (robot.gamepad1.dpad_down) {
            driveSpeed -= 0.1
        }

        // Checks if the main joystick is being moved, if not check strafe nxt
        if (drive != 0.0 || turn != 0.0 || strafe != 0.0) {
            drive(drive, turn, strafe)
        } else {
            brake()
        }
    }

    private fun drive(drive: Double, turn: Double, strafe: Double) {
        robot.leftMotors[Robot.FRONT].power = Range.clip(drive+turn+strafe, -1.0, 1.0)
        robot.leftMotors[Robot.BACK].power = Range.clip(drive-turn+strafe, -1.0, 1.0)
        robot.rightMotors[Robot.FRONT].power = Range.clip(drive-turn-strafe, -1.0, 1.0)
        robot.rightMotors[Robot.BACK].power = Range.clip(drive+turn-strafe, -1.0, 1.0)
    }

    fun brake() {
        // Sets all motors to 0 power
        for (motor in robot.leftMotors + robot.rightMotors) {
            motor.power = 0.0
        }
    }

    companion object {
        // Diameter of Mecanum wheels
        const val WHEELDIAMETER = 3.93701
        const val UNITSPERREVOLUTION = 134.4
        const val GEARRATIO = 1
        const val MULTIPLIER = 0.5
    }
}
