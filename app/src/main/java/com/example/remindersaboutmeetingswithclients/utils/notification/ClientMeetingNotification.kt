package com.example.remindersaboutmeetingswithclients.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.remindersaboutmeetingswithclients.R
import com.example.remindersaboutmeetingswithclients.presentation.MainActivity
import com.example.remindersaboutmeetingswithclients.presentation.reminder.createreminder.CreateReminderFragmentConstants.CLIENTS_FULL_NAME_LIST
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

private const val CLIENT_MEETING_NOTIFICATION_PREF_NAME = "client_meeting_notification_pref_name"

object ClientMeetingNotification {

    private lateinit var notificationManager: NotificationManager
    private const val CHANNEL_ID = "channelID"
    private const val CHANNEL_NAME = "NotificationChannel"

    private var clientsDataList = mutableListOf<ClientDataForNotification>()

    fun addClientDataToList(clientData: ClientDataForNotification, context: Context) {
        clientsDataList.add(clientData)
        saveClientFullNameInPreference(context)
    }

    fun createNotification(context: Context) {
        if (!this::notificationManager.isInitialized)
            initNotificationManager(context)

        clientsDataList = getClientsFullNameListFromPreference(context)
            .sortedBy { clientDataForNotification -> clientDataForNotification.notificationTime }
            .toMutableList()

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val clientFullName =
            clientsDataList.minByOrNull { clientData -> clientData.notificationTime }?.clientFullName

        val notificationText = "You have a meeting with $clientFullName in an hour!"

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setContentTitle("Reminder")
            .setContentText(notificationText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val uniqueNotificationId = System.currentTimeMillis().toInt()

        createChannelIfNeeded(notificationManager)
        notificationManager.notify(uniqueNotificationId, notificationBuilder.build())

        clientsDataList.remove(clientsDataList.first())
        saveClientFullNameInPreference(context)
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

    private fun getClientsFullNameListFromPreference(
        context: Context
    ): MutableList<ClientDataForNotification> {
        val clientsFullNameList = mutableListOf<ClientDataForNotification>()
        try {
            val sharedPreferences =
                context.getSharedPreferences(CLIENT_MEETING_NOTIFICATION_PREF_NAME, MODE_PRIVATE)

            val jsonArray =
                JSONArray(sharedPreferences.getString(CLIENTS_FULL_NAME_LIST, "[]"))

            for (i in 0 until jsonArray.length()) {
                clientsFullNameList.add(
                    ClientDataForNotification(
                        jsonArray.getJSONObject(i).getJSONObject("clientDataForNotification")
                            .getString("clientFullName").toString(),
                        jsonArray.getJSONObject(i).getJSONObject("clientDataForNotification")
                            .getString("notificationTime").toLong()
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return clientsFullNameList
    }

    private fun saveClientFullNameInPreference(context: Context) {
        val jsonArray = JSONArray()

        for (clientData in clientsDataList) {
            val jsonString =
                "{\"clientDataForNotification\":{\"clientFullName\":\"${clientData.clientFullName}\"," +
                        "\"notificationTime\":${clientData.notificationTime}}}"
            val jsonObject = JSONObject(jsonString)
            jsonArray.put(jsonObject)
        }

        val sharedPreferences =
            context.getSharedPreferences(CLIENT_MEETING_NOTIFICATION_PREF_NAME, MODE_PRIVATE)

        sharedPreferences.edit().putString(CLIENTS_FULL_NAME_LIST, jsonArray.toString()).apply()
    }
}