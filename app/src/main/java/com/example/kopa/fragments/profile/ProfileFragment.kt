package com.example.kopa.fragments.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.example.kopa.databinding.ProfileLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ObsoleteCoroutinesApi

class ProfileFragment(userId:String):Fragment() {

    private lateinit var binding: ProfileLayoutBinding
    private lateinit var viewModel: ProfileFragmentViewModel
    val userID = userId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.profile_layout,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        binding.profileViewModel = viewModel
        viewModel.initialization(binding, userID)
        binding.changePhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        binding.logOutButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.finish()
            Firebase.auth.signOut()
            intent.putExtra("signedOut", "true")
            activity?.startActivity(intent)

        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            viewModel.getNewPhoto(
                requestCode,
                resultCode,
                data!!,
                binding.profileImage,
                userID
            )
        }
    }
}