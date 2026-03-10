package com.example.myapplication.presintation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemStationBinding
import domain.models.Stations
import domain.usecase.GetDirectionUseCase

class RouteAdapter(
    private val getDirectionUseCase: GetDirectionUseCase
) : RecyclerView.Adapter<RouteAdapter.StationViewHolder>() {

    private var stations: List<Stations> = emptyList()

    fun submitList(list: List<Stations>) {
        stations = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val current = stations[position]
        holder.binding.tvStationName.text = current.name
        holder.binding.tvLineName.text = current.line.name

        holder.binding.viewLineTop.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        holder.binding.viewLineBottom.visibility = if (position == stations.size - 1) View.INVISIBLE else View.VISIBLE

        if (position < stations.size - 1) {
            val next = stations[position + 1]
            if (current.name == next.name && current.line != next.line) {
                val direction = if (position + 2 < stations.size) {
                    val afterTransfer = stations[position + 2]
                    getDirectionUseCase.execute(next, afterTransfer)
                } else ""
                
                holder.binding.tvTransferHint.visibility = View.VISIBLE
                holder.binding.tvTransferHint.text = "Transfer to ${next.line.name} towards $direction"
            } else {
                holder.binding.tvTransferHint.visibility = View.GONE
            }
        } else {
            holder.binding.tvTransferHint.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = stations.size

    class StationViewHolder(val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root)
}
