package com.kgp.myaccount.ui.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.kgp.myaccount.R
import com.kgp.myaccount.databinding.ActivityNewHistoryBinding
import com.kgp.myaccount.ui.baseclass.BaseActivity
import org.koin.android.ext.android.inject
import java.time.DayOfWeek

class NewOrEditHistoryActivity : BaseActivity() {
    companion object {
        const val KEY_MONEY = "money"
        const val KEY_DETAIL = "detail"
        const val KEY_MODE = "mode"
        const val KEY_DATE_TIME = "dateTime"
    }

    private lateinit var binding: ActivityNewHistoryBinding
    private val editHistoryViewModel: EditHistoryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        addListener()

        observeEvents()
    }

    private fun initView() {
        binding.moneyEdit.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.toString().toLong() == 0L) {
                binding.moneyEdit.setText("")
            }
        }
    }

    private fun observeEvents() {
        editHistoryViewModel.historyMode.observe(this) {
            setDefaultModeColor()

            activateModeColor(it)
        }

        editHistoryViewModel.currentDateTime.observe(this) {
            val year = it.year
            val month = it.monthValue
            val day = it.dayOfMonth
            val dayOfWeek = when (it.dayOfWeek) {
                DayOfWeek.SUNDAY -> "일"
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
                else -> "일"
            }
            val hour = it.hour
            val minute = it.minute

            val dateFormat = String.format("%d-%02d-%02d(%s)", year, month, day, dayOfWeek)
            val timeFormat = String.format("%02d:%02d", hour, minute)
            binding.dateEdit.setText(dateFormat)
            binding.timeEdit.setText(timeFormat)
        }
    }

    private fun activateModeColor(mode: HistoryMode) {
        when (mode) {
            HistoryMode.EXPENSE -> {
                binding.modeExpense.apply {
                    borderColor = getColor(R.color.blue)
                    clippedBackgroundColor = getColor(R.color.blue_light)
                    binding.expenseText.setTextColor(getColor(R.color.blue))
                }
            }
            HistoryMode.INCOME -> {
                binding.modeIncome.apply {
                    borderColor = getColor(R.color.red)
                    clippedBackgroundColor = getColor(R.color.red_light)
                    binding.incomeText.setTextColor(getColor(R.color.red))
                }
            }

            HistoryMode.IGNORE -> {
                binding.modeIgnore.apply {
                    borderColor = getColor(R.color.black)
                    clippedBackgroundColor = getColor(R.color.white)
                    binding.ignoreText.setTextColor(getColor(R.color.black))
                }
            }
        }
    }

    private fun setDefaultModeColor() {
        val grey = getColor(R.color.grey)
        val greyDark = getColor(R.color.grey_dark)

        binding.modeExpense.apply {
            borderColor = greyDark
            clippedBackgroundColor = grey
            binding.expenseText.setTextColor(greyDark)
        }
        binding.modeIncome.apply {
            borderColor = greyDark
            clippedBackgroundColor = grey
            binding.incomeText.setTextColor(greyDark)
        }
        binding.modeIgnore.apply {
            borderColor = greyDark
            clippedBackgroundColor = grey
            binding.ignoreText.setTextColor(greyDark)
        }
    }

    private fun showDatePicker() {
        val currentLocalDate = editHistoryViewModel.currentDateTime.value
        val callback = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            editHistoryViewModel.changeDate(year, month, dayOfMonth)
        }
        currentLocalDate?.also {
            val dialog = DatePickerDialog(this, callback, it.year, it.monthValue - 1, it.dayOfMonth)
            dialog.show()
        }
    }

    private fun showTimePicker() {
        val currentLocalDate = editHistoryViewModel.currentDateTime.value
        val callback = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            editHistoryViewModel.changeTime(hourOfDay, minute)
        }
        currentLocalDate?.also {
            val dialog = TimePickerDialog(this, callback, it.hour, it.minute, true)
            dialog.show()
        }
    }

    private fun addListener() {
        binding.modeExpense.setOnClickListener {
            editHistoryViewModel.changeMode(HistoryMode.EXPENSE)
        }

        binding.modeIncome.setOnClickListener {
            editHistoryViewModel.changeMode(HistoryMode.INCOME)
        }

        binding.modeIgnore.setOnClickListener {
            editHistoryViewModel.changeMode(HistoryMode.IGNORE)
        }

        binding.dateEdit.setOnClickListener {
            showDatePicker()
        }

        binding.timeEdit.setOnClickListener {
            showTimePicker()
        }

        binding.done.setOnClickListener {
            Intent().apply {
                if (!availableInput()) {
                    return@setOnClickListener
                }

                val money = binding.moneyEdit.text.toString().toLong()
                val detail = binding.detailEdit.text.toString()
                val mode = editHistoryViewModel.historyMode.value
                val date = editHistoryViewModel.currentDateTime.value

                putExtra(KEY_MONEY, money)
                putExtra(KEY_DETAIL, detail)
                putExtra(KEY_MODE, mode)
                putExtra(KEY_DATE_TIME, date)

                setResult(RESULT_OK, this)

                finish()
            }
        }

        binding.cancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun availableInput(): Boolean {
        val moneyText = binding.moneyEdit.text.toString()

        if (moneyText.isBlank() || moneyText.toLong() == 0L) {
            Toast.makeText(this, "금액을 입력해주세요!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}