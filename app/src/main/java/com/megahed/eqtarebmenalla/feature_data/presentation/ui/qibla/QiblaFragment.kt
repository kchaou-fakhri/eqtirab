package com.megahed.eqtarebmenalla.feature_data.presentation.ui.qibla

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat.getSystemService
import com.megahed.eqtarebmenalla.R
import com.megahed.eqtarebmenalla.databinding.FragmentQiblaBinding


class QiblaFragment : Fragment(), SensorEventListener {

    lateinit var binding : FragmentQiblaBinding
    lateinit var sensorManager : SensorManager
    lateinit var sensor: Sensor
    var curent_degree =0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQiblaBinding.inflate(inflater, container, false)
        val root : View = binding.root



        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager



        return root
    }

    override fun onSensorChanged(event: SensorEvent?) {
       var degree = Math.round(event?.values?.get(0)!!).toDouble()
        var rotateAnimation = RotateAnimation(
            curent_degree.toFloat(), (-degree).toFloat(),
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)

        rotateAnimation.duration = 500
        rotateAnimation.repeatCount = 0
        rotateAnimation.fillAfter = true
        binding.compass.startAnimation(rotateAnimation)
        Log.println(Log.ASSERT, "qibla degr", degree.toString())
       curent_degree = -degree
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}