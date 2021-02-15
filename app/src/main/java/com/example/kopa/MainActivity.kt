package com.example.kopa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kopa.fragments.splash_screen.SplachScreenFragment
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arguments = intent!!.extras
        if(arguments != null){
            val signedOut = arguments!!["signedOut"].toString()
            if(signedOut == "true"){
                this.replaceFragment(SplachScreenFragment("true"))
            }else{
                this.replaceFragment(SplachScreenFragment("false"))
            }
        }else{
            this.replaceFragment(SplachScreenFragment("false"))
        }

    }
    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}