package dk.chen.garbagev1.domain

import dk.chen.garbagev1.data.ItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class ItemRepositoryImpl @Inject constructor() : ItemRepository {

    private val _items = MutableStateFlow<List<ItemDto>>(emptyList())

    override fun showSortingList(): Flow<List<Item>> {
        return _items.map { dtoList ->
            dtoList.sortedByWhereAndWhat().map { it.toItem() }
        }
    }

    override fun findItem(what: String) : Item? {
        val trimmedQuery = what.trim()
        return _items.value
            .find { it.what.equals(trimmedQuery, ignoreCase = true)}
            ?.toItem()
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
        _items.update { currentList ->
            currentList.filterNot { it.what == item.what && it.where == item.where }
        }
    }

    override fun addItem(item: Item) {
        val formattedItemDto =
            item.copy(
                what = item.what.toTitleCase(),
                where = item.where.toTitleCase()
            ).toDto()

        _items.update { currentList ->
            if (currentList.contains(formattedItemDto)) {
                currentList
            } else {
                currentList + formattedItemDto
            }
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
            comparator = compareBy(
                { it.where },
                { it.what }
            )
        )

    private fun ItemDto.toItem() = Item(what = this.what, where = this.where)
}

interface ItemRepository {
    fun showSortingList(): Flow<List<Item>>
    fun findItem(what: String): Item?
    fun addItem(item: Item)
    fun removeItem(item: Item)
    fun populateItems(rawText: String)
}