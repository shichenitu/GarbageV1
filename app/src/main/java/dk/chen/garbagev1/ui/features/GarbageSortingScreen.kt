package dk.chen.garbagev1.ui.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.R
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import dk.chen.garbagev1.domain.fullDescription


@Composable
fun GarbageSortingScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: GarbageSortingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GarbageSortingScreen(modifier, uiState, viewModel.uiEvents)
}

@Composable
private fun GarbageSortingScreen(
    modifier: Modifier = Modifier,
    uiState: GarbageSortingViewModel.UiState,
    uiEvents: GarbageSortingViewModel.UiEvents
) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            TextField(
                value = uiState.itemWhat,
                onValueChange = { uiEvents.onWhatChange(it) },
                label = { Text(text = stringResource(id = R.string.garbage_item_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }

        item {
            if (uiState.itemWhere.isNotEmpty()) {
                Text(
                    text = uiState.itemWhere,
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        item { Spacer(Modifier.height(16.dp)) }

        if (uiState.displaySortingList) {
            item {
                Text(
                    text = stringResource(id = R.string.list_label),
                    style = typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            uiState.sortingList.forEach { item ->
                item {
                    val context = LocalContext.current
                    Text(
                        text = item.fullDescription(context),
                        modifier = Modifier.clickable { uiEvents.onRemoveItemClick(item) }
                    )
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    uiEvents.onSearchClick(itemWhat = uiState.itemWhat)
                    focusManager.clearFocus()
                }) {
                    Text(text = stringResource(id = R.string.where_label))
                }
                Button(onClick = uiEvents::onToggleListVisibilityClick) {
                    Text(text = stringResource(id = uiState.toggleListVisibilityButtonLabel))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GarbageSortingScreenPreview() {
    GarbageV1Theme {
        GarbageSortingScreen(
            modifier = Modifier,
            uiState = GarbageSortingViewModel.UiState(),
            uiEvents = object : GarbageSortingViewModel.UiEvents {
                override fun onSearchClick(itemWhat: String) {}
                override fun onRemoveItemClick(item: Item) {}
                override fun onToggleListVisibilityClick() {}
                override fun onWhatChange(newValue: String) {}
                override fun onWhereChange(newValue: String) {}
            }
        )
    }
}