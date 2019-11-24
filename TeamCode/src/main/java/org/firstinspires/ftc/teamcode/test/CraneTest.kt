package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.modules.shared.SkystoneRobot

@TeleOp(name = "Crane Test")
@Disabled
class CraneTest: LinearOpMode() {
    override fun runOpMode() {
        val sleep_lambda: (Long)->Unit = {time -> sleep(time)}
        val robot = SkystoneRobot(hardwareMap, gamepad1, gamepad2, this.telemetry, sleep_lambda)
        robot.servos["claw"] = hardwareMap.get(Servo::class.java, "claw")
        robot.servos["counter"] = hardwareMap.get(Servo::class.java, "counter")
        robot.other_motors["bottom"] = hardwareMap.get(DcMotor::class.java, "bottom")
        robot.other_motors["bottom"]?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        robot.crane.initialize()
        waitForStart()

        while (opModeIsActive()) {
            robot.crane.runByController()
            telemetry.addData("Servo", robot.servos["claw"] ?: "wtf it doesn't exist")
            telemetry.update()
            sleep(50)

        }
    }
}