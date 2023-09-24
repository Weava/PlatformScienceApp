package com.example.psinterviewapp

import com.example.psinterviewapp.di.ViewModelFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PsInterviewModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }
}