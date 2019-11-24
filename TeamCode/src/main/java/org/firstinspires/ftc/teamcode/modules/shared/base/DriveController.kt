package org.firstinspires.ftc.teamcode.modules.shared.base

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

interface DriveController {
    val robot: Robot
    /**
     * Moves the robot in a certain direction for a certain period of time.
     *
     * @param seconds The amount of time in seconds the robot should move
     * @param direction The direction the robot will move in
     */
    fun driveByTime(seconds: Long, direction: Direction)
    /** Moves the robot by a certain distance in a certain direction
     * @param inches The number of inches the robot should move in
     * @param direction The direction for the robot to move in
     */
    fun driveByDistance(inches: Int, direction: Direction)
    /** Moves the robot based on the attached controller
     */
    fun driveByController()
}
