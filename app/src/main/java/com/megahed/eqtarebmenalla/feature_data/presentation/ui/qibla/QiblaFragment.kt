package com.megahed.eqtarebmenalla.feature_data.presentation.ui.qibla

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.megahed.eqtarebmenalla.R
import com.megahed.eqtarebmenalla.databinding.FragmentQiblaBinding
import com.megahed.eqtarebmenalla.feature_data.presentation.viewoModels.QiblaVM


class QiblaFragment : Fragment(), SensorEventListener {

    lateinit var qiblaVM: QiblaVM
    lateinit var binding : FragmentQiblaBinding
    lateinit var sensorManager : SensorManager
    lateinit var sensor: Sensor
    var altitude = ""
    var longitude =""
    var angl = 0
    var curent_degree =0F
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentQiblaBinding.inflate(inflater, container, false)
        val root : View = binding.root


        qiblaVM = QiblaVM()
        getArguments()?.getString("altitude")?.let { altitude =it }
        getArguments()?.getString("longitude")?.let {longitude = it }

        qiblaVM.getQibla(altitude, longitude).observe(viewLifecycleOwner, Observer {
            angl = it.data.direction.toInt()
        })

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager


        return root
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val type = event!!.sensor.type
        val data: FloatArray

       var degree = Math.round(event.values[0]).toFloat()
        var rotateAnimation = RotateAnimation(
            curent_degree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F)

        rotateAnimation.duration = 100
        binding.mainImageDial.startAnimation(rotateAnimation)
//        rotateAnimation.fillAfter = true
        Log.println(Log.ASSERT, "qibla degr", degree.toString())
        binding.sotwLabel.text = degree.toString()
        if (degree.toInt() in angl-5 .. angl+5){
            binding.constraint.setBackgroundColor(requireContext().getColor(R.color.primary_color))
        }
        else{
            binding.constraint.setBackgroundColor(requireContext().getColor(R.color.white))

        }
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