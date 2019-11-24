package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.Range

@TeleOp(name = "TestTeleOp")
@Disabled
class TestTeleOp : LinearOpMode() {
    var left_motors: MutableList<DcMotor> = mutableListOf()
    var right_motors: MutableList<DcMotor> = mutableListOf()

    override fun runOpMode() {
        for (i in 0 until 4) {
            var motor = hardwareMap.get("motor_$i") as DcMotor
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            if (i % 2 == 0) {
                motor.direction = DcMotorSimple.Direction.REVERSE
                right_motors.add(motor)
            } else {
                left_motors.add(motor)
            }
        }

        waitForStart()

        while (opModeIsActive()) {
            var leftPower: Double = 0.0
            var rightPower: Double = 0.0

            var turn = gamepad1.left_stick_x
            var drive = -gamepad1.left_stick_y

            var strafe = gamepad1.right_stick_x.toDouble()

            rightPower = Range.clip(drive - turn, -1.0f, 1.0f).toDouble()
            leftPower = Range.clip(drive + turn, -1.0f, 1.0f).toDouble()
            if (rightPower != 0.0 && leftPower != 0.0) {
                for (motor in right_motors) {
                    motor.power = rightPower
                }

                for (motor in left_motors) {
                    motor.power = leftPower
                }
            } else if (strafe != 0.0) {
                left_motors[1].power = strafe
                left_motors[0].power = -strafe
                right_motors[0].power = strafe
                right_motors[1].power = -strafe
            } else {
                for (motor in right_motors) {
                    motor.power = 0.0
                }

                for (motor in left_motors) {
                    motor.power = 0.0
                }
            }
            telemetry.addData("Oui Oui im in me mum car", "Left: $leftPower Right: $rightPower")
            telemetry.update()
        }
    }
}
