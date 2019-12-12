package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.modules.shared.base.Robot

class CWheels(val robot: Robot) {
    var wheels: MutableMap<String, DcMotor?> = mutableMapOf()
    private var running = false
    fun initialize() {
        val right_compliant = robot.other_motors["rc"]
        val left_compliant = robot.other_motors["lc"]
        wheels["lc"] = left_compliant
        wheels["rc"] = right_compliant
    }

    fun runByController() {
        if (robot.gamepad1.y) {
            running = !running
            robot.sleep(400L)
        }
        if (running) {
            wheels["lc"]?.power = -0.25
            wheels["rc"]?.power = 0.25
        } else {
            wheels["lc"]?.power = 0.0
            wheels["rc"]?.power = 0.0
        }
    }

    companion object {
        const val DURATION = 3000
        const val STEPS = 20
    }
}