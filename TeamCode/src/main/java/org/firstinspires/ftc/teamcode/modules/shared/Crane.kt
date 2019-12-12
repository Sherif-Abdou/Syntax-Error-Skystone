package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.modules.shared.base.BaseRobot

class Crane(val robot: BaseRobot) {
    private var servos: MutableMap<String, Servo?> = mutableMapOf()
    private var motor: DcMotor? = null

    fun initialize() {
        this.servos["claw"] = robot.servos["claw"]
        this.servos["counter"] = robot.servos["counter"]
        this.motor = robot.other_motors["bottom"]
        this.servos["claw"]?.position = 0.0
        this.servos["counter"]?.position = CRANEUPPERBOUND
    }

    fun grab() {
        val currentPosition = servos["claw"]?.position
        when (currentPosition) {
            0.0 -> servos["claw"]?.position = 0.25
            null -> this.servos["claw"]!!.position = 0.0
            else -> servos["claw"]?.position = 0.0
        }
        robot.sleep(300L)
    }

    fun counter() {
        val currentPosition = servos["counter"]?.position
        if (currentPosition == CRANEUPPERBOUND) {
            servos["counter"]?.position = CRANELOWERBOUND
        } else {
            servos["counter"]?.position = CRANEUPPERBOUND
        }
        robot.sleep(300L)
    }

    fun runByController() {
        val rotation = robot.gamepad2.left_stick_x
        this.motor?.power = rotation.toDouble() / 2.5
         if (robot.gamepad2.a) {
            grab()
        }
        if (robot.gamepad2.y) {
            counter()
        } else {
            var power = 0.0
            if (robot.gamepad2.dpad_left) {
                power += 0.1
            }
            if (robot.gamepad2.dpad_right) {
                power -= 0.1
            }
            servos["counter"]?.position = servos["counter"]?.position!! + power
        }
    }

    companion object {
        const val MOTORTOCOUNTERRATIO = 0.1f
        const val CRANELOWERBOUND = 0.0
        const val CRANEUPPERBOUND = 1.0
    }
}