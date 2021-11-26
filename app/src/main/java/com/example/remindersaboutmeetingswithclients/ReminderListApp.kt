package com.example.remindersaboutmeetingswithclients

import android.app.Application
import com.example.remindersaboutmeetingswithclients.di.component.AppComponent
import com.example.remindersaboutmeetingswithclients.di.component.DaggerAppComponent

class ReminderListApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}