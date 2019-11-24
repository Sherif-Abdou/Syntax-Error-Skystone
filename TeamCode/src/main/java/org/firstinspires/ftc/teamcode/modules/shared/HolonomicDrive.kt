package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.modules.shared.base.Direction
import org.firstinspires.ftc.teamcode.modules.shared.base.DriveController
import org.firstinspires.ftc.teamcode.modules.shared.base.Robot
/** A drive component for a holonomic drivetrain
 * @param robot The robot class the controller is controlling
 */
class HolonomicDrive(override val robot: Robot) : DriveController {
    /** Base Drive Speeed */
    var driveSpeed: Double = 0.5
    
    override fun driveByTime(seconds: Long, direction: Direction) {
        when (direction) {
            Direction.NORTH -> drive(driveSpeed, 0.0, 0.0)
            Direction.SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            Direction.EAST -> drive(0.0, 0.0,-driveSpeed)
            Direction.WEST -> drive(0.0,0.0, driveSpeed)
        }
        // Pause program execution for the amount of seconds to drive
        robot.sleep(seconds)

        brake()
    }

    override fun driveByDistance(feet: Double, direction: Direction) {
        var distance_travelled = 0.0

        // Resets the encoder values to 0
        for (motor in robot.leftMotors + robot.rightMotors) {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }

        when (direction) {
            Direction.NORTH -> drive(driveSpeed, 0.0, 0.0)
            Direction.SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            Direction.EAST -> drive(0.0, 0.0,-driveSpeed)
            Direction.WEST -> drive(0.0,0.0, driveSpeed)
        }

        // Keep updating the distance travelled until the robot has moved by x amount of feet
        while (distance_travelled <= feet) {
            // Collects the average encoder position of the motors
            var average = 0.0
            for (motor in robot.leftMotors + robot.rightMotors) {
                average += ((kotlin.math.abs(motor.currentPosition) / UNITSPERREVOLUTION) * (WHEELDIAMETER * Math.PI)) / 4
            }
            // Sets the distance the robot has travelled to the average
            distance_travelled = average
        }
    }

    override fun driveByController() {
        val strafe = robot.gamepad1.left_stick_y.toDouble() * MULTIPLIER
        val drive =  -robot.gamepad1.right_stick_x.toDouble() * MULTIPLIER
        var turn = 0.0
        if (robot.gamepad1.right_bumper) {
            turn -= 1.0 * MULTIPLIER
        }
        if (robot.gamepad1.left_bumper) {
           turn += 1.0 * MULTIPLIER
        }

        // Checks if the main joystick is being moved, if not check strafe nxt
        if (drive != 0.0 || turn != 0.0 || strafe != 0.0) {
            drive(drive, turn, strafe)
        } else {
            brake()
        }
    }

    private fun drive(drive: Double, turn: Double, strafe: Double) {
        robot.leftMotors[1].power = Range.clip(drive+turn-strafe, -1.0, 1.0)
        robot.leftMotors[0].power = Range.clip(drive+turn+strafe, -1.0, 1.0)
        robot.rightMotors[0].power = Range.clip(drive-turn-strafe, -1.0, 1.0)
        robot.rightMotors[1].power = Range.clip(drive-turn+strafe, -1.0, 1.0)
    }

    fun brake() {
        // Sets all motors to 0 power
        for (motor in robot.leftMotors + robot.rightMotors) {
            motor.power = 0.0
        }
    }

    companion object {
        // Diameter of Mecanum wheels
        const val WHEELDIAMETER = 3.5
        const val UNITSPERREVOLUTION = 1440
        const val MULTIPLIER = 0.5
    }
}
