package com.example.kopa.fragments.code_input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.MainActivity

import com.example.kopa.R
import com.example.kopa.databinding.CodeInputScreenLayoutBinding
import com.example.kopa.fragments.verification.VerificationFragmentViewModel
import com.example.kopa.fragments.verification.VerificationVewModelFactory

class CodeInputFragment(verificationId: String, userId: String): Fragment() {

    private lateinit var binding: CodeInputScreenLayoutBinding
    private lateinit var viewModel: VerificationFragmentViewModel
    private lateinit var viewModelFactory: VerificationVewModelFactory
    private var id= verificationId
//    val authKey = authKey
    val userID = userId
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.code_input_screen_layout,
            container,
            false)
        viewModelFactory = VerificationVewModelFactory(userID)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(VerificationFragmentViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(VerificationFragmentViewModel(userID)::class.java)
        binding.codeInputViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.endCodeInputButton.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            viewModel.authenticate(binding.codeInput, requireActivity() as MainActivity, id ,binding.errorCode,binding.progress)
        }
        return binding.root
    }
}