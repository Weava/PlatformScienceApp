package com.example.psinterviewapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.psinterviewapp.AppComponentProvider
import com.example.psinterviewapp.AppComponentProvider.Companion.getComponentProvider
import com.example.psinterviewapp.di.ViewModelFactory
import com.example.psinterviewapp.ui.theme.PsInterviewAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DriverListViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getComponentProvider()
            .provideComponent()
            .driverListComponent()
            .context(this)
            .build()
            .inject(this)

        setContent { Driver(viewModelFactory) }
    }
}