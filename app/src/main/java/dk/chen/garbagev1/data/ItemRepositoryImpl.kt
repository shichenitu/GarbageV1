package dk.chen.garbagev1.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dk.chen.garbagev1.R
import dk.chen.garbagev1.domain.Item
import dk.chen.garbagev1.domain.ItemRepository
import dk.chen.garbagev1.domain.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Scanner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : ItemRepository {

    private val _garbageSorting = MutableStateFlow<List<ItemDto>>(emptyList())
    private val _items = MutableStateFlow<List<ItemDto>>(emptyList())

    init {
        val list = mutableListOf<ItemDto>()
        val scanner = Scanner(context.resources.openRawResource(R.raw.garbage))
        scanner.useDelimiter(";|\n")
        while (scanner.hasNext()) {
            val what = scanner.next().trim()
            if (scanner.hasNext()) {
                val where = scanner.next().trim()
                list.add(ItemDto(what, where))
            }
        }
        scanner.close()
        _garbageSorting.value = list
    }

    override fun showSortingList(): Flow<List<Item>> {
        return _garbageSorting.map { dtoList ->
            dtoList.sortedByWhereAndWhat().map { it.toItem() }
        }
    }

    override fun findItem(what: String): Item? {
        return _garbageSorting.value
            .find { it.what.equals(what, ignoreCase = true) }
            ?.toItem()
    }

    override fun addItem(item: Item) {
        val formattedItemDto =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase()).toDto()

        _garbageSorting.update { currentList ->
            if (currentList.contains(formattedItemDto)) {
                currentList
            } else {
                currentList + formattedItemDto
            }
        }
    }

    override fun populateItems(rawText: String) {
        val newList = rawText.lines()
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) {
                    ItemDto(what = parts[0].trim(), where = parts[1].trim())
                } else null
            }

        _items.update { newList }
    }

    override fun removeItem(item: Item) {
        _garbageSorting.update { currentList ->
            currentList - item.toDto()
        }
    }

    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(separator = " ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    private fun List<ItemDto>.sortedByWhereAndWhat() =
        this.sortedWith(
            compareBy(
                { it.where },
                { it.what }
            )
        )
}