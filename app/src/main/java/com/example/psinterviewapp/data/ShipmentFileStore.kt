package com.example.psinterviewapp.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okio.IOException
import okio.buffer
import okio.source
import javax.inject.Inject
import kotlin.coroutines.resume

class ShipmentFileStore @Inject constructor(
    private val context: Context,
    private val shipmentManifestJsonAdapter: JsonAdapter<ShipmentManifest>
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getShipmentManifest(): ShipmentManifestRetrievalResult {
        return suspendCancellableCoroutine { continuation ->
            try {
                val jsonSource = context.assets
                    .open("shipmentsManifest.json")
                    .source()
                    .buffer()
                val shipmentManifest = shipmentManifestJsonAdapter.fromJson(jsonSource)
                if (shipmentManifest == null) {
                    continuation.resume(ShipmentManifestRetrievalResult.Error.EmptyOrNullFile)
                } else {
                    continuation.resume(ShipmentManifestRetrievalResult.Success(shipmentManifest))
                }
            } catch (ex: IOException) {
                continuation.resume(ShipmentManifestRetrievalResult.Error.Exception(ex))
            }
        }
    }
}