package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.robot.Robot
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.Range
import kotlin.math.abs
import kotlin.math.roundToInt

/** A drive component for a holonomic drivetrain
 * @param robot The robot class the controller is controlling
 */
enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}


class HolonomicDrive(val robot: SkystoneRobot, val gyro: Gyro?) {
    /** Base Drive Speeed */
    var driveSpeed: Double = 0.5

    private var timeout = ElapsedTime()
    private var stable_timeout = ElapsedTime()
    private var base_angle = 0.0
    val pid = PID(.015, .000007, 0.5)

    fun driveByTime(seconds: Long, direction: Direction) {
        when (direction) {
            Direction.NORTH -> drive(driveSpeed, 0.0, 0.0)
            Direction.SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            Direction.EAST -> drive(0.0, 0.0, -driveSpeed)
            Direction.WEST -> drive(0.0, 0.0, driveSpeed)
        }
        // Pause program execution for the amount of seconds to drive
        robot.sleep(seconds)

        brake()
    }

    fun driveByDistance(inches: Int, direction: Direction) {
        var fLeftEncoder = (inches * UNITSPERREVOLUTION) / (Math.PI * WHEELDIAMETER)

        // Reverses the direction if motor goes reverse
        if (direction == Direction.NORTH || direction == Direction.WEST) {
            fLeftEncoder *= -1
        }

        robot.leftMotors[Constants.FRONT].mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        robot.leftMotors[Constants.FRONT].targetPosition = fLeftEncoder.roundToInt()
        robot.leftMotors[Constants.FRONT].mode = DcMotor.RunMode.RUN_TO_POSITION

        when (direction) {
            Direction.NORTH -> drive(driveSpeed, 0.0, 0.0)
            Direction.SOUTH -> drive(-driveSpeed, 0.0, 0.0)
            Direction.EAST -> drive(0.0, 0.0, -driveSpeed)
            Direction.WEST -> drive(0.0, 0.0, driveSpeed)
        }

        while (robot.leftMotors[Constants.FRONT].isBusy
                && abs(robot.leftMotors[Constants.FRONT].targetPosition) >= abs(robot.leftMotors[Constants.FRONT].currentPosition)) {
            telemetry()
            robot.telemetry.update()
        }

        brake()

        robot.leftMotors[Constants.FRONT].mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    fun telemetry() {
        robot.telemetry.addData("Encoder Position", robot.leftMotors[Constants.FRONT].currentPosition)
        robot.telemetry.addData("Target", robot.leftMotors[Constants.FRONT].targetPosition)
        robot.telemetry.addData("Power", driveSpeed)
        robot.telemetry.addData("LF", "${robot.leftMotors[Constants.FRONT].power}")
        robot.telemetry.addData("LB", "${robot.leftMotors[Constants.BACK].power}")
        robot.telemetry.addData("RF", "${robot.rightMotors[Constants.FRONT].power}")
        robot.telemetry.addData("RB", "${robot.rightMotors[Constants.BACK].power}")
    }

    fun driveByController() {
        val drive = -robot.gamepad1.left_stick_y.toDouble() * driveSpeed
        val strafe = robot.gamepad1.right_stick_x.toDouble() * driveSpeed
        var turn = 0.0

        if (timeout.milliseconds() >= 250) {
            if (robot.gamepad1.dpad_up) {
                driveSpeed += 0.1
                timeout.reset()
            }

            if (robot.gamepad1.dpad_down) {
                driveSpeed -= 0.1
                timeout.reset()
            }

            if (robot.gamepad1.right_trigger == 1f) {
                val new_angle = gyro!!.angle!! - 45
                DriveToRotation(new_angle ,gyro) { true }
                base_angle  = new_angle
                timeout.reset()
            }

            if (robot.gamepad1.left_trigger == 1f) {
                val new_angle = gyro!!.angle!! + 45
                DriveToRotation(new_angle , gyro) { true }
                base_angle  = new_angle
                timeout.reset()
            }

            if (robot.gamepad1.y) {
                val nearest = (gyro!!.angle!! / 45.0).roundToInt() * 45.0
                DriveToRotation(nearest, gyro) { true }
                base_angle = nearest
                timeout.reset()
            }
        }

        if (robot.gamepad1.right_bumper) {
            turn -= 1.0 * driveSpeed
        }

        if (robot.gamepad1.left_bumper) {
            turn += 1.0 * driveSpeed
        }

        var pure_turn = true

        if (turn == 0.0 && stable_timeout.milliseconds() >= 500) {
            turn += pid.loop(20.0, base_angle, gyro!!.angle!!)
            pure_turn = false
        }

        robot.telemetry.addData("Turn", turn)

        // Checks if the main joystick is being moved, if not check strafe nxt
        if (drive != 0.0 || turn != 0.0 || strafe != 0.0) {
            drive(drive, turn, strafe)
            if (pure_turn) {
                stable_timeout.reset()
            }
        } else {
            brake()
            base_angle = gyro!!.angle!!
        }


        telemetry()
    }

    fun drive(drive: Double, turn: Double, strafe: Double) {
        robot.leftMotors[Constants.FRONT].power = Range.clip(drive + strafe + turn, -1.0, 1.0)
        robot.rightMotors[Constants.FRONT].power = Range.clip(drive - strafe - turn, -1.0, 1.0)
        robot.leftMotors[Constants.BACK].power = Range.clip(drive - strafe + turn, -1.0, 1.0)
        robot.rightMotors[Constants.BACK].power = Range.clip(drive + strafe - turn, -1.0, 1.0)
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
    }
}
