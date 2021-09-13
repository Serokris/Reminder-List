package com.example.remindersaboutmeetingswithclients.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreferenceUtils {
    fun clearSharedPreferenceIfPreferenceIsSet(context: Context, preferenceKey: String) {
        val sharedPreferences = context.getSharedPreferences(preferenceKey, MODE_PRIVATE)
        if (isPreferenceSet(sharedPreferences)) {
            sharedPreferences.edit().clear().apply()
        }
    }

    private fun isPreferenceSet(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.all.isNotEmpty()
    }
}