package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding


class AsteroidAdapter (val clickListener: AsteroidListener): RecyclerView.Adapter<AsteroidAdapter.ViewHolder>()

{
    //var data = listOf<Asteroid>()
    val asteroid1 = Asteroid(1L,"rumpelstiltskin","sec",1.6,1.7,
        1.8,1.9,true)
    val asteroid2 = Asteroid(1L,"rumpelstiltskin1000","sec",1.6,1.7,
        1.8,1.9,true)
    val asteroid3 = Asteroid(1L,"rumpelstiltskinrumpelstiltskin","sec",1.6,1.7,
        1.8,1.9,true)
    var asteroidList = mutableListOf<Asteroid>()

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        //asteroidList.add(asteroid1)
        //asteroidList.add(asteroid2)
        //asteroidList.add(asteroid3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_asteroid, parent, false)
        return ViewHolder.from(parent)
    }

    override fun getItemCount()= asteroidList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = asteroidList[position]
        val res = holder.itemView.context.resources
        holder.codename.text = item.codename
        holder.closeappraochdate.text = item.closeApproachDate
        holder.bind(clickListener,item)
    }
    //class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    class ViewHolder private constructor(val binding: ListItemAsteroidBinding)
            : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: AsteroidListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
        val codename: TextView = itemView.findViewById(R.id.code_name)
        val closeappraochdate: TextView = itemView.findViewById(R.id.close_approach_date)

}
}
class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
