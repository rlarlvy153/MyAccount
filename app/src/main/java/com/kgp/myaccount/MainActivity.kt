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
import com.kgp.myaccount.ui.edit.HistoryMode
import com.kgp.myaccount.ui.edit.NewOrEditHistoryActivity
import kotlinx.coroutines.flow.collect
import org.koin.core.component.inject
import java.time.LocalDateTime

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    private val historyViewModel: HistoryViewModel by inject()

    private val historyAdapter = HistoryListAdapter()

    private val newHistoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }

        val extras = it.data?.extras ?: return@registerForActivityResult
        val money = extras.getLong(NewOrEditHistoryActivity.KEY_MONEY, 0)
        val detail = extras.getString(NewOrEditHistoryActivity.KEY_DETAIL) ?: return@registerForActivityResult
        val mode = extras.getSerializable(NewOrEditHistoryActivity.KEY_MODE) as? HistoryMode ?: return@registerForActivityResult
        val dateTime = extras.getSerializable(NewOrEditHistoryActivity.KEY_DATE_TIME) as? LocalDateTime ?: return@registerForActivityResult

        historyViewModel.insert(money, detail, mode, dateTime)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newHistory.setOnClickListener {
            val intent = Intent(this, NewOrEditHistoryActivity::class.java)
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