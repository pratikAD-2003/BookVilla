package com.pycreations.bookvilla.data.repository

import com.pycreations.bookvilla.data.models.GenreResponseModel
import com.pycreations.bookvilla.data.models.Item
import com.pycreations.bookvilla.data.retrofit.RetrofitClient.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository {
    suspend fun getByGenre(query : String) : GenreResponseModel{
        return withContext(Dispatchers.IO) {
            apiService.getByGenre(query)
        }
    }

    suspend fun getByTrending(query: String) : GenreResponseModel{
        return withContext(Dispatchers.IO) {
            apiService.getByTrending(query,"newest")
        }
    }

    suspend fun getByBookId(id: String) : Item{
        return withContext(Dispatchers.IO) {
            apiService.getBookById(id)
        }
    }
}