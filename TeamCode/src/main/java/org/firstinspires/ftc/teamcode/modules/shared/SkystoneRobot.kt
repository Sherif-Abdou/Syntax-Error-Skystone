package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.modules.autonomous.Map
import org.firstinspires.ftc.teamcode.modules.autonomous.Starts

class SkystoneRobot(val map: HardwareMap, val gamepad1: Gamepad, val gamepad2: Gamepad, val telemetry: Telemetry, val sleep: (Long) -> Unit) {
    var y: Float = 0f
    var x: Float = 0f
    var imu: BNO055IMU? = null
    val rightMotors: MutableList<DcMotor> = mutableListOf()
    var DriveController: HolonomicDrive
    var leftMotors: MutableList<DcMotor> = mutableListOf()
    lateinit var distanceSensor: UltraSonicSensor
    val board = Map(Starts.BLUEBOTTOM, this)
    val gyro = Gyro(this)

    init {
        this.DriveController = HolonomicDrive(this, this.gyro)
    }

    fun initialize() {
        initializeMotors()
        initializeServos()

        this.gyro.initialize()
    }

    fun teleoploop() {
        DriveController.driveByController()
        gyro.telemetry()
        telemetry.update()
        sleep(20)
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