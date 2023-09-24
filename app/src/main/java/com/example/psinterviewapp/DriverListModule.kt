package com.example.psinterviewapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.psinterviewapp.data.ShipmentManifest
import com.example.psinterviewapp.di.ActivityScope
import com.example.psinterviewapp.domain.RetrieveShipmentManifest
import com.example.psinterviewapp.ui.DriverListViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.Module
import dagger.Provides

@Module
class DriverListModule {

    @OptIn(ExperimentalStdlibApi::class)
    @ActivityScope
    @Provides
    fun provideShipmentManifestJsonAdapter(moshi: Moshi): JsonAdapter<ShipmentManifest> {
        return moshi.adapter()
    }

    @ActivityScope
    @Provides
    fun provideDriverListViewModel(
        retrieveShipmentManifest: RetrieveShipmentManifest
    ): DriverListViewModel {
        return DriverListViewModel(retrieveShipmentManifest)
    }
}