package com.example.skuadtestapp.home.viewModel

import NearbyApiModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.skuadtestapp.home.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var homeRepository: HomeRepository

    init {
        homeRepository = HomeRepository(application)
    }

    @ExperimentalCoroutinesApi
    suspend fun getNearbyRestaurants(): Flow<NearbyApiModel> = flow {
        homeRepository.getNearbyRestaurants().collect {
            emit(it)
        }
    }

}