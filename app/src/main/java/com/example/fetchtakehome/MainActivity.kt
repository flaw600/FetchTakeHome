package com.example.fetchtakehome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.fetchtakehome.composable.HiringScaffold
import com.example.fetchtakehome.ui.theme.FetchTakeHomeTheme
import com.example.fetchtakehome.viewmodel.HiringViewModel
import com.example.fetchtakehome.viewmodel.impl.HiringViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HiringViewModel by viewModels<HiringViewModelImpl>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeTheme {
                //TODO Should this be part of HiringScreen?
                HiringScaffold(viewModel)
            }
        }
    }
}
