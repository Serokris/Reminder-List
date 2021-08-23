package com.example.remindersaboutmeetingswithclients.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.remindersaboutmeetingswithclients.utils.notification.NotificationReceiver
import java.util.*

class ReminderAlarmManager {
    companion object {
        fun createAlarm(context: Context, calendar : Calendar) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NotificationReceiver::class.java)

            val uniqueRequestCode = System.currentTimeMillis().toInt()

            val pendingIntent = PendingIntent.getBroadcast(
                context, uniqueRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val oneHour = 3600000L
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis - oneHour, pendingIntent)
        }
    }
}