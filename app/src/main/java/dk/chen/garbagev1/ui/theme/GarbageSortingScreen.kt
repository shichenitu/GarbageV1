package dk.chen.garbagev1.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

        val sortingList: List<String> = listOf(
            "Newspaper should be placed in: Paper",
            "Magazine should be placed in: Paper",
            "Milk carton should be placed in: Plastic",
            "Shoe box should be placed in: Cardboard",
            "Can should be placed in: Metal"
        )

        if (showSortingList) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(text = stringResource(id = R.string.list_label), style = typography.titleLarge)
                sortingList.forEach { item ->
                    Text(text = item)
                }
            }
        }

        TextField(
            value = garbageName,
            onValueChange = { garbageName = it },
            label = { Text(text = stringResource(id = R.string.garbage_item_label)) }
        )

        Button(onClick = { showSortingList = true }) {
            Text(text = stringResource(id = R.string.where_label))
        }

        Button(onClick = { showSortingList = true }) {
            Text(text = stringResource(id = R.string.list_button_label))
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