package com.kgp.myaccount.di

import com.kgp.myaccount.ui.HistoryViewModel
import com.kgp.myaccount.ui.edit.EditHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HistoryViewModel() }
    viewModel { EditHistoryViewModel() }
}