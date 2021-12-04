package com.example.remindersaboutmeetingswithclients

import android.app.Application
import com.example.remindersaboutmeetingswithclients.di.component.DaggerAppComponent

class ReminderListApp : Application() {

    val appComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}