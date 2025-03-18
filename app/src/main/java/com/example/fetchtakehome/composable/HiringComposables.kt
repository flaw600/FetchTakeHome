package com.example.fetchtakehome.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fetchtakehome.R
import com.example.fetchtakehome.model.HiringResponseItem
import com.example.fetchtakehome.ui.theme.FetchTakeHomeTheme
import com.example.fetchtakehome.viewmodel.HiringState
import com.example.fetchtakehome.viewmodel.HiringViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiringScaffold(viewModel: HiringViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(stringResource(R.string.hiring_activity_name))
            }
        )
    }, floatingActionButton = {
        val state by viewModel.hiringListState.collectAsStateWithLifecycle(HiringState.Loading)
        if (state !is HiringState.Loading) {
            FloatingActionButton(onClick = { viewModel.refresh() }) {
                Icon(Icons.Filled.Refresh, "Refresh")
            }
        }
    }) { innerPadding ->
        HiringScreen(viewModel, Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiringScreen(viewModel: HiringViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.hiringListState.collectAsState(HiringState.Loading)
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when(val value = state.value) {
            is HiringState.Success -> {
                HiringList(value.hiringListGroup)
            }
            is HiringState.Message -> {
                Text(
                    stringResource(value.messageResId),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
            is HiringState.Loading -> CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HiringList(hiringResponse: Map<Int, List<HiringResponseItem>>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        hiringResponse.forEach { (_, value) ->
            items(value, key = { it.id }) {
                HiringCell(it)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun HiringCell(hiringResponseItem: HiringResponseItem) {
    ListItem(
        headlineContent = { hiringResponseItem.name?.let { Text(it) } },
        trailingContent = { Text(stringResource(R.string.list_header, hiringResponseItem.listId)) }
    )
}

@Preview(showBackground = true)
@Composable
private fun HiringListPreview() {
    FetchTakeHomeTheme {
        HiringList(
            mapOf(
                1 to listOf(HiringResponseItem(id = 0, name = "Jassi Sikand"), HiringResponseItem(id = 1, name = "GS")),
                2 to listOf(HiringResponseItem(id = 2, name = "Jassi Sikand"), HiringResponseItem(id = 3, name = "GS"))
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HiringStateLoading() {
    FetchTakeHomeTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HiringStateError() {
    FetchTakeHomeTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.error_message), textAlign = TextAlign.Center)
        }
    }
}