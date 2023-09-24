package com.example.psinterviewapp

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PsInterviewModule::class])
interface PsInterviewComponent {
    fun driverListComponent(): DriverListComponent.Builder
}