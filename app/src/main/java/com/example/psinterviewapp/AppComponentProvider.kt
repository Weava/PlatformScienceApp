package com.example.psinterviewapp

import android.content.Context

interface AppComponentProvider {

    fun provideComponent(): PsInterviewComponent

    companion object {
        fun Context.getComponentProvider(): AppComponentProvider {
            return applicationContext as AppComponentProvider
        }
    }
}