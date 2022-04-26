package com.kgp.myaccount.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kgp.myaccount.R
import com.kgp.myaccount.databinding.VhDayBinding
import com.kgp.myaccount.databinding.VhHistoryItemBinding
import com.kgp.myaccount.ui.historytype.DayItem
import com.kgp.myaccount.ui.historytype.HistoryBaseItem
import com.kgp.myaccount.ui.historytype.HistoryItem
import com.kgp.myaccount.utils.DateUtil
import com.kgp.myaccount.utils.MoneyUtil

class HistoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<HistoryBaseItem>()

    inner class HistoryViewHolder(private val binding: VhHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyItem: HistoryItem) {

            val hour = historyItem.localDateTime.hour
            val minute = historyItem.localDateTime.minute
            val timeText = String.format("%02d:%02d", hour, minute)
            binding.time.text = timeText

            binding.detail.text = historyItem.detail

            historyItem.money.also { money ->
                val formattedMoney = MoneyUtil.formatMoney(money)

                if (!historyItem.shouldIgnore) {
                    val c = if (money < 0) {
                        ContextCompat.getColor(itemView.context, R.color.blue)
                    } else {
                        ContextCompat.getColor(itemView.context, R.color.red)
                    }
                    binding.money.text = formattedMoney
                    binding.money.setTextColor(c)
                } else {
                    binding.money.text = formattedMoney
                    binding.money.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey_dark))
                }
            }
        }
    }

    inner class DayViewHolder(private val binding: VhDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dayItem: DayItem) {
            val day = dayItem.day.dayOfMonth
            val dayOfWeekStr = DateUtil.dayOfWeekToWord(dayItem.day.dayOfWeek)
            val dayText = "$day($dayOfWeekStr)"
            binding.day.text = dayText

            //TODO 가공 해서 줄 수 없나
            val formattedMoney = MoneyUtil.formatMoney(dayItem.sumOfDay)
            val moneyText = "총합 $formattedMoney 원"
            binding.net.text = moneyText

            val c = if (dayItem.sumOfDay < 0) {
                ContextCompat.getColor(itemView.context, R.color.blue)
            } else {
                ContextCompat.getColor(itemView.context, R.color.red)
            }
            binding.net.setTextColor(c)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HistoryType.DAY.ordinal) {
            val vh = VhDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DayViewHolder(vh)
        } else {
            val vh = VhHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HistoryViewHolder(vh)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is DayItem) {
            HistoryType.DAY.ordinal
        } else {
            HistoryType.HISTORY.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (items[position] is DayItem) {
            (holder as DayViewHolder).bind(items[position] as DayItem)
        } else if (items[position] is HistoryItem) {
            (holder as HistoryViewHolder).bind(items[position] as HistoryItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    enum class HistoryType {
        DAY, HISTORY
    }
}