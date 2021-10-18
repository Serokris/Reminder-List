package com.example.remindersaboutmeetingswithclients.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import com.example.remindersaboutmeetingswithclients.utils.notification.NotificationReceiver
import com.example.remindersaboutmeetingswithclients.utils.constants.ReminderListFragmentConstants.REMINDER_LIST_FRAGMENT_PREF_NAME
import com.example.remindersaboutmeetingswithclients.utils.constants.ReminderListFragmentConstants.REQUEST_CODES_LIST
import java.util.*
import org.json.JSONArray
import java.lang.Exception

object ReminderAlarmManager {

    private val requestCodesList = mutableListOf<Int>()

    private var currentRequestCode: Int = 0
    val getCurrentRequestCode: Int get() = currentRequestCode

    fun createAlarm(context: Context, calendar: Calendar) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)

        val uniqueRequestCode = System.currentTimeMillis().toInt()
        currentRequestCode = uniqueRequestCode

        requestCodesList.add(uniqueRequestCode)
        saveRequestCodesInPreference(context)

        val pendingIntent = PendingIntent.getBroadcast(
            context, uniqueRequestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT
        )

        val oneHour = 3600000L
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis - oneHour,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    fun cancelAllAlarms(context: Context) {
        val requestCodesList = getRequestCodesListFromPreference(context)

        for (requestCode in requestCodesList) {
            cancelAlarm(context, requestCode)
        }
    }

    private fun getRequestCodesListFromPreference(context: Context): List<Int> {
        val requestCodesList = mutableListOf<Int>()
        try {
            val sharedPreferences =
                context.getSharedPreferences(REMINDER_LIST_FRAGMENT_PREF_NAME, MODE_PRIVATE)

            val jsonArray =
                JSONArray(sharedPreferences.getString(REQUEST_CODES_LIST, "[]"))

            for (i in 0 until jsonArray.length()) {
                requestCodesList.add(jsonArray.getInt(i))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestCodesList
    }

    private fun saveRequestCodesInPreference(context: Context) {
        val jsonArray = JSONArray()

        for (requestCode in requestCodesList) {
            jsonArray.put(requestCode)
        }

        val sharedPreferences =
            context.getSharedPreferences(
                REMINDER_LIST_FRAGMENT_PREF_NAME,
                MODE_PRIVATE
            )

        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CODES_LIST, jsonArray.toString()).apply()
    }
}