package org.firstinspires.ftc.teamcode.modules.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.modules.shared.HolonomicDrive
import org.firstinspires.ftc.teamcode.modules.shared.SkystoneRobot
import org.firstinspires.ftc.teamcode.modules.shared.base.Direction

@Autonomous(name="SimpleMoveLeft")
class SimpleMoveBlue: LinearOpMode() {
    lateinit var robot: SkystoneRobot
    override fun runOpMode() {
        robot = SkystoneRobot(hardwareMap, gamepad1, gamepad2, telemetry) {time -> sleep(time)}

        initializeMotors()
        initializeServos()

        robot.crane.initialize()
        robot.cWheels.initialize()

        waitForStart()
        robot.DriveController.driveByDistance(1000, Direction.NORTH)
        robot.DriveController.driveByDistance(1000, Direction.SOUTH)
        (robot.DriveController as HolonomicDrive).brake()
    }

    private fun initializeMotors() {
        // Maps hardware
        for (i in 0 until 4) {
            val motor = hardwareMap.dcMotor.get("motor_$i")
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            if (i % 2 == 0 && i !=0) {
                motor.direction = DcMotorSimple.Direction.REVERSE
                robot.rightMotors.add(motor)
            } else if (i == 0) {
                robot.rightMotors.add(motor)
            } else {
                robot.leftMotors.add(motor)
            }
        }
//        robot.other_motors["bottom"] = hardwareMap.dcMotor.get("bottom")
//        robot.other_motors["bottom"]?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
//        robot.other_motors["rc"] = hardwareMap.dcMotor.get("rc")
//        robot.other_motors["lc"] = hardwareMap.dcMotor.get("lc")
    }

    private fun initializeServos() {
//        robot.servos["claw"] = hardwareMap.servo.get("claw")
//        robot.servos["counter"] = hardwareMap.servo.get("counter")
    }
}