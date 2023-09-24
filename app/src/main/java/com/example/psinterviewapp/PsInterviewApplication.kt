package com.example.psinterviewapp

import android.app.Application

class PsInterviewApplication : Application(), AppComponentProvider {

    private val appLevelComponent = DaggerPsInterviewComponent.create()

    override fun provideComponent(): PsInterviewComponent = appLevelComponent
}