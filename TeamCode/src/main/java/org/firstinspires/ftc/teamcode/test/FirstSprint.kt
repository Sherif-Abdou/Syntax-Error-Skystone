package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.modules.shared.base.BaseRobot

@TeleOp(name = "Sprint #1")
@Disabled
class FirstSprint: LinearOpMode() {
    private lateinit var robot: BaseRobot

    override fun runOpMode() {
        // Base SkystoneRobot
        val sleep_lambda: (Long)->Unit = {time -> sleep(time)}
        robot = BaseRobot(hardwareMap, gamepad1, gamepad2, telemetry, sleep_lambda)

        initializeMotors()

        waitForStart()

        while (opModeIsActive()) {
            robot.DriveController.driveByController()
        }
    }

    private fun initializeMotors() {
        for (i in 0 until 4) {
            val motor = hardwareMap.dcMotor.get("motor_$i")
            if (i % 2 == 0) {
                motor.direction = DcMotorSimple.Direction.REVERSE
                robot.rightMotors.add(motor)
            } else {
                robot.leftMotors.add(motor)
            }
        }
    }
}