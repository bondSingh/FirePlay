package saty.firebase

class PersonRepository (private val apiService: PersonApiService) {
    suspend fun getPersons(): List<Person> {
        return apiService.getRecipes()
    }
}
