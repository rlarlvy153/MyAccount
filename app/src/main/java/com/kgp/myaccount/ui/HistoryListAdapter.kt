package com.kgp.myaccount.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kgp.myaccount.databinding.VhHistoryItemBinding

class HistoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<HistoryItem>()

    inner class HistoryViewHolder(private val binding: VhHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyItem: HistoryItem) {
            binding.money.text = historyItem.money.toString()
        }
    }

    inner class DayViewHolder() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = VhHistoryItemBinding.inflate(LayoutInflater.from(parent.context))
        return HistoryViewHolder(vh)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HistoryViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}