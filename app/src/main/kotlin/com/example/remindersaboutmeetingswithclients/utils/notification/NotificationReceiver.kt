package com.example.remindersaboutmeetingswithclients.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ClientMeetingNotification.createNotification(context)
    }
}