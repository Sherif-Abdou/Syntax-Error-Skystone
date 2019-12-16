package org.firstinspires.ftc.teamcode.modules.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.modules.shared.SkystoneRobot

@TeleOp(name = "Competition TeleOp")
class CompTeleOp: LinearOpMode() {
    lateinit var robot: SkystoneRobot
    override fun runOpMode() {
        robot = SkystoneRobot(hardwareMap, gamepad1, gamepad2, telemetry) {time -> sleep(time)}

        robot.initialize()

        waitForStart()

        while (opModeIsActive()) {
            robot.teleoploop()
        }
    }

}