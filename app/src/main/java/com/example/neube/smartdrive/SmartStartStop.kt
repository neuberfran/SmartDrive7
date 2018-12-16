package com.example.neube.smartdrive

import android.content.ContentValues.TAG
import android.graphics.Path
import android.util.Log
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager


class SmartStartStop(i2cName: String, i2cAddress: Int) : AutoCloseable {

    companion object {

        private val TAG = SmartStartStop::class.java.simpleName!!

        private const val SmartDrive_ADDRESS = 0x1B
        private  const val SmartDrive_VOLTAGE_MULTIPLIER = 212.7
        private  const val SmartDrive_PPR = 360         // PPR - pulses per rotation

        private const val SmartDrive_COMMAND   =  0x41

        // Supported I2C commands
        private const val CMD_R = 0x52
        private const val CMD_S = 0x53

    }

    private var device: I2cDevice? = null

    init {

        device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

        Log.i(SmartStartStop.TAG, "Volto 39 $device")

    }

    override fun close() {
        device?.close().also { device = null }
    }

    fun start() = device?.writeRegByte(SmartDrive_COMMAND, CMD_R.toByte())

    fun stop() = device?.writeRegByte(SmartDrive_COMMAND, CMD_R.toByte())

}