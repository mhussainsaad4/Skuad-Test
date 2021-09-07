package com.example.skuadtestapp.home.viewModel

import NearbyApiModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.skuadtestapp.home.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AndroidEntryPoint
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
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