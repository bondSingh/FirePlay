package saty.firebase

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import saty.firebase.databinding.DateItemBinding
import saty.firebase.databinding.PersonItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PersonAdapter(var persons: List<Person>, private val clickInterface: PersonItemClickInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_DATE = 0
    private val VIEW_TYPE_PERSON = 1

    inner class PersonViewHolder(val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DateViewHolder(val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //returning different ViewHolder depending on the viewtype.
        return if (viewType == VIEW_TYPE_DATE) {
            val dateBinding =
                DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateViewHolder(dateBinding)
        } else {
            val personBinding =
                PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PersonViewHolder(personBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //binding the view based on the viewHolder type
        when (holder) {
            is PersonViewHolder -> {
                val person = persons[position - 1]
                val res: Resources = Resources.getSystem()

                holder.binding.name.text = res.getString(R.string.name, person.name)
                holder.binding.age.text = res.getString(R.string.age, person.age)
                holder.binding.place.text = res.getString(R.string.place, person.place)
                holder.binding.degree.text = res.getString(R.string.degree, person.degree)
                holder.binding.language.text = res.getString(R.string.language, person.language)

               /* Glide.with(holder.itemView)
                    .load(recipe.imageUrl)
                    .centerCrop()
                    .into(holder.binding.image)*/
                holder.itemView.setOnClickListener{
                    clickInterface.onPersonItemClicked(position)
                }
            }

            is DateViewHolder -> {
                holder.binding.dateText.text = "Date : " + getCurrentDate()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        //Setting view type to Date to display date as the first element of the recyclerview
        return if (position == 0) VIEW_TYPE_DATE else VIEW_TYPE_PERSON
    }

    override fun getItemCount(): Int {
        //adding one to accommodate for the extra date item at the top.
        return persons.size + 1
    }

    private fun getCurrentDate(): String {
        val currentDate = Date()
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        return sdf.format(currentDate)
    }
}
