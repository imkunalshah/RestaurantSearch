package com.kunal.restaurantsearch.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kunal.restaurantsearch.R
import com.kunal.restaurantsearch.data.models.Restaurants
import com.kunal.restaurantsearch.databinding.FragmentShowDetailsDialogBinding
import com.kunal.restaurantsearch.utils.getDialogWidth
import com.kunal.restaurantsearch.utils.loadRestaurantCoverImage

class ShowDetailsDialogFragment : DialogFragment() {

    lateinit var binding: FragmentShowDetailsDialogBinding

    companion object {
        const val TAG = "ShowDetailsDialogFragment"
        const val RESTAURANT = "restaurant"

        fun newInstance(restaurant: Restaurants.Restaurant?): ShowDetailsDialogFragment {
            val bundle = Bundle().apply {
                putSerializable(RESTAURANT, restaurant)
            }
            val showDetailsDialogFragment = ShowDetailsDialogFragment().apply {
                arguments = bundle
            }
            return showDetailsDialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDetailsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDimens(view)
        initializeViews()
    }

    private fun initializeViews() {
        val restaurant = arguments?.getSerializable(RESTAURANT) as? Restaurants.Restaurant
        val operatingHours = restaurant?.operatingHours
        val monday = operatingHours?.monday
        val tuesday = operatingHours?.tuesday
        val wednesday = operatingHours?.wednesday
        val thursday = operatingHours?.thursday
        val friday = operatingHours?.friday
        val saturday = operatingHours?.saturday
        val sunday = operatingHours?.sunday
        var cuisines = ""
        var sundayTimings = ""
        var mondayTimings = ""
        var tuesdayTimings = ""
        var wednesdayTimings = ""
        var thursdayTimings = ""
        var fridayTimings = ""
        var saturdayTimings = ""
        sunday?.let {
            for (i in it.indices) {
                val time = it[i]
                sundayTimings = if (i != 0) {
                    "$sundayTimings, $time"
                } else {
                    "$sundayTimings $time"
                }
            }
        }
        monday?.let {
            for (i in it.indices) {
                val time = it[i]
                mondayTimings = if (i != 0) {
                    "$mondayTimings, $time"
                } else {
                    "$mondayTimings $time"
                }
            }
        }
        tuesday?.let {
            for (i in it.indices) {
                val time = it[i]
                tuesdayTimings = if (i != 0) {
                    "$tuesdayTimings, $time"
                } else {
                    "$tuesdayTimings $time"
                }
            }
        }
        wednesday?.let {
            for (i in it.indices) {
                val time = it[i]
                wednesdayTimings = if (i != 0) {
                    "$wednesdayTimings, $time"
                } else {
                    "$wednesdayTimings $time"
                }
            }
        }
        thursday?.let {
            for (i in it.indices) {
                val time = it[i]
                thursdayTimings = if (i != 0) {
                    "$thursdayTimings, $time"
                } else {
                    "$thursdayTimings $time"
                }
            }
        }
        friday?.let {
            for (i in it.indices) {
                val time = it[i]
                fridayTimings = if (i != 0) {
                    "$fridayTimings, $time"
                } else {
                    "$fridayTimings $time"
                }
            }
        }
        saturday?.let {
            for (i in it.indices) {
                val time = it[i]
                saturdayTimings = if (i != 0) {
                    "$saturdayTimings, $time"
                } else {
                    "$saturdayTimings $time"
                }
            }
        }
        restaurant?.cuisineType?.let {
            for (i in it.indices) {
                val cuisine = it[i]
                cuisines = if (i != 0) {
                    "$cuisines, $cuisine"
                } else {
                    "$cuisines $cuisine"
                }
            }
        }

        binding.restaurantImage.loadRestaurantCoverImage(
            restaurant?.photograph,
            binding.restaurantImage
        )
        binding.restaurantName.text = restaurant?.name
        binding.restaurantAddress.text = restaurant?.address
        binding.restaurantCuisines.text = cuisines
        binding.sundayTime.text = sundayTimings
        binding.mondayTime.text = mondayTimings
        binding.tuesdayTime.text = tuesdayTimings
        binding.wednesdayTime.text = wednesdayTimings
        binding.thursdayTime.text = thursdayTimings
        binding.fridayTime.text = fridayTimings
        binding.saturdayTime.text = saturdayTimings
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setDimens(view: View) {
        view.layoutParams.width = context?.getDialogWidth() ?: 200
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded)
        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss()
                true
            } else false
        }
        return dialog
    }
}