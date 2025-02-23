package com.example.contentresolversampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contentresolversampleapp.model.RandomStringRepository

class RandomStringViewModelFactory (private val randomStringRepository: RandomStringRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomStringViewModel(randomStringRepository) as T
    }
}