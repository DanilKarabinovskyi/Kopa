package com.example.kopa.fragments.registation_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.MainActivity

import com.example.kopa.R
import com.example.kopa.databinding.RegistrationInfoScreenLayoutBinding
import com.example.kopa.fragments.verification.VerificationFragmentViewModel
import com.example.kopa.fragments.verification.VerificationVewModelFactory

class RegistrationInfoFragment( userId: String) : Fragment() {

    private lateinit var binding: RegistrationInfoScreenLayoutBinding
    private lateinit var viewModel: RegistrationInfoFragmentViewModel
    private lateinit var viewModelFactory: RegistrationInfoViewModelFactory
    val userID = userId
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.registration_info_screen_layout,
            container,
            false)
        viewModelFactory = RegistrationInfoViewModelFactory(userID)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RegistrationInfoFragmentViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(RegistrationInfoFragmentViewModel(userID)::class.java)
        binding.registrationInfoViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.splitName(binding.nameInput,binding.surnameInput,binding.cityInput,binding.telephoneInput)
        binding.endLoginButton.setOnClickListener {
            viewModel.saveData(
                binding.nameInput,
                binding.surnameInput,
                binding.cityInput, binding.telephoneInput,
                requireActivity() as MainActivity,
                binding.errorName,binding.errorSurname,
                binding.errorCity,
                binding.errorTelephone)
        }
        return binding.root
    }
}