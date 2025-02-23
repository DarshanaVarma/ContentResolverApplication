package com.example.contentresolversampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contentresolversampleapp.model.RandomString
import com.example.contentresolversampleapp.model.RandomStringRepository

class RandomStringViewModel(private val randomStringRepository: RandomStringRepository): ViewModel() {
    private val _randomString = MutableLiveData<RandomString>()
    val randomString: MutableLiveData<RandomString> = _randomString

    fun generateRandomString(length: Int): LiveData<RandomString> {
         val randomStringObject = randomStringRepository.getRandomString(length)
        _randomString.postValue(randomStringObject)
        return _randomString
    }


}