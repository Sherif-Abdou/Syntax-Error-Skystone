package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.modules.autonomous.Map
import org.firstinspires.ftc.teamcode.modules.autonomous.Starts
import org.firstinspires.ftc.teamcode.modules.shared.base.BaseRobot

class SkystoneRobot(map: HardwareMap, gamepad1: Gamepad, gamepad2: Gamepad, telemetry: Telemetry, sleep: (Long)->Unit) : BaseRobot(map, gamepad1, gamepad2, telemetry, sleep) {
    val crane = Crane(this)
    val cWheels = CWheels(this)
    lateinit var distanceSensor: UltraSonicSensor
    val board = Map(Starts.BLUEBOTTOM, this)
    val gyro = Gyro(this)

    init {
        this.DriveController = HolonomicDrive(this, this.gyro)
    }

    fun initialize() {
        initializeMotors()
        initializeServos()

        this.crane.initialize()
        this.cWheels.initialize()
        this.gyro.initialize()
    }

    fun teleoploop() {
        DriveController.driveByController()
        crane.runByController()
        cWheels.runByController()
        gyro.telemetry()
        telemetry.update()
    }

    private fun initializeMotors() {
        // Maps hardware
        for (i in 0 until 4) {
            val motor = this.map.dcMotor.get("motor_$i")
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            if (i == 0) {
                motor.direction = DcMotorSimple.Direction.REVERSE
            }

            if (i % 2 == 0) {
                this.rightMotors.add(motor)
            } else {
                this.leftMotors.add(motor)
            }
        }
        this.imu = this.map.get(BNO055IMU::class.java, "imu")
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