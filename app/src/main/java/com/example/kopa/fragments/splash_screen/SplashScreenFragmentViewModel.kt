package com.example.kopa.fragments.splash_screen

import android.os.Handler
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.example.kopa.fragments.login.LoginFragment

class SplashScreenFragmentViewModel:ViewModel() {
    fun toTelephonFragment(activity: MainActivity){
        Handler().postDelayed({
        val manager: FragmentManager? = activity.supportFragmentManager
        val transaction: FragmentTransaction? = manager?.beginTransaction()
        transaction!!.replace(R.id.fragment_container, LoginFragment())
        transaction.commit()
        }, 2000)
    }
    fun toLoginFragment(activity: MainActivity){
            val manager: FragmentManager? = activity.supportFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction!!.replace(R.id.fragment_container, LoginFragment())
            transaction.commit()
    }
}