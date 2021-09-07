package com.example.skuadtestapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skuadtestapp.R
import com.example.skuadtestapp.databinding.ActivityHomeBinding
import com.example.skuadtestapp.home.callbacks.IHomeRecyclerCallBack
import com.example.skuadtestapp.utils.isNetworkAvailable
import com.example.skuadtestapp.utils.showToast

class HomeActivity : AppCompatActivity(), IHomeRecyclerCallBack {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
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