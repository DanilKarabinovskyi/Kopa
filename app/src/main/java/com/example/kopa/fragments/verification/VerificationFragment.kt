package com.example.kopa.fragments.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.MainActivity

import com.example.kopa.R
import com.example.kopa.databinding.VerificationScreenLayoutBinding

class VerificationFragment( userId: String): Fragment() {

    private lateinit var binding: VerificationScreenLayoutBinding
    private lateinit var viewModel: VerificationFragmentViewModel
    private lateinit var viewModelFactory: VerificationVewModelFactory
    val userID = userId
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.verification_screen_layout,
            container,
            false)
        viewModelFactory = VerificationVewModelFactory(userID)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(VerificationFragmentViewModel::class.java)

//        viewModel = ViewModelProvider(this).get(VerificationFragmentViewModel(userID)::class.java)
        binding.verificationViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.endVerificationButton.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            viewModel.verifyNumber(
                requireActivity() as MainActivity,
                binding.telephoneInput.text.toString(),
                binding.telephoneInput,
                binding.codeInput,
                binding.endVerificationButton,
                binding.endCodeInputButton,
                binding.progress,
                requireContext(),
                binding.errorVerification,
                binding.errorCode)
        }
//        binding.endCodeInputButton.setOnClickListener {
//            binding.progress.visibility = View.VISIBLE
//            viewModel.authenticate(binding.codeInput, requireActivity() as MainActivity, requireContext(),binding.errorCode,binding.progress)
//        }
        return binding.root
    }
}