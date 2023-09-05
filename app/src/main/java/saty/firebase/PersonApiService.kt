package saty.firebase

import retrofit2.http.GET

interface PersonApiService {
    @GET("recipes.json")
    suspend fun getRecipes(): List<Person>
}
