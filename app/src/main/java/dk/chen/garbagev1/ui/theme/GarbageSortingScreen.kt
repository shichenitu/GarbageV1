package dk.chen.garbagev1.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.chen.garbagev1.R
import dk.chen.garbagev1.ui.theme.theme.GarbageV1Theme

@Composable
fun GarbageSortingScreen(modifier: Modifier = Modifier) {
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
                items(dk.chen.garbagev1.ItemsDB.garbageSorting) { item ->
                    Text (
                        text = "${item.what} should be placed in: ${item.where}",
                        modifier = Modifier.padding(vertical = 4.dp)
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
        GarbageSortingScreen()
    }
}