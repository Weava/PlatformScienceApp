package com.example.psinterviewapp.data

import android.content.Context
import android.content.res.AssetManager
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException

class ShipmentFileStoreTest {

    private val context: Context = mock()
    @OptIn(ExperimentalStdlibApi::class)
    private val adapter: JsonAdapter<ShipmentManifest> = Moshi.Builder().build().adapter()
    private val fileStore = ShipmentFileStore(context, adapter)

    @Test
    fun `getShipmentManifest - given file exists and is valid, should return succesfully`() = runTest {
        // Given
        val json =
            """
                {
                  "shipments": [
                    "215 Osinski Manors",
                    "9856 Marvin Stravenue",
                    "7127 Kathlyn Ferry",
                    "987 Champlin Lake",
                    "63187 Volkman Garden Suite 447",
                    "75855 Dessie Lights",
                    "1797 Adolf Island Apt. 744",
                    "2431 Lindgren Corners",
                    "8725 Aufderhar River Suite 859",
                    "79035 Shanna Light Apt. 322"
                  ],
                  "drivers": [
                    "Everardo Welch",
                    "Orval Mayert",
                    "Howard Emmerich",
                    "Izaiah Lowe",
                    "Monica Hermann",
                    "Ellis Wisozk",
                    "Noemie Murphy",
                    "Cleve Durgan",
                    "Murphy Mosciski",
                    "Kaiser Sose"
                  ]
                }
            """.trimIndent()
        val expected = adapter.fromJson(json)
        val assetManager = mock<AssetManager>()
        given(context.assets).willReturn(assetManager)
        given(assetManager.open("shipmentsManifest.json"))
            .willReturn(
                ByteArrayInputStream(json.toByteArray())
            )

        // When
        val shipmentManifest = fileStore.getShipmentManifest()

        // Then
        shipmentManifest as ShipmentManifestRetrievalResult.Success
        assertThat(expected).isEqualTo(shipmentManifest.shipmentManifest)
    }

    @Test
    fun `getShipmentManifest - given file read errors, should return error`() = runTest {
        // Given
        val assetManager = mock<AssetManager>()
        val expected = FileNotFoundException("File not found")
        given(context.assets).willReturn(assetManager)
        given(assetManager.open("shipmentsManifest.json"))
            .willThrow(expected)

        // When
        val shipmentManifest = fileStore.getShipmentManifest()

        // Then
        shipmentManifest as ShipmentManifestRetrievalResult.Error.Exception
        assertThat(expected).isEqualTo(shipmentManifest.throwable)
    }
}