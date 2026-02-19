package dk.chen.garbagev1.domain


import android.annotation.SuppressLint
import android.content.Context
import dk.chen.garbagev1.R
import dk.chen.garbagev1.data.ItemDto

data class Item(val what: String, val where: String)

fun Item.toDto(): ItemDto = ItemDto(what = this.what, where = this.where)

@SuppressLint("StringFormatInvalid")
fun Item.fullDescription(context: Context): String =
    context.getString(R.string.list_label, this.what.lowercase(), this.where)