package com.example.skuadtestapp.search.viewModel

import SearchApiModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.skuadtestapp.search.repository.SearchRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AndroidEntryPoint
class SearchViewModel(application: Application) : AndroidViewModel(application) {
    
    @Inject
    private lateinit var searchRepository: SearchRepository

    init {
        searchRepository = SearchRepository(application)
    }

    suspend fun getSearchedRestaurants(keyword: String): Flow<SearchApiModel> = flow {
        searchRepository.getSearchedRestaurants(keyword).collect {
            emit(it)
        }
    }
}