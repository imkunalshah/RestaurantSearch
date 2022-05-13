package com.kunal.restaurantsearch.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunal.restaurantsearch.R
import com.kunal.restaurantsearch.data.models.Menus
import com.kunal.restaurantsearch.data.models.Restaurants
import com.kunal.restaurantsearch.utils.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor() : ViewModel() {

    private lateinit var restaurants: Restaurants
    private lateinit var menus: Menus
    private var searchJob: Job? = null
    private var _searchResult = MutableLiveData<List<Restaurants.Restaurant>>()
    val searchResult: LiveData<List<Restaurants.Restaurant>> = _searchResult

    fun initializeJsonData(context: Context) {
        val restaurantJson = context.resources.openRawResource(R.raw.restaurants)
            .bufferedReader().use { it.readText() }
        val menuJson = context.resources.openRawResource(R.raw.menus)
            .bufferedReader().use { it.readText() }

        restaurants = restaurantJson.toObject()
        menus = menuJson.toObject()
    }

    fun performSearch(keyWord: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(400L)
            val menuList = menus.menuList
            val restaurantList = restaurants.restaurantList
            val restaurantIds: MutableList<Int> = mutableListOf()
            for (restaurant in menuList) {
                val categories = restaurant.categories
                for (category in categories) {
                    val menusList = category.menuItems
                    var isRestaurantAdded = false
                    for (item in menusList) {
                        val menuItem = item.name
                        if (menuItem.trim().contains(keyWord, true)) {
                            restaurantIds.add(restaurant.restaurantId)
                            isRestaurantAdded = true
                            break
                        }
                    }
                    if (isRestaurantAdded) break
                }
            }
            for (restaurant in restaurantList) {
                val restaurantName = restaurant.name
                val restaurantCuisines = restaurant.cuisineType
                if (restaurantName.trim().contains(keyWord, true)) {
                    restaurantIds.add(restaurant.id)
                }
                for (cuisine in restaurantCuisines) {
                    if (cuisine.trim().contains(keyWord, true)) {
                        restaurantIds.add(restaurant.id)
                        break
                    }
                }
            }
            val distinctRestaurantIds = restaurantIds.distinct()
            val resultRestaurants = getResultRestaurants(distinctRestaurantIds)
            withContext(Dispatchers.Main) {
                _searchResult.postValue(resultRestaurants)
            }
        }
    }

    private fun getResultRestaurants(distinctRestaurantIds: List<Int>): List<Restaurants.Restaurant> {
        val resultList: MutableList<Restaurants.Restaurant> = mutableListOf()
        for (id in distinctRestaurantIds) {
            for (restaurant in restaurants.restaurantList) {
                if (restaurant.id == id) {
                    resultList.add(restaurant)
                    break
                }
            }
        }
        return resultList.distinct().toList()
    }

}