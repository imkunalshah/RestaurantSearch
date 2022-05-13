package com.kunal.restaurantsearch.ui.base

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunal.restaurantsearch.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    abstract fun initializeObservers()

    abstract fun initializeViews()

    abstract fun initializeData()

}