package com.example.psinterviewapp.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ShipmentInMemoryStoreTest {

    private val store = ShipmentInMemoryStore()

    @Test
    fun `store - given no item stored, retrieve returns null`() {
        // When
        val actual = store.retrieve()

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `store - given item stored, retrieve returns same item`() {
        // Given
        val item = ShipmentManifest(listOf("123"), listOf("456"))

        // When
        store.store(item)
        val actual = store.retrieve()

        // Then
        assertThat(actual).isEqualTo(item)
    }
}