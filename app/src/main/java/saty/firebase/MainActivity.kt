package saty.firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import saty.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var fireStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fireStore = FirebaseFirestore.getInstance()

        val person  = Person()
        binding.submit.setOnClickListener {
            person.name = binding.name.text.toString()
            person.age = binding.age.text.toString().toInt()
            person.place = binding.place.text.toString()
            person.degree = binding.degree.text.toString()
            person.language = binding.language.text.toString()


            fireStore.collection("person")
                .add(person)
                .addOnSuccessListener{
                    println("Added Successfully: $person")
                }
                .addOnFailureListener {
                    println("Something went Wrong")
                }
            println("Submit clicked")



        }


      println("value of error" +Status.valueOf("ERROR"))
      println("value of array" +Status.values())


        binding.goToAllPersons.setOnClickListener {
            startActivity(Intent(this, AllPersons::class.java))
        }

    }

}