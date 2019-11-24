package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.modules.autonomous.Map
import org.firstinspires.ftc.teamcode.modules.autonomous.Starts
import org.firstinspires.ftc.teamcode.modules.shared.base.BaseRobot
import org.firstinspires.ftc.teamcode.modules.shared.base.DriveController

class SkystoneRobot(map: HardwareMap, gamepad1: Gamepad, gamepad2: Gamepad, telemetry: Telemetry, sleep: (Long)->Unit) : BaseRobot(map, gamepad1, gamepad2, telemetry, sleep) {
    val crane = Crane(this)
    val cWheels = CWheels(this)
    lateinit var distanceSensor: UltraSonicSensor
    val board = Map(Starts.BLUEBOTTOM, this)

    init {
        this.DriveController = HolonomicDrive(this)
    }

    fun teleoploop() {
        DriveController.driveByController()
        crane.runByController()
        cWheels.runByController()
    }
}