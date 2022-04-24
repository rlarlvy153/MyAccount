package com.kgp.myaccount.ui.edit

import android.content.Intent
import android.os.Bundle
import com.kgp.myaccount.R
import com.kgp.myaccount.databinding.ActivityNewHistoryBinding
import com.kgp.myaccount.ui.baseclass.BaseActivity
import org.koin.android.ext.android.inject

class EditHistoryActivity : BaseActivity() {
    private lateinit var binding: ActivityNewHistoryBinding
    private val editHistoryViewModel: EditHistoryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListener()

        observeEvents()
    }

    private fun observeEvents() {
        editHistoryViewModel.historyMode.observe(this) {
            setDefaultModeColor()

            activateModeColor(it)
        }
    }

    private fun activateModeColor(mode: HistoryMode) {
        when(mode){
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

        binding.done.setOnClickListener {
            Intent().apply {
                if (!availableInput()) {
                    return@setOnClickListener
                }

                val money = binding.moneyEdit.text.toString().toLong()
                val detail = binding.detailEdit.text.toString()

                putExtra("money", money)
                putExtra("detail", detail)

                setResult(RESULT_OK, this)

                finish()
            }
        }
    }

    private fun availableInput(): Boolean {
        val moneyText = binding.moneyEdit.text.toString()
        val detail = binding.detailEdit.text.toString()

        if (moneyText.isBlank()) {
            return false
        }

        return true
    }
}