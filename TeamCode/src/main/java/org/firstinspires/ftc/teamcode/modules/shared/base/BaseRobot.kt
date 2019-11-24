package org.firstinspires.ftc.teamcode.modules.shared.base

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.modules.shared.HolonomicDrive

open class BaseRobot(override var map: HardwareMap, override var gamepad1: Gamepad, override var gamepad2: Gamepad, override var telemetry: Telemetry, override var sleep: (Long) -> Unit) : Robot() {
    override var x: Float = -1.0f
    override var y: Float = -1.0f
    override var width: Float = -1.0f
    override var length: Float = -1.0f
    override var DriveController: DriveController = HolonomicDrive(this)
    override var CVController: CVController? = null
    override var GyroController: GyroController? = null
    override var leftMotors: MutableList<DcMotor> = mutableListOf()
    override var rightMotors: MutableList<DcMotor> = mutableListOf()
    override var servos: MutableMap<String, Servo> = mutableMapOf()
    override var distanceSensors: MutableMap<String, DistanceSensor> = mutableMapOf()
    override var colorSensors: MutableMap<String, ColorSensor> = mutableMapOf()
    override var imu: BNO055IMU? = null
    override var other_motors: MutableMap<String, DcMotor> = mutableMapOf()
}
