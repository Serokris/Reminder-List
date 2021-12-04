package com.example.remindersaboutmeetingswithclients.di.component

import android.content.Context
import com.example.remindersaboutmeetingswithclients.di.common.ViewModelFactory
import com.example.remindersaboutmeetingswithclients.di.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DatabaseModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        SharedPreferencesModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent {

    fun viewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}