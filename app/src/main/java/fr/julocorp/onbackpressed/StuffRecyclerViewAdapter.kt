package fr.julocorp.onbackpressed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StuffRecyclerViewAdapter(
    private val bunchOfStuff: List<String>
) : RecyclerView.Adapter<StuffRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.stuff_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idView.text = bunchOfStuff[position]
    }

    override fun getItemCount(): Int = bunchOfStuff.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.stuffItem)
    }
}