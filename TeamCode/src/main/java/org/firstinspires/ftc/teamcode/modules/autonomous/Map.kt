package org.firstinspires.ftc.teamcode.modules.autonomous

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.modules.shared.HolonomicDrive
import org.firstinspires.ftc.teamcode.modules.shared.base.Direction
import org.firstinspires.ftc.teamcode.modules.shared.base.Robot


enum class Starts {
    BLUETOP, BLUEBOTTOM, REDTOP, REDBOTTOM
}

// The robot's map of the game feild
class Map(private val start: Starts, val robot: Robot) {
    fun initializeMap(side: Double, back: Double) {
        when (start) {
            Starts.BLUEBOTTOM -> {
                robot.x = back.toFloat()
                robot.y = side.toFloat()
            }
            Starts.BLUETOP -> {
                robot.x = side.toFloat()
                robot.y = 144-back.toFloat()
            }
            Starts.REDTOP -> {
                robot.x = 144-back.toFloat()
                robot.y = 144-side.toFloat()
            }
            Starts.REDBOTTOM -> {
                robot.x = 144-side.toFloat()
                robot.y = back.toFloat()
            }
        }
    }
}

// Moves to a location(in inches)
fun HolonomicDrive.moveToPoint(x: Double, y: Double) {
    val delta_x = x - robot.x
    val delta_y = y - robot.y
    driveByDistance(delta_x.toInt(), Direction.WEST)
    driveByDistance(delta_y.toInt(), Direction.NORTH)
    robot.x = x.toFloat()
    robot.y = y.toFloat()
}