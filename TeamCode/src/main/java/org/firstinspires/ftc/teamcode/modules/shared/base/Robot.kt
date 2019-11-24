package org.firstinspires.ftc.teamcode.modules.shared.base

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.teamcode.modules.autonomous.Mapable
import org.firstinspires.ftc.robotcore.external.Telemetry

abstract class Robot: Mapable {

    // Controller Modules
    abstract var DriveController: DriveController
    abstract var CVController: CVController?
    abstract var GyroController: GyroController?

    // Gamepads
    abstract var gamepad1: Gamepad
    abstract var gamepad2: Gamepad

    // Outputs
    abstract var leftMotors: MutableList<DcMotor>
    abstract var rightMotors: MutableList<DcMotor>
    abstract var servos: MutableMap<String, Servo>
    abstract var other_motors: MutableMap<String, DcMotor>

    // Sensors
    abstract var distanceSensors: MutableMap<String, DistanceSensor>
    abstract var colorSensors: MutableMap<String, ColorSensor>
    abstract var imu: BNO055IMU?

    // Hardware Map
    abstract var map: HardwareMap

    abstract var telemetry: Telemetry
    abstract var sleep: (Long)->Unit

    override var x: Float = -1.0f
    override var y: Float = -1.0f
    override var length: Float = -1.0f
    override var width: Float = -1.0f
}
