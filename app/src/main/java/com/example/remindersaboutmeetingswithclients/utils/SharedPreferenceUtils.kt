package com.example.remindersaboutmeetingswithclients.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferenceUtils {
    fun clearSharedPreferenceIfPreferenceIsSet(context: Context, preferenceKey: String) {
        if (isPreferenceSet(context, preferenceKey)) {
            val sharedPreferences = context.getSharedPreferences(preferenceKey, MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
        }
    }

    private fun isPreferenceSet(context: Context, preferenceKey: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(preferenceKey, MODE_PRIVATE)
        return sharedPreferences.all.isNotEmpty()
    }
}