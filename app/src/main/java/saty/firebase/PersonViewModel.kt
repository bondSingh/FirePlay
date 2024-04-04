package saty.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository) : ViewModel(){
    private val TAG = "PersonViewModel"
    private val _personsLiveData = MutableLiveData<Resource<List<Person>>>()
    val personsLiveData: LiveData<Resource<List<Person>>> get() = _personsLiveData

    lateinit var fireStore : FirebaseFirestore

    fun fetchPersons() {
        viewModelScope.launch {
            _personsLiveData.value = Resource.loading(data = null)
            try {
                fireStore = FirebaseFirestore.getInstance()

                val docRef = fireStore.collection("person")
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val personList = mutableListOf<Person>()
                            Log.d(TAG, "DocumentSnapshot data: ${document.documents}")
                            for (data in  document.documents){
                                val person = Person()
                                person.name = data?.get("name") as String
                                //Firebase stores Int as a Long. Converting it back to Int
                                person.age = (data.get("age") as Long).toInt()
                                person.place = data.get("place") as String
                                person.degree = data.get("degree") as String
                                person.language = data.get("language") as String
                                personList.add(person)
                            }
                            _personsLiveData.value = Resource.success(personList)

                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            } catch (noNet : NoConnectivityException){
                _personsLiveData.value =
                    Resource.error(data = null, message = noNet.message.toString())
            }
            catch (e: Exception) {
                if (e.message != null) {
                    _personsLiveData.value =
                        Resource.error(data = null, message = e.message.toString())
                } else {
                    _personsLiveData.value = Resource.error(
                        data = null,
                        message = "Something went wrong. Please try again later."
                    )
                }
            }
        }
    }

}

