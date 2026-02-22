package dk.chen.garbagev1.domain

import androidx.compose.runtime.mutableStateListOf

data class Item(val what: String, val where: String)

object ItemsDB {
    private val _garbageSorting = mutableStateListOf<Item>()

    val garbageSorting: List<Item> get() = _garbageSorting.sortedWith (compareBy ({it.where} , {it.what}))

    // Extension function to handle Title Case formatting
    private fun String.toItem(): Item? {
        val parts = this.split(",")
        return if (parts.size == 2) {
            Item(what = parts[0].trim() , where = parts[1].trim())
        } else null
    }

    fun populateItems(rawText: String) {
        _garbageSorting.clear()

        rawText.lines().forEach { line ->
            if (line.isNotBlank()) {
                val item = line.toItem()
                if (item != null) {
                    _garbageSorting.add(item)
                }
            }
        }
    }

    fun findItem(query: String) : Item? {
        val trimmedQuery = query.trim()
        return _garbageSorting.find { it.what.equals(trimmedQuery, ignoreCase = true)}
    }

    private fun String.toTitleCase(): String {
        return this.trim().split(" ").joinToString(" ") { word ->
            word.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    fun addItem(item: Item) {
        val formattedItem =
            item.copy(what = item.what.toTitleCase(), where = item.where.toTitleCase())

        if (!_garbageSorting.contains(formattedItem)) {
            _garbageSorting.add(formattedItem)
        }
    }

    fun removeItem(item: Item) {
        _garbageSorting.remove(element = item)
    }
}