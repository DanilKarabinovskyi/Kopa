package com.example.kopa.fragments.code_input

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.example.kopa.fragments.registation_info.RegistrationInfoFragment
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*
import java.util.concurrent.TimeUnit

class CodeInputFragmentViewModel:ViewModel() {
//    var auth = FirebaseAuth.getInstance()
//    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//    var verificationId = ""
//    private fun verificationCallbacks (
//        inputTelephone: TextInputEditText,
//        inputCode: TextInputEditText,
//        verifyButton: Button,
//        goInSystem: Button,
//        progress: ProgressBar,
//        context: Context,
//        telephoneError: TextView,
//        codeError: TextView
//    ) {
//        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                signIn(credential,activity = MainActivity(),inputCode,codeError,progress)
//                progress.visibility = View.INVISIBLE
//            }
//
//            override fun onVerificationFailed(p0: FirebaseException) {
//                progress.visibility = View.INVISIBLE
//                telephoneError.visibility = View.VISIBLE
//                inputTelephone.setBackgroundResource(R.drawable.ic_input_bg_red)
//            }
//
//            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
////                super.onCodeSent(p0, p1)
//                verificationId = p0
//                progress.visibility = View.INVISIBLE
//                inputTelephone.visibility = View.INVISIBLE
//                inputCode.visibility = View.VISIBLE
//                verifyButton.visibility = View.INVISIBLE
//                goInSystem.visibility = View.VISIBLE
//            }
//        }
//    }
//    fun verifyNumber(activity: MainActivity, phoneNumber:String, inputTelephone: TextInputEditText, inputCode: TextInputEditText,
//                     verifyButton: Button, goInSystem: Button, progress: ProgressBar, context: Context, telephoneError: TextView, codeError: TextView
//    ){
//        if(!phoneNumber.isBlank() && (phoneNumber.length > 10)) {
//            auth.setLanguageCode(Locale.getDefault().language)
//            verificationCallbacks(inputTelephone, inputCode,
//                verifyButton, goInSystem,progress,context,telephoneError,codeError)
//
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,
//                60,
//                TimeUnit.SECONDS,
//                activity,
//                mCallbacks
//            )
//        }
//        else{
//            progress.visibility = View.INVISIBLE
//            telephoneError.visibility = View.VISIBLE
//            inputTelephone.setBackgroundResource(R.drawable.ic_input_bg_red)
//        }
//    }
//    private fun signIn (credential: PhoneAuthCredential, activity: MainActivity, inputCode: TextInputEditText, codeError: TextView, progress: ProgressBar) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener {
//                    task: Task<AuthResult> ->
//                if (task.isSuccessful) {
//                    val manager: FragmentManager = activity.supportFragmentManager
//                    val transaction: FragmentTransaction? = manager?.beginTransaction()
//                    transaction!!.replace(R.id.fragment_container, RegistrationInfoFragment())
//                    transaction.commit()
//                }
//            }.addOnFailureListener {
//                progress.visibility = View.INVISIBLE
//                codeError.visibility = View.VISIBLE
//                inputCode.setBackgroundResource(R.drawable.ic_input_bg_red)
//            }
//    }
//
//    fun authenticate (inputCode: TextInputEditText, activity: MainActivity, context: Context, codeError: TextView, progress: ProgressBar) {
//
//        val verifyNumber = inputCode.text.toString()
//        if(!verifyNumber.isBlank() && (verifyNumber.length == 6)) {
//            val credential: PhoneAuthCredential =
//                PhoneAuthProvider.getCredential(verificationId, verifyNumber)
//            signIn(credential, activity,inputCode,codeError,progress)
//        }
//        else{
//            progress.visibility = View.INVISIBLE
//            codeError.visibility = View.VISIBLE
//            inputCode.setBackgroundResource(R.drawable.ic_input_bg_red)
//        }
//    }
}