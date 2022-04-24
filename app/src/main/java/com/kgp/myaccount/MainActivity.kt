package com.kgp.myaccount

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kgp.myaccount.databinding.ActivityMainBinding
import com.kgp.myaccount.ui.HistoryListAdapter
import com.kgp.myaccount.ui.HistoryViewModel
import com.kgp.myaccount.ui.baseclass.BaseActivity
import com.kgp.myaccount.ui.edit.EditHistoryActivity
import kotlinx.coroutines.flow.collect
import org.koin.core.component.inject

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    private val historyViewModel: HistoryViewModel by inject()

    private val historyAdapter = HistoryListAdapter()

    private val newHistoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }

        val extras = it.data?.extras ?: return@registerForActivityResult
        val money = extras.getLong("money") ?: return@registerForActivityResult
        val detail = extras.getString("detail") ?: return@registerForActivityResult


        //TODO 데이터 필터링
        historyViewModel.insert(money, detail, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newHistory.setOnClickListener {
            val intent = Intent(this, EditHistoryActivity::class.java)
            newHistoryLauncher.launch(intent)
        }
        initHistoryRecyclerView()
        observeEvents()


    }

    private fun observeEvents() {
        lifecycleScope.launchWhenCreated {
            historyViewModel.getAllHistories().collect {
                historyAdapter.items = it
                historyAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initHistoryRecyclerView() {
        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}