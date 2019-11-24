package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.modules.shared.SkystoneRobot

@TeleOp(name="CWheelsTest")
@Disabled
class CWheelsTest: LinearOpMode() {
    override fun runOpMode() {
        val robot = SkystoneRobot(this.hardwareMap, gamepad1, gamepad2, telemetry) { time -> this.sleep(time)}
        robot.cWheels.initialize()

        waitForStart()

        while (opModeIsActive()) {
            robot.cWheels.runByController()
        }
    }
}