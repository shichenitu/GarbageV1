package dk.chen.garbagev1.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.chen.garbagev1.ItemsDB
import dk.chen.garbagev1.R
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme
import kotlinx.coroutines.launch

@Composable
fun GarbageSortingScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showSortingList: Boolean by rememberSaveable { mutableStateOf(value = false) }
        var garbageName by rememberSaveable { mutableStateOf("") }


        if (!showSortingList) {
            TextField(
                value = garbageName,
                onValueChange = { garbageName = it },
                label = { Text(text = stringResource(id = R.string.garbage_item_label)) }
            )

            Button(onClick = {
                val foundItem = dk.chen.garbagev1.ItemsDB.findItem(garbageName)
                if (foundItem != null) {
                    garbageName = "${foundItem.what} should be placed in: ${foundItem.where}"
                } else {
                    garbageName = "$garbageName should be placed in: not found"
                }
            }) {
                Text(text = stringResource(id = R.string.where_label))
            }

            Button(onClick = { showSortingList = true }) {
                Text(text = stringResource(id = R.string.show_sorting_list_label))
            }
        } else {
            Button(onClick = { showSortingList = false }) {
                Text(text = stringResource(id = R.string.search_item_label))
            }

            Text(
                text = stringResource(id = R.string.list_label) ,
                style = typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(ItemsDB.garbageSorting) { item ->
                    Text (
                        text = "${item.what} should be placed in: ${item.where}",
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable {
                                scope.launch {
                                    val result = snackbarHostState
                                        .showSnackbar(
                                            message = "Removed ${item.what}",
                                            actionLabel = "Undo",
                                            duration = SnackbarDuration.Short
                                        )
                                    when (result) {
                                        SnackbarResult.ActionPerformed -> {
                                            /* Handle snackbar action performed (UNDO) */
                                        }

                                        SnackbarResult.Dismissed -> {
                                            /* Handle snackbar dismissed */
                                            ItemsDB.removeItem(item)
                                        }
                                    }
                                }
                            }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortingListScreenPreview() {
    GarbageV1Theme() {
        val snackbarHostState = remember { SnackbarHostState() }
        GarbageSortingScreen(snackbarHostState = snackbarHostState)
    }
}