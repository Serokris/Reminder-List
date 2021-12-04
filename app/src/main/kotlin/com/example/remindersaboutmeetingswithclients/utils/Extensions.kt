package com.example.remindersaboutmeetingswithclients.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.remindersaboutmeetingswithclients.ReminderListApp
import com.example.remindersaboutmeetingswithclients.di.component.AppComponent

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun View.hideView() {
    this.visibility = View.INVISIBLE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

val Fragment.appComponent: AppComponent
    get() = (requireContext().applicationContext as ReminderListApp).appComponent