package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.hardware.bosch.BNO055IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation
import org.firstinspires.ftc.teamcode.modules.shared.base.GyroController
import org.firstinspires.ftc.teamcode.modules.shared.base.Robot

public class Gyro(public override val robot: Robot) : GyroController {
    private var imu: BNO055IMU? = null
    private var lastAngle = Orientation()
    private var globalAngle = 0.0

    fun initialize() {
        val parameters = BNO055IMU.Parameters()

        parameters.mode = BNO055IMU.SensorMode.IMU
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC
        parameters.loggingEnabled = false

        imu = robot.imu
        imu?.initialize(parameters)
    }

    val angle: Double?
        get() {
            if (imu == null) return null
            val orientation = imu!!.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES)

            val delta_angle = orientation.firstAngle - lastAngle.firstAngle

            if (globalAngle < -180.0) {
                globalAngle+= 360
            } else if (globalAngle> 180.0) {
                globalAngle-= 360
            }

            globalAngle += delta_angle
            lastAngle = orientation

            return globalAngle
        }

    fun telemetry() {
        robot.telemetry.addData("Angle", angle)
    }
}