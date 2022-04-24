package com.kgp.myaccount.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kgp.myaccount.databinding.ActivityNewHistoryBinding

class NewHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.done.setOnClickListener {
            Intent().apply {
                val money = binding.moneyEdit.text.toString().toLong()
                val detail = binding.detailEdit.text.toString()

                putExtra("money", money)
                putExtra("detail", detail)

                setResult(RESULT_OK, this)

                finish()
            }
        }
    }
}