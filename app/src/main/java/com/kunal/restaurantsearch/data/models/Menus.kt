package com.kunal.restaurantsearch.data.models

import com.google.gson.annotations.SerializedName
import com.kunal.restaurantsearch.utils.JSONConvertable

data class Menus(
    @SerializedName("menus")
    var menuList: List<Menu>,
) : JSONConvertable {

    data class Menu(
        @SerializedName("restaurantId")
        var restaurantId: Int,
        @SerializedName("categories")
        var categories: List<Category>
    ) : JSONConvertable

    data class Category(
        @SerializedName("id")
        var id: Long,
        @SerializedName("name")
        var name: String,
        @SerializedName("menu-items")
        var menuItems: List<Item>
    ) : JSONConvertable

    data class Item(
        @SerializedName("id")
        var id: Long,
        @SerializedName("name")
        var name: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("price")
        var price: String,
        @SerializedName("images")
        var images: List<String>
    ) : JSONConvertable
}
