package com.example.kopa.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.example.kopa.databinding.LoginScreenLayoutBinding
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {

    private lateinit var binding: LoginScreenLayoutBinding
    private lateinit var viewModel: LoginFragmentViewModel
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var auth = Firebase.auth
    lateinit var callbackManager: CallbackManager
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
        viewModel.handleResult(requestCode, resultCode, data!!, requireContext(), requireActivity() as MainActivity)
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
                inflater,
                R.layout.login_screen_layout,
                container,
                false)

        viewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
//        auth.signOut()
        binding.telephoneLoginButton.setOnClickListener {
            viewModel.toVerification(requireActivity() as MainActivity)
        }
        binding.facebookLoginButton.setOnClickListener {
            binding.facebookRealButton.performClick()
        }
        binding.facebookRealButton.fragment = this
        binding.facebookRealButton.setOnClickListener {
            viewModel.facebookAuth(binding.facebookRealButton, activity as MainActivity)
        }
        LoginManager.getInstance().logOut()
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        mGoogleSignInClient.signOut()
        binding.googleLoginButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, viewModel.RC_SIGN_IN)
        }


        return binding.root
    }

}