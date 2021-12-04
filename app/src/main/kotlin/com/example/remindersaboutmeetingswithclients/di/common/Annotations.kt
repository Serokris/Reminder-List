package com.example.remindersaboutmeetingswithclients.di.common

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import kotlin.reflect.KClass

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CreateReminderPrefName

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)