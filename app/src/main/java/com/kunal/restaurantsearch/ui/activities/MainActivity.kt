package com.kunal.restaurantsearch.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunal.restaurantsearch.databinding.ActivityMainBinding
import com.kunal.restaurantsearch.ui.adapters.SearchListAdapter
import com.kunal.restaurantsearch.ui.base.BaseActivity
import com.kunal.restaurantsearch.ui.fragments.QuitAppDialogFragment
import com.kunal.restaurantsearch.ui.fragments.ShowDetailsDialogFragment
import com.kunal.restaurantsearch.utils.gone
import com.kunal.restaurantsearch.utils.visible
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val _searchListAdapter by lazy {
        SearchListAdapter(
            emptyList()
        ) { restaurant ->
            val showDetailsDialogFragment = ShowDetailsDialogFragment.newInstance(restaurant)
            showDetailsDialogFragment.show(supportFragmentManager, ShowDetailsDialogFragment.TAG)
        }
    }

    private var searchListAdapter: SearchListAdapter? = null
        get() {
            kotlin.runCatching {
                field = _searchListAdapter
            }.onFailure {
                Timber.d("Error: $it")
                field = null
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initializeData()
        initializeViews()
        initializeObservers()
    }

    override fun initializeObservers() {
        mainViewModel.searchResult.observe(this) { restaurantList ->
            if (restaurantList.isNotEmpty()) {
                Timber.d("List:$restaurantList")
                searchListAdapter?.clearList()
                searchListAdapter?.updateList(restaurantList)
                hideSearchNotFoundPlaceholder()
                hideStartSearchingPlaceHolder()
            } else {
                searchListAdapter?.clearList()
                showSearchNotFoundPlaceholder()
                hideStartSearchingPlaceHolder()
            }
        }
    }

    override fun initializeViews() {
        showStartSearchingPlaceHolder()
        initializeSearch()
        initializeSearchList()
    }

    private fun initializeSearchList() {
        binding.searchResultsList.apply {
            adapter = searchListAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initializeSearch() {
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length >= 2) {
                    mainViewModel.performSearch(s.trim().toString())
                    hideStartSearchingPlaceHolder()
                    binding.clearText.visible()
                    return
                }
                if (s.length == 1) {
                    searchListAdapter?.clearList()
                    showStartSearchingPlaceHolder()
                    hideSearchNotFoundPlaceholder()
                    binding.clearText.visible()
                    return
                }
                if (s.isEmpty()) {
                    searchListAdapter?.clearList()
                    showStartSearchingPlaceHolder()
                    hideSearchNotFoundPlaceholder()
                    binding.clearText.gone()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        binding.clearText.setOnClickListener {
            binding.searchET.text.clear()
            binding.clearText.gone()
        }
    }

    private fun showStartSearchingPlaceHolder() {
        binding.startSearchingPlaceholder.visible()
    }

    private fun hideStartSearchingPlaceHolder() {
        binding.startSearchingPlaceholder.gone()
    }

    private fun showSearchNotFoundPlaceholder() {
        binding.searchNotFoundPlaceholder.visible()
    }

    private fun hideSearchNotFoundPlaceholder() {
        binding.searchNotFoundPlaceholder.gone()
    }

    override fun initializeData() {
        mainViewModel.initializeJsonData(this)
    }

    override fun onBackPressed() {
        val quitAppDialogFragment = QuitAppDialogFragment.newInstance().apply {
            isCancelable = false
        }.also {
            it.onQuitClicked = {
                super.onBackPressed()
            }
        }
        quitAppDialogFragment.show(supportFragmentManager, QuitAppDialogFragment.TAG)
    }

}