package com.example.skuadtestapp.home

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.ActivityHomeBinding
import com.example.skuadtestapp.home.adapters.HomeRecyclerAdapter
import com.example.skuadtestapp.home.callbacks.IHomeRecyclerCallBack
import com.example.skuadtestapp.home.viewModel.HomeViewModel
import com.example.skuadtestapp.search.SearchActivity
import com.example.skuadtestapp.utils.isNetworkAvailable
import com.example.skuadtestapp.utils.manageProgressBar
import com.example.skuadtestapp.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class HomeActivity : AppCompatActivity(), View.OnClickListener, IHomeRecyclerCallBack {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerAdapter: HomeRecyclerAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkForNetwork()
    }

    private fun checkForNetwork() {
        if (isNetworkAvailable(this)) {
            initCode()
            updateUI()
            initRecycler()
            defineRecycler()
            manageProgressBar(binding.contentLoadingBar, true)
            CoroutineScope(Dispatchers.Main).launch {
                collectRecyclerData()
            }
        } else showToast(this, getString(R.string.no_network))
    }

    private fun initCode() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.include.tvOpenSearchActivity.setOnClickListener(this)
    }

    private fun updateUI() {
        binding.include.ivCloseSearch.visibility = View.INVISIBLE
    }

    override fun onClick(v: View?) {
        if (v == binding.include.tvOpenSearchActivity) startActivity(Intent(this, SearchActivity::class.java))
    }

    /*

    todo: recycler view          */

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvNearby.layoutManager = layoutManager
    }

    private fun defineRecycler() {
        recyclerAdapter = HomeRecyclerAdapter(this, this)
        binding.rvNearby.setHasFixedSize(true)
        binding.rvNearby.adapter = recyclerAdapter
    }

    @ExperimentalCoroutinesApi
    private suspend fun collectRecyclerData() {
        homeViewModel.getNearbyRestaurants().collect {
            manageProgressBar(binding.contentLoadingBar, false)
            recyclerAdapter.setResultsList(it.results)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onSelectedRecyclerClick(position: Int) {

    }
}