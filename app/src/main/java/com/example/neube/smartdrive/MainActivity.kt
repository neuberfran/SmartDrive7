package com.example.neube.smartdrive

import android.app.Activity
import android.content.ContentValues.TAG
import com.example.neube.smartdrive.SmartDriveDriver
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager
import com.google.firebase.database.*
import java.io.IOException
import java.security.KeyStore
import java.util.*
import kotlin.jvm.java

class MainActivity : Activity() {

    private var mButtonInputDriver: ButtonInputDriver? = null

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!

        private const val I2C_PIN_NAME = "I2C1"
        private const val I2C_ADDRESS_SMARTDRIVE = 0x1B
        private const val SmartDrive_COMMAND   =  0x41

        // Supported I2C commands
        private const val CMD_R = 0x52
        private const val CMD_S = 0x53

        var database = FirebaseDatabase.getInstance()

        var mDatabase = database.reference

        private var mLedGpio: Gpio? = null
  //      public var mButtonInputDriver: ButtonInputDriver? = null

        public var FCMotorDoisA: Button? = null
        public var FCMotorDoisB: Button? = null
        public var FCMotorUmA: Button? = null
        public var FCMotorUmB: Button? = null
        public lateinit var buttonInputDriver: ButtonInputDriver

    }

    private var Smart: SmartStartStop? = null
    //  private var Smartb: SmartFimDeCurso? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanI2cDevices()

        Log.i(TAG, "Volto 43.0 passei aki")

        Smart = SmartStartStop(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

        Log.i(TAG, "Volto 43.1 passei aki")

//        var pioService = PeripheralManager.getInstance()
//        var portList = pioService.gpioList
//
//        mLedGpio = pioService.openGpio(BoardDefaults.getGPIOForButton15())
//        mLedGpio!!.setDirection(Gpio.DIRECTION_IN)
//        mLedGpio!!.setActiveType(Gpio.ACTIVE_LOW);

        getDataInit()

        Log.i(TAG, "Volto 43.3 passei aki")
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {

        var mDatabase = FirebaseDatabase.getInstance()
        var fcmotordoisa = mDatabase.getReference("fcmotordoisa")
        var fcmotordoisb = mDatabase.getReference("fcmotordoisb")

        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            // Turn off the LED
            Log.i(TAG, "Volto 44.22......")

            fcmotordoisa.setValue(4)
            //     setLedValue(false)

            return true

            Log.i(TAG, "Volto 44.33......")

        }

        return super.onKeyUp(keyCode, event)
    }

    // Clean

    @Throws(IOException::class)
    fun CleanRegisters(device: I2cDevice, address: Int) {

        device.writeRegByte(SmartDrive_COMMAND, CMD_S.toByte())
        //    device.writeRegByte(address, limpa)
    }

    public fun getDataInit() {

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.i(TAG, "Configuring GPIO pins")

                Log.i(TAG, "Configuring GPIO pins")
//
//                var pioService = PeripheralManager.getInstance()
//                var portList = pioService.gpioList
//
//                mLedGpio = pioService.openGpio(BoardDefaults.getGPIOForButton15())
//                mLedGpio!!.setDirection(Gpio.DIRECTION_IN)
//                mLedGpio!!.setActiveType(Gpio.ACTIVE_LOW);
//
                var mDatabase = FirebaseDatabase.getInstance()
                var fcmotordoisa = mDatabase.getReference("fcmotordoisa")
                var fcmotordoisb = mDatabase.getReference("fcmotordoisb")

//
//                Log.i(TAG, "Volto 44.11......")
//
//                Log.i(TAG, "Volto 44---1 ${mLedGpio!!.value}")

//                try {
//
//                    FCMotorDoisA = Button(BoardDefaults.getGPIOForButton15(),
//                            //       mButton = Button(BUTTON_GPIO_PIN,
//                            Button.LogicState.PRESSED_WHEN_LOW)
//
//                    FCMotorDoisA!!.setOnButtonEventListener { button, pressed ->
//
//                        Log.i(TAG, "Volto 45-1: ${fcmotordoisa}")
//
//                        if (pressed) {
//                            fcmotordoisa.setValue(1)
//
//                            Log.i(TAG, "Volto 45.00 ${fcmotordoisa}")
//
//                        }
//                        else if (!pressed){
//                            Log.i(TAG, "Volto 45+1' ${fcmotordoisa}")
//                            fcmotordoisa.setValue(0)
//                        }
//
//                        Log.i(TAG, "Volto 45.01010: ${fcmotordoisa}")
//                    }
//
//                } catch (e: IOException) {
//                    // couldn't configure the button...
//                }

                try {

                    FCMotorDoisB = Button(BoardDefaults.getGPIOForButton21(),
                            //       mButton = Button(BUTTON_GPIO_PIN,
                            Button.LogicState.PRESSED_WHEN_LOW)

                    FCMotorDoisB!!.setOnButtonEventListener { button, pressed ->

                        Log.i(TAG, "Volto 46-1: ${fcmotordoisb}")

                        if (pressed) {
                            fcmotordoisb.setValue(1)

                            Log.i(TAG, "Volto 46.00 ${fcmotordoisb}")

                        }
                        else if (!pressed){
                            Log.i(TAG, "Volto 46+1' ${fcmotordoisb}")
                            fcmotordoisb.setValue(0)
                        }

                        Log.i(TAG, "Volto 47: ${fcmotordoisb}")
                    }

                } catch (e: IOException) {
                    // couldn't configure the button...
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }
        mDatabase.addValueEventListener(dataListener)
    }

    override fun onResume() {
        super.onResume()

        //     fan!!.start()

        Smart?.let { Smart ->
            Smart?.start()

            //   wait1sec()
            //      Smart?.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Smart?.close().also { Smart = null }
    }

    private fun scanI2cDevices() {
        Log.i(TAG, "Scanning I2C devices")
        PeripheralManager.getInstance().scanI2cAvailableAddresses(I2C_PIN_NAME)
                .map { String.format(Locale.US, "0x%02X", it) }
                .forEach { address -> Log.i(TAG, "Found: $address") }
    }

    private fun wait1sec() = Thread.sleep(5000)
}