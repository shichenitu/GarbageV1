package dk.chen.garbagev1.data

import dk.chen.garbagev1.domain.Item

data class ItemDto(val what: String, val where: String)

fun ItemDto.toItem(): Item = Item(what = this.what, where = this.where)