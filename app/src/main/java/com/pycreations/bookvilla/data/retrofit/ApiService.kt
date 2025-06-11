package com.pycreations.bookvilla.data.retrofit

import com.pycreations.bookvilla.data.models.GenreResponseModel
import com.pycreations.bookvilla.data.models.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun getByGenre(
        @Query("q") genre : String
    ) : GenreResponseModel

    @GET("volumes")
    suspend fun getByTrending(
        @Query("q") genre : String,
        @Query("orderBy") newest : String
    ) : GenreResponseModel

    @GET("volumes/{id}")
    suspend fun getBookById(
        @Path("id") volumeId: String
    ): Item
}