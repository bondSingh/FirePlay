package saty.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import saty.firebase.databinding.ActivityAllPersonsBinding

class AllPersons : AppCompatActivity(), PersonItemClickInterface {

    private lateinit var binding: ActivityAllPersonsBinding
    private lateinit var personViewModel: PersonViewModel
    private lateinit var recipeAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_persons)

        binding = ActivityAllPersonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and its adapter
        recipeAdapter = PersonAdapter(emptyList(), this) // Provide an empty list initially
        binding.recyclerview.adapter = recipeAdapter

        // Initialize ViewModel
        val recipeApiService: PersonApiService = RetrofitBuilder.getRetrofit(this).create(
            PersonApiService::class.java)
        val recipeRepository = PersonRepository(recipeApiService)
        personViewModel = ViewModelProvider(this, ViewModelFactory(recipeRepository))[PersonViewModel::class.java]

        personViewModel.fetchPersons()


        personViewModel.personsLiveData.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideLoading()
                        resource.data?.let {
                            recipeAdapter.persons = resource.data
                            recipeAdapter.notifyDataSetChanged()}
                    }

                    Status.ERROR -> {
                        hideLoading()
                        resource.message?.let { it1 -> Snackbar.make(binding.root, it1, Snackbar.LENGTH_SHORT).show() }
                    }

                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onPersonItemClicked(position: Int) {
        Log.d("Person Item Clicked::", "${recipeAdapter.persons[position]}")
    }
}