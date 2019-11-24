package org.firstinspires.ftc.teamcode.modules.shared

import com.qualcomm.robotcore.hardware.*
import kotlin.experimental.and

class UltraSonicSensor(map: HardwareMap, name: String) {
    private val device: I2cDevice = map.i2cDevice.get(name)
    private val reader: I2cDeviceSynch
    init {
        reader = I2cDeviceSynchImpl(device, ADDRESS, false)
        reader.engage()
    }

    val distance: Pair<Byte, Byte>
        get() {
            val cache = reader.read(REG_START, READ_LENGTH)
            return Pair(cache[0] and READ_AND.toByte(), cache[1] and READ_AND.toByte())
        }

    companion object {
        val ADDRESS = I2cAddr(0x14)
        const val REG_START = 0x04
        const val READ_LENGTH = 2
        const val READ_AND = 0xFF
    }
}