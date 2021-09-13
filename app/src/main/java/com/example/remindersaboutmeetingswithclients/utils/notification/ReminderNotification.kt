package com.example.remindersaboutmeetingswithclients.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.presentation.MainActivity

object ReminderNotification {

    private lateinit var notificationManager: NotificationManager
    private const val CHANNEL_ID = "channelID"
    private const val CHANNEL_NAME = "NotificationChannel"

    var currentClientFullName: String = ""

    fun createNotification(context: Context) {
        if (!this::notificationManager.isInitialized)
            initNotificationManager(context)

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bigText = "You have a meeting with $currentClientFullName in an hour!"

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setContentTitle("Reminder")
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val uniqueNotificationId = System.currentTimeMillis().toInt()

        createChannelIfNeeded(notificationManager)
        notificationManager.notify(uniqueNotificationId, notificationBuilder.build())
    }

    private fun initNotificationManager(context: Context) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
    }

    private fun createChannelIfNeeded(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}