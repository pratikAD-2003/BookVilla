package com.pycreations.bookvilla.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pycreations.bookvilla.data.models.GenreResponseModel
import com.pycreations.bookvilla.data.models.Item
import com.pycreations.bookvilla.data.repository.BookRepository
import com.pycreations.bookvilla.data.retrofit.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class GetGenreState {
    object Idle : GetGenreState()
    object Loading : GetGenreState()
    data class Success(val response: GenreResponseModel) : GetGenreState()
    data class Error(val error: String) : GetGenreState()
}

sealed class GetSpecificBookState {
    object Idle : GetSpecificBookState()
    object Loading : GetSpecificBookState()
    data class Success(val response: Item) : GetSpecificBookState()
    data class Error(val error: String) : GetSpecificBookState()
}

sealed class GetMultipleByIdBookState {
    object Loading : GetMultipleByIdBookState()
    data class Success(val response: Item) : GetMultipleByIdBookState()
    data class SuccessMultiple(val response: List<Item>) : GetMultipleByIdBookState()
    data class Error(val error: String) : GetMultipleByIdBookState()
    object Idle : GetMultipleByIdBookState()
}


class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    private val _getGenreState = MutableStateFlow<GetGenreState>(GetGenreState.Idle)
    val getGenreState: StateFlow<GetGenreState> get() = _getGenreState

    private val _getTrendingState = MutableStateFlow<GetGenreState>(GetGenreState.Idle)
    val getTrendingState: StateFlow<GetGenreState> get() = _getTrendingState

    private val _getSpecificBookState =
        MutableStateFlow<GetSpecificBookState>(GetSpecificBookState.Idle)
    val getSpecificBookState: StateFlow<GetSpecificBookState> get() = _getSpecificBookState

    private val _getSearchState = MutableStateFlow<GetGenreState>(GetGenreState.Idle)
    val getSearchState: StateFlow<GetGenreState> get() = _getSearchState

    private val _getSearchLimitState = MutableStateFlow<GetGenreState>(GetGenreState.Idle)
    val getSearchLimitState: StateFlow<GetGenreState> get() = _getSearchLimitState


    private val _getMultipleBooksByIdState =
        MutableStateFlow<GetMultipleByIdBookState>(GetMultipleByIdBookState.Idle)
    val getMultipleBooksByIdState: StateFlow<GetMultipleByIdBookState> get() = _getMultipleBooksByIdState


    fun getGenre(query: String, si: Int, ei: Int) {
        viewModelScope.launch {
            _getGenreState.value = GetGenreState.Loading
            try {
                val response = bookRepository.searchBook(query, si, ei)
                _getGenreState.value = GetGenreState.Success(response)
            } catch (e: Exception) {
                _getGenreState.value = GetGenreState.Error("genre error " + e.message)
            }
        }
    }

    fun resetGetGenre() {
        _getGenreState.value = GetGenreState.Idle

    }

    fun getSearchLimit(query: String, si: Int, ei: Int) {
        viewModelScope.launch {
            _getSearchLimitState.value = GetGenreState.Loading
            try {
                val response = bookRepository.searchBook(query, si, ei)
                _getSearchLimitState.value = GetGenreState.Success(response)
            } catch (e: Exception) {
                _getSearchLimitState.value = GetGenreState.Error("genre error " + e.message)
            }
        }
    }

    fun resetSearchLimitState() {
        _getSearchLimitState.value = GetGenreState.Idle
    }

    fun getTrending(query: String, si: Int, ei: Int) {
        viewModelScope.launch {
            _getTrendingState.value = GetGenreState.Loading
            try {
                val response = bookRepository.getByTrending(query, si, ei)
                _getTrendingState.value = GetGenreState.Success(response)
            } catch (e: Exception) {
                _getTrendingState.value = GetGenreState.Error("genre trending error " + e.message)
            }
        }
    }

    fun resetTrendingGenre() {
        _getTrendingState.value = GetGenreState.Idle
    }

    fun getBookById(id: String) {
        viewModelScope.launch {
            _getSpecificBookState.value = GetSpecificBookState.Loading
            try {
                val response = bookRepository.getByBookId(id)
                _getSpecificBookState.value = GetSpecificBookState.Success(response)
            } catch (e: Exception) {
                _getSpecificBookState.value =
                    GetSpecificBookState.Error("genre book by id error " + e.message)
            }
        }
    }

    fun resetBookByIdGenre() {
        _getSpecificBookState.value = GetSpecificBookState.Idle
    }

    fun fetchMultipleBooksByIds(ids: List<String>) {
        viewModelScope.launch {
            _getMultipleBooksByIdState.value = GetMultipleByIdBookState.Loading
            try {
                val bookList = ids.map { id ->
                    apiService.getBookById(id)
                }
                _getMultipleBooksByIdState.value =
                    GetMultipleByIdBookState.SuccessMultiple(bookList)
            } catch (e: Exception) {
                _getMultipleBooksByIdState.value =
                    GetMultipleByIdBookState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetMultipleBooks() {
        _getMultipleBooksByIdState.value = GetMultipleByIdBookState.Idle
    }
}