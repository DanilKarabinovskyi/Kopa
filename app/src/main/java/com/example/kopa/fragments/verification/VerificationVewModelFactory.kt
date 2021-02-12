package com.example.kopa.fragments.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VerificationVewModelFactory(private val userId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerificationFragmentViewModel::class.java)) {
            return VerificationFragmentViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}