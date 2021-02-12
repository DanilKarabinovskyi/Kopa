package com.example.kopa.fragments.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.MainActivity

import com.example.kopa.R
import com.example.kopa.databinding.SplashScreenLayoutBinding

class SplachScreenFragment(logOut:String): Fragment() {

    private lateinit var binding: SplashScreenLayoutBinding
    private lateinit var viewModel: SplashScreenFragmentViewModel
    var signOut = logOut
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.splash_screen_layout,
            container,
            false)

        viewModel = ViewModelProvider(this).get(SplashScreenFragmentViewModel::class.java)
        binding.splashScreenViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        if(signOut =="true"){
            viewModel.toLoginFragment(requireActivity() as MainActivity)
        }else{
            viewModel.toTelephonFragment(requireActivity() as MainActivity)
        }

        return binding.root
    }
}