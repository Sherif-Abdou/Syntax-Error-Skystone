package org.firstinspires.ftc.teamcode.modules.shared

import kotlin.math.abs

class PID(var P: Double = 0.0, var I: Double = 0.0, var D: Double = 0.0) {
    private var last_time: Double = 0.0
    private var previous_err: Double = 0.0
    private var i = 0.0

    var i_limit = 1.0

    fun loop(delta_time: Double, desired_position: Double, current_position: Double): Double {
        val current_err = desired_position - current_position

        val p = P * current_err

        i += I * (current_err * (delta_time))

        if (i > i_limit) {
            i = i_limit
        } else if (i < -i_limit) {
            i = -i_limit
        }

        val d = D * (current_err - previous_err) / (delta_time)
        val output = p + i + d

        previous_err = current_err


        return output
    }


}

fun HolonomicDrive.DriveToRotation(rotation: Double, gyro: Gyro, running: ()-> Boolean) {
    val pidDrive = PID(.015, .000007, 0.5)
    var t = 0
    var power = 0.0
    do {
        power = pidDrive.loop(20.0, rotation, gyro.angle!!)
        this.drive(0.0, power, 0.0)
        robot.telemetry.addData("pid power", power)
        robot.telemetry.addData("time", t)
        gyro.telemetry()
        telemetry()
        robot.telemetry.update()
        robot.sleep(20)
        t+=20
    } while (running() && abs(power)>=0.03)

    brake()
}
