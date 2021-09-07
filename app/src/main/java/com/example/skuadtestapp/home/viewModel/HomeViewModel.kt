package com.example.skuadtestapp.home.viewModel

import NearbyApiModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.skuadtestapp.home.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var homeRepository: HomeRepository

    init {
        homeRepository = HomeRepository(application)
    }

    suspend fun getNearbyRestaurants(): Flow<NearbyApiModel> = flow {
        homeRepository.getNearbyRestaurants().collect {
            emit(it)
        }
    }

}