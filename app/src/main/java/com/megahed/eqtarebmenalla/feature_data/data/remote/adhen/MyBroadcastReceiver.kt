package com.megahed.eqtarebmenalla.feature_data.data.remote.adhen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.megahed.eqtarebmenalla.R


class MyBroadcastReceiver : BroadcastReceiver() {
    var mp: MediaPlayer? = null // Here

    private val TAG = "VPET"
    override fun onReceive(p0: Context?, p1: Intent?) {


            val mp = MediaPlayer.create(p0 , R.raw.azan1)
            mp.start()




//
//            var i = Intent(p0, AdhenAlarmActivity::class.java)
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//
//            p0?.startActivity(i)



 //       val builder = NotificationCompat.Builder(requireContext(), "FajerAdhen")
//                    .setSmallIcon(R.drawable.prayer_icon)
//                    .setContentTitle("أذان صلاة الفجر")
//                    .setContentText("حان الأن وقت صلاة الفجر")
//                    .setAutoCancel(true)
//                    .setDefaults(NotificationCompat.PRIORITY_HIGH)
//                    .setContentIntent(pendingIntent)
//                val notificationManager = NotificationManagerCompat.from(requireContext())
//                if (ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        Manifest.permission.POST_NOTIFICATIONS
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//
//                }
//                notificationManager.notify(111, builder.build())
    }
}