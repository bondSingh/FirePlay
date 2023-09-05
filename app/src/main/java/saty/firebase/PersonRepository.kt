package saty.firebase

class PersonRepository (private val apiService: PersonApiService) {
    suspend fun getRecipes(): List<Person> {
        return apiService.getRecipes()
    }
}
