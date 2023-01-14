package com.example.shortstories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<CustomAdapter.DataHolder>() {

    val storiesList = mutableListOf<Story>()

    fun setItems(storyList: List<Story>) {
        this.storiesList.clear()
        this.storiesList.addAll(storyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val dataHolder = DataHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.custom_layout,parent,false))
        return dataHolder
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val story = storiesList[position]
        holder.storyTitle.text = story.title
        holder.storySubTitle.text = story.subtitle
        holder.storyLetter.text = story.title.first().toString()

        generateColors(holder,position)

        holder.itemView.setOnClickListener {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    StoryDetailsFragment::class.java,
                    bundleOf(
                        "title" to story.title,
                        "subtitle" to story.subtitle,
                        "description" to story.description,
                    )
                )
                .addToBackStack("StoryDetailsFragment")
                .commit()
        }
    }
    private fun generateColors(holder: DataHolder,position: Int){
        val r = java.util.Random()
        val red = r.nextInt(255+position)
        val green = r.nextInt(255-position+1)
        val blue = r.nextInt(255+position+1)
        holder.cardViewLetter.setCardBackgroundColor(Color.rgb(red,green,blue))
    }

    override fun getItemCount(): Int {
        return storiesList.size
    }

    class DataHolder(item: View): RecyclerView.ViewHolder(item){
        val storyTitle:TextView = item.findViewById(R.id.tvTitle)
        val storySubTitle:TextView = item.findViewById(R.id.tvSubtitle)
        val storyLetter:TextView = item.findViewById(R.id.tvStoryLetter)
        val cardViewLetter:CardView = item.findViewById(R.id.circle)

    }
}