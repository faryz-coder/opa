package com.bit.onlinepandemicapps

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(private val state: MutableList<State>) : RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val stateName: TextView = itemView.findViewById(R.id.tableState)
        val zone: TextView = itemView.findViewById(R.id.tableZone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_table, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stateName.text = state[position].state
        holder.zone.text = state[position].status
        if (state[position].status.toUpperCase() == "RED") {
            holder.zone.setTextColor(Color.RED)
        } else if (state[position].status.toUpperCase() == "GREEN") {
            holder.zone.setTextColor(Color.GREEN)
        } else if (state[position].status.toUpperCase() == "YELLOW") {
            holder.zone.setTextColor(Color.YELLOW)
        }
    }

    override fun getItemCount() = state.size

}
