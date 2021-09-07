package com.example.skuadtestapp.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.ActivitySearchBinding
import com.example.skuadtestapp.search.callbacks.ISearchRecyclerCallBack
import com.example.skuadtestapp.utils.isNetworkAvailable
import com.example.skuadtestapp.utils.showToast

class SearchActivity : AppCompatActivity(), ISearchRecyclerCallBack {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        init()
    }

    private fun init() {
        if (isNetworkAvailable(this))
        else showToast(this, getString(R.string.no_network))
    }

    /*

    todo recycler view */
    override fun onSelectedRecyclerClick(position: Int) {

    }
}