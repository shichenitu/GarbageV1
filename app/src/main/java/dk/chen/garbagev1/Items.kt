package dk.chen.garbagev1

import androidx.compose.runtime.mutableStateListOf

data class Item(val what: String, val where: String)

object ItemsDB {
    private val _garbageSorting = mutableStateListOf(
        Item(what = "newspaper", where = "paper"),
        Item(what = "magazine", where = "paper"),
        Item(what = "book", where = "paper"),
        Item(what = "milk carton", where = "plastic"),
        Item(what = "shoe box", where = "cardboard"),
        Item(what = "can", where = "metal"),
        Item(what = "aluminium foil (clean)", where = "metal"),
        Item(what = "teddy bears", where = "daily waste"),
        Item(what = "flower pot (plastic)", where = "daily waste"),
        Item(what = "cables", where = "metal"),
        Item(what = "envelopes", where = "paper"),
        Item(what = "detergents", where = "plastic"),
        Item(what = "musical instrument", where = "wood"),
        Item(what = "cookware", where = "metal"),
        Item(what = "hammer", where = "metal"),
        Item(what = "curtain clips", where = "metal"),
        Item(what = "jars", where = "glass"),
        Item(what = "carpets", where = "bulky waste"),
        Item(what = "postcards", where = "cardboard"),
        Item(what = "chips bag", where = "other"),
        Item(what = "tooth brush", where = "plastic"),
        Item(what = "shampoo bottle", where = "plastic"),
        Item(what = "capsule", where = "metal"),
        Item(what = "needle", where = "metal"),
        Item(what = "letter", where = "paper"),
        Item(what = "plastic bottle", where = "plastic"),
        Item(what = "meat", where = "food waste"),
        Item(what = "clothes", where = "other"),
        Item(what = "cutlery", where = "metal"),
        Item(what = "paint", where = "chemical"),
        Item(what = "chlorine", where = "chemical"),
        Item(what = "computer", where = "electronics"),
        Item(what = "battery", where = "batteries"),
        Item(what = "printer", where = "electronics"),
        Item(what = "potato", where = "food"),
        Item(what = "cabbage", where = "food"),
        Item(what = "kale", where = "food"),
        Item(what = "cauliflower", where = "food"),
        Item(what = "onion", where = "food"),
        Item(what = "beetroot", where = "food"),
        Item(what = "celeriac", where = "food"),
        Item(what = "cellery", where = "food"),
        Item(what = "flour", where = "food"),
        Item(what = "sugar", where = "food"),
        Item(what = "rice", where = "food"),
    )

    val garbageSorting: List<Item> get() = _garbageSorting.sortedWith (compareBy ({it.where} , {it.what}))

    // Extension function to handle Title Case formatting
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