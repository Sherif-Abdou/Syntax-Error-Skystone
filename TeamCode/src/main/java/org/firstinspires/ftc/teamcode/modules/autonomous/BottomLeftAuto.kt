package org.firstinspires.ftc.teamcode.modules.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.modules.shared.HolonomicDrive
import org.firstinspires.ftc.teamcode.modules.shared.SkystoneRobot
import kotlin.math.abs

@Autonomous(name = "Blue Quarry")
@Disabled
class BottomLeftAuto: LinearOpMode() {
    lateinit var robot: SkystoneRobot
    lateinit var drive: HolonomicDrive
    override fun runOpMode() {
        initializeRobot()
        waitForStart()
        robot.crane.initialize()
        drive = robot.DriveController as HolonomicDrive
        scanForSkyStones()
    }

    private fun initializeRobot() {
        for (i in 0 until 4) {
            val motor = this.hardwareMap.get(DcMotor::class.java, "motor_$i")
            if (i % 2 == 0) {
                motor.direction = DcMotorSimple.Direction.REVERSE
                robot.leftMotors.add(motor)
            } else {
                robot.rightMotors.add(motor)
            }
        }
        robot.servos["claw"] = hardwareMap.get(Servo::class.java, "claw")
        robot.servos["counter"] = hardwareMap.get(Servo::class.java, "counter")
        robot.other_motors["bottom"] = hardwareMap.get(DcMotor::class.java, "bottom")
        robot.other_motors["bottom"]?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        robot.colorSensors["color"] = hardwareMap.colorSensor.get("color")
        robot.board.initializeMap(robot.distanceSensor.distance.first.toDouble(), 0.0)
    }

    fun scanForSkyStones() {
        var y = 39.0
        while (opModeIsActive()) {
            drive.moveToPoint(39.0, y)
            val red = abs(Constants.STONERED - robot.colorSensors["color"]!!.red())
            val green = abs(Constants.STONEGREEN - robot.colorSensors["color"]!!.green())
            val blue = abs(Constants.STONEBLUE - robot.colorSensors["color"]!!.blue())
            if (red > Constants.STONETHRESHOLD && green > Constants.STONETHRESHOLD && blue > Constants.STONETHRESHOLD) {
                break
            }
            y -= Constants.BRICK_WIDTH.toFloat()
        }
    }

    private fun grabStone() {
        //TODO: Figure out how robot grabs Skystone
    }

    private fun releaseStone() {
        //TODO: Figure out how robot releases Skystone
    }
}