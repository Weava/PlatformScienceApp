package com.example.psinterviewapp.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psinterviewapp.R
import com.example.psinterviewapp.domain.RetrieveShipmentManifest
import com.example.psinterviewapp.domain.SuitableShippingResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DriverListViewModel(
    private val retrieveShipmentManifest: RetrieveShipmentManifest
) : ViewModel(), ListExpansionUpdater<DriverListItemModel> {

    private val _state: MutableStateFlow<DriverListState> = MutableStateFlow(DriverListState())
    val state: StateFlow<DriverListState> = _state

    init {
        loadDriverList()
    }

    override fun expandCollapseListItem(item: DriverListItemModel) {
        val mutableStateList = _state.value.driverList.toMutableList()
        val newDriverItem = item.copy(
            isExpanded = !item.isExpanded
        )

        mutableStateList.replaceAll {
            if (it == item) {
                newDriverItem
            } else {
                it
            }
        }

        _state.value = _state.value.copy(driverList = mutableStateList.toList())
    }

    private fun loadDriverList() {
        viewModelScope.launch {
            when (val result = retrieveShipmentManifest()) {
                SuitableShippingResult.NoValidMap -> {
                    DriverListState(emptyList(), errorMessage = R.string.error_message)
                }
                is SuitableShippingResult.Success -> {
                    result.driverToAddressMap.map {
                        DriverListItemModel(it.key, it.value)
                    }.let { DriverListState(it) }
                }
            }.also { _state.value = it }
        }
    }
}

data class DriverListState(
    val driverList: List<DriverListItemModel> = emptyList(),
    @StringRes val errorMessage: Int? = null
)