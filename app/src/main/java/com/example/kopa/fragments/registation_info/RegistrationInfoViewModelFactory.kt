package com.example.kopa.fragments.registation_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.fragments.verification.VerificationFragmentViewModel

class RegistrationInfoViewModelFactory(private val userId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationInfoFragmentViewModel::class.java)) {
            return RegistrationInfoFragmentViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}