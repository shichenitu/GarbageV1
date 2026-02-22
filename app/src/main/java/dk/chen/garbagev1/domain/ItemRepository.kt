package dk.chen.garbagev1.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun showSortingList(): Flow<List<Item>>
    fun findItem(what: String): Item?
    fun addItem(item: Item)
    fun removeItem(item: Item)
}