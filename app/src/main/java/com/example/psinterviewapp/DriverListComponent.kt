package com.example.psinterviewapp

import android.content.Context
import com.example.psinterviewapp.di.ActivityScope
import com.example.psinterviewapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DriverListModule::class])
interface DriverListComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance fun context(context: Context): Builder
        fun build(): DriverListComponent
    }

    fun inject(mainActivity: MainActivity)
}