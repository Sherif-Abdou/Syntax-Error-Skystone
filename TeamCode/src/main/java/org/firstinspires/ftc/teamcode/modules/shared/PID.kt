package org.firstinspires.ftc.teamcode.modules.shared

class PID(var P: Double = 0.0, var I: Double = 0.0, var D: Double = 0.0) {
    private var last_time: Double = 0.0
    private var previous_err: Double = 0.0
    private var i = 0.0

    var i_limit = 1.0

    fun loop(current_time: Double, desired_position: Double, current_position: Double): Double {
        val current_err = desired_position - current_position

        val p = P * current_err

        i += I * (current_err * (current_time - last_time))

        if (i > i_limit) {
            i = i_limit
        } else if (i < -i_limit) {
            i = -i_limit
        }

        val d = D * (current_err-previous_err)/(current_time-last_time)
        val output = p + i + d

        previous_err = current_err
        last_time = current_time

        return output
    }
}