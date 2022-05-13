package com.kunal.restaurantsearch.data.models

import com.google.gson.annotations.SerializedName
import com.kunal.restaurantsearch.utils.JSONConvertable
import java.io.Serializable

data class Restaurants(
    @SerializedName("restaurants")
    var restaurantList: List<Restaurant>,
) : JSONConvertable {
    data class Restaurant(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("neighborhood")
        var neighborhood: String,
        @SerializedName("photograph")
        var photograph: String,
        @SerializedName("address")
        var address: String,
        @SerializedName("latlng")
        var latlng: LatLang,
        @SerializedName("cuisine_type")
        var cuisineType: List<String>,
        @SerializedName("operating_hours")
        var operatingHours: OperatingHours,
        @SerializedName("reviews")
        var reviews: List<Review>
    ) : JSONConvertable, Serializable

    data class LatLang(
        @SerializedName("lat")
        var lat: String,
        @SerializedName("lng")
        var lng: String
    ) : JSONConvertable, Serializable

    data class OperatingHours(
        @SerializedName("monday")
        var monday: List<String>,
        @SerializedName("tuesday")
        var tuesday: List<String>,
        @SerializedName("wednesday")
        var wednesday: List<String>,
        @SerializedName("thursday")
        var thursday: List<String>,
        @SerializedName("friday")
        var friday: List<String>,
        @SerializedName("saturday")
        var saturday: List<String>,
        @SerializedName("sunday")
        var sunday: List<String>,
    ) : JSONConvertable, Serializable

    data class Review(
        @SerializedName("name")
        var name: String,
        @SerializedName("date")
        var date: String,
        @SerializedName("rating")
        var rating: Int,
        @SerializedName("comments")
        var comments: String
    ) : JSONConvertable, Serializable
}
