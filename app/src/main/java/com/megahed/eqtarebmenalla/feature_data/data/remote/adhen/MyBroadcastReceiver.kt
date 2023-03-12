package com.megahed.eqtarebmenalla.feature_data.data.remote.adhen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.megahed.eqtarebmenalla.R
import com.megahed.eqtarebmenalla.feature_data.presentation.ui.home.AdhenAlarmActivity


class MyBroadcastReceiver : BroadcastReceiver() {
    var mp: MediaPlayer? = null // Here
    lateinit var  sharedPreference : SharedPreferences

    override fun onReceive(p0: Context?, p1: Intent?) {

//
//            val mp = MediaPlayer.create(p0 , R.raw.adhen)
//            mp.start()

//

        sharedPreference =  p0!!.getSharedPreferences("adhen",Context.MODE_PRIVATE)

        val intent = Intent(p0, AdhenAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var title = sharedPreference.getString("title","").toString()
        var text = sharedPreference.getString("text","").toString()
        var _channelId = sharedPreference.getString("channelId","").toString()
        var id    = sharedPreference.getString("id","0")?.toInt()

        Log.println(Log.ASSERT, "----------", title)



        val pendingIntent: PendingIntent = PendingIntent.getActivity(p0, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        var builder = NotificationCompat.Builder(p0!!, _channelId)
            .setSmallIcon(R.drawable.prayer_icon)
            .setContentTitle(title)
           // .setContentText(text)

            .setStyle(
                NotificationCompat.BigTextStyle()

                .bigText(text))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?



// === Removed some obsoletes

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = _channelId
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            val attributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val sound: Uri =
                Uri.parse((ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + p0
                    .packageName).toString() + "/" + R.raw.adhen) //Here is FILE_NAME is the name of file that you want to play


            channel.setSound(sound, attributes)
            channel.enableLights( true)
            notificationManager?.createNotificationChannel(channel)
            if (channelId != null) {
                builder.setChannelId(channelId)
            }
        }

        notificationManager?.notify(id!!, builder.build());


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