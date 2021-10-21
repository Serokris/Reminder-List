package com.example.remindersaboutmeetingswithclients.utils

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object: Observer<T> {
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