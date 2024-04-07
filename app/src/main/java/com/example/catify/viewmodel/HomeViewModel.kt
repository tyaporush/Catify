package com.example.catify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.catify.model.CatUIModel
import com.example.catify.model.ImageSize
import com.example.catify.usecase.CatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catsUseCase: CatsUseCase
) : ViewModel() {

    private val _homeScreenState: MutableStateFlow<PagingData<CatUIModel>> =
        MutableStateFlow(value = PagingData.empty())
    val homeScreenState: MutableStateFlow<PagingData<CatUIModel>> get() = _homeScreenState

    init {
        onEvent(HomeEvents.FetchCats)
    }

    private fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.FetchCats -> {
                viewModelScope.launch(Dispatchers.IO) {
                    catsUseCase()
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .mapNotNull {
                            it.map { catItem ->
                                with(catItem) {
                                    CatUIModel(
                                        id = id,
                                        size = ImageSize(
                                            width = width.toIntOrNull() ?: 100,
                                            height = height.toIntOrNull() ?: 100
                                        ),
                                        imageUrl = imageUrl
                                    )
                                }
                            }
                        }
                        .collect {
                            _homeScreenState.value = it
                        }
                }
            }
        }
    }

    sealed class HomeEvents {
        object FetchCats : HomeEvents()
    }

}