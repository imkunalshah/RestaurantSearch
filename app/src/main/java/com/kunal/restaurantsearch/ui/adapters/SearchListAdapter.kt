package com.kunal.restaurantsearch.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunal.restaurantsearch.data.models.Restaurants
import com.kunal.restaurantsearch.databinding.LayoutSearchItemBinding
import com.kunal.restaurantsearch.utils.loadRestaurantCoverImage

class SearchListAdapter(
    _restaurantList: List<Restaurants.Restaurant>,
    private val onClick: (restaurant: Restaurants.Restaurant?) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    private var restaurantList: MutableList<Restaurants.Restaurant>? = null

    init {
        restaurantList = _restaurantList.toMutableList()
    }

    class SearchListViewHolder(
        private val binding: LayoutSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            restaurant: Restaurants.Restaurant?,
            onClick: (restaurant: Restaurants.Restaurant?) -> Unit
        ) {
            binding.restaurantName.text = restaurant?.name
            binding.restaurantAddress.text = restaurant?.address
            binding.restaurantImage.loadRestaurantCoverImage(
                restaurant?.photograph,
                binding.restaurantImage
            )
            binding.viewDetailsButton.setOnClickListener {
                onClick.invoke(restaurant)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListViewHolder {
        val binding =
            LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        val restaurant = restaurantList?.get(position)
        holder.bind(restaurant, onClick)
    }

    override fun getItemCount() = restaurantList?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(restaurantList: List<Restaurants.Restaurant>?) {
        restaurantList?.toList()?.let {
            this.restaurantList?.clear()
            this.restaurantList?.addAll(it)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        restaurantList?.clear()
        notifyDataSetChanged()
    }
}