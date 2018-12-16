//package com.example.neube.smartdrive
//
//import android.app.UiAutomation
//import android.content.ContentValues.TAG
//import android.os.Bundle
//import android.os.Handler
//import android.util.Log
//import com.example.neube.smartdrive.MainActivity.Companion.mDatabase
//
//import com.google.android.things.contrib.driver.button.Button
//import com.google.android.things.contrib.driver.button.ButtonInputDriver
//import com.google.android.things.pio.Gpio
//import com.google.android.things.pio.PeripheralManager
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import java.io.IOException
//
//class SmartFimDeCurso {
//
//    companion object {
//
//        private var FCMotorDoisA: com.google.android.things.contrib.driver.button.Button? = null
//        private var FCMotorDoisB: com.google.android.things.contrib.driver.button.Button? = null
//        private var FCMotorUmA: com.google.android.things.contrib.driver.button.Button? = null
//        private var FCMotorUmB: com.google.android.things.contrib.driver.button.Button? = null
//        private lateinit var buttonInputDriver: ButtonInputDriver
//
//
//    }
//
//    fun getDataInit() {
//
//     //   var fcmotordoisa = FirebaseDatabase.getInstance().reference
//
//        val database = FirebaseDatabase.getInstance()
//        val fcmotordoisa = database.getReference("fcmotordoisa")
//        val fcmotordoisb = database.getReference("fcmotordoisb")
//
//        try {
//
//            FCMotorDoisA = Button(BoardDefaults.getGPIOForButton15(),
//                    //       mButton = Button(BUTTON_GPIO_PIN,
//                    Button.LogicState.PRESSED_WHEN_LOW)
//
//            FCMotorDoisA!!.setOnButtonEventListener { button, pressed ->
//
//                if (pressed) {
//                    fcmotordoisa.setValue(1)
//                }
//                fcmotordoisa.setValue(0)
//            }
//
//        } catch (e: IOException) {
//            // couldn't configure the button...
//        }
//
//        try {
//
//            FCMotorDoisB = Button(BoardDefaults.getGPIOForButton15(),
//                    //       mButton = Button(BUTTON_GPIO_PIN,
//                    Button.LogicState.PRESSED_WHEN_LOW)
//
//            FCMotorDoisB!!.setOnButtonEventListener { button, pressed ->
//
//                if (pressed) {
//                    fcmotordoisb.setValue(1)
//                }
//                fcmotordoisb.setValue(0)
//            }
//
//        } catch (e: IOException) {
//            // couldn't configure the button...
//        }
//    }
//}
//
