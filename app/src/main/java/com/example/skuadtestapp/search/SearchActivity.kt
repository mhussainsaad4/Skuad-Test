package com.example.skuadtestapp.search

import SearchResults
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.ActivitySearchBinding
import com.example.skuadtestapp.search.adapters.SearchRecyclerAdapter
import com.example.skuadtestapp.search.callbacks.ISearchRecyclerCallBack
import com.example.skuadtestapp.search.viewModel.SearchViewModel
import com.example.skuadtestapp.utils.isNetworkAvailable
import com.example.skuadtestapp.utils.manageProgressBar
import com.example.skuadtestapp.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), View.OnClickListener, ISearchRecyclerCallBack {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerAdapter: SearchRecyclerAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkForNetwork()
    }

    private fun checkForNetwork() {
        if (isNetworkAvailable(this)) {
            initCode()
            updateUI()
            initRecycler()
            defineRecycler()
        } else showToast(this, getString(R.string.no_network))
    }

    private fun initCode() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.include.editText.addTextChangedListener(GenericTextWatcher(binding.include.editText))
        binding.include.ivCloseSearch.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    private fun updateUI() {
        binding.include.tvOpenSearchActivity.visibility = View.GONE
        binding.include.editText.visibility = View.VISIBLE
        binding.include.ivCloseSearch.visibility = View.INVISIBLE
    }

    private fun updateClearUI() {
        binding.include.ivCloseSearch.visibility = View.INVISIBLE
        recyclerAdapter.setResultsList(mutableListOf<SearchResults>())
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        if (v == binding.include.ivCloseSearch) {
            binding.include.editText.setText("")
            updateClearUI()
        } else if (v == binding.ivBack) onBackPressed()
    }

    /*

    todo text watcher */

    inner class GenericTextWatcher(val v: View?) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            when (v?.id) {
                binding.include.editText.id -> {
                    var searchString: String
                    s?.let {
                        searchString = it.toString()
                        if (searchString.isEmpty()) updateClearUI()
                        else {
                            manageProgressBar(binding.contentLoadingBar, true)
                            binding.include.ivCloseSearch.visibility = View.VISIBLE
                            CoroutineScope(Dispatchers.Main).launch {
                                collectRecyclerData(searchString)
                            }
                        }
                    }
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
    /*

    todo recycler view */

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvSearch.layoutManager = layoutManager
    }

    private fun defineRecycler() {
        recyclerAdapter = SearchRecyclerAdapter(this, this)
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = recyclerAdapter
    }

    @ExperimentalCoroutinesApi
    private suspend fun collectRecyclerData(keyword: String) {
        searchViewModel.getSearchedRestaurants(keyword).collect {
            manageProgressBar(binding.contentLoadingBar, false)
            recyclerAdapter.setResultsList(it.results)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onSelectedRecyclerClick(position: Int) {

    }
}