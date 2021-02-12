package com.example.kopa.fragments.login

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.StateSet
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.example.kopa.fragments.registation_info.RegistrationInfoFragment
import com.example.kopa.fragments.verification.VerificationFragment
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginFragmentViewModel:ViewModel() {
    private var auth = Firebase.auth
    val RC_SIGN_IN: Int = 2
    var callbackManager: CallbackManager = CallbackManager.Factory.create();
    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    lateinit var googleId:String

    fun toVerification(activity: MainActivity){
        if(user != null){
    if(user!!.displayName != ""){
        val docRef = user!!.displayName?.let {
            db.collection("users").document(
                    it
            )
        }
        docRef!!.get().addOnCompleteListener { firstTask ->
            Log.d(ContentValues.TAG, "in----------------")
            if (firstTask.isSuccessful) {
                if(firstTask.result!!.exists()){
                    Log.d(StateSet.TAG, "signInWithCredential:success")
                    val manager: FragmentManager = activity.supportFragmentManager
                    val transaction: FragmentTransaction = manager.beginTransaction()
                    user!!.displayName?.let {
                        RegistrationInfoFragment(it)
                    }?.let {
                        transaction.replace(
                                R.id.fragment_container, it
                        )
                    }
                    transaction.commit()
                }else{
                    val manager: FragmentManager? = activity.supportFragmentManager
                    val transaction: FragmentTransaction? = manager?.beginTransaction()
                    transaction!!.replace(
                            R.id.fragment_container, VerificationFragment(
                            "telephoneNumber"
                    )
                    )
                    transaction.commit()
                }
            }
        }
    }else{
        val manager: FragmentManager? = activity.supportFragmentManager
        val transaction: FragmentTransaction? = manager?.beginTransaction()
        transaction!!.replace(
                R.id.fragment_container, VerificationFragment(
                "telephoneNumber"
        )
        )
        transaction.commit()
    }

        }else{
            val manager: FragmentManager? = activity.supportFragmentManager
            val transaction: FragmentTransaction? = manager?.beginTransaction()
            transaction!!.replace(
                    R.id.fragment_container, VerificationFragment(
                    "telephoneNumber"
            )
            )
            transaction.commit()
        }


    }

    fun handleResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent,
            context: Context,
            activity: MainActivity
    ){
        if (requestCode == RC_SIGN_IN ) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!, context, activity)
            } catch (e: ApiException) {
                Log.w(StateSet.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, context: Context, activity: MainActivity) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                    }
                    val acct = GoogleSignIn.getLastSignedInAccount(activity)
                    if (acct != null) {
                        googleId = acct.id as String
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(ControlsProviderService.TAG, "User profile updated.")
                            }
                        }
                    val email = user!!.email
                    val docRef = email?.let {
                        db.collection("users").document(
                                googleId
                        )
                    }
                    docRef!!.get().addOnCompleteListener { firstTask ->
                        Log.d(ContentValues.TAG, "in----------------")
                        if (firstTask.isSuccessful) {
                            if(firstTask.result!!.exists()){
                                Log.d(StateSet.TAG, "signInWithCredential:success")
                                val manager: FragmentManager = activity.supportFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                googleId?.let {
                                    RegistrationInfoFragment(it)
                                }?.let {
                                    transaction.replace(
                                            R.id.fragment_container, it
                                    )
                                }
                                transaction.commit()
                            }else{
                                val manager: FragmentManager = activity.supportFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                googleId?.let { VerificationFragment(it) }?.let {
                                    transaction.replace(
                                            R.id.fragment_container,
                                            it
                                    )
                                }
                                transaction.commit()
                            }
                        } else {
                            val manager: FragmentManager = activity.supportFragmentManager
                            val transaction: FragmentTransaction = manager.beginTransaction()
                            googleId?.let { VerificationFragment(it) }?.let {
                                transaction.replace(
                                        R.id.fragment_container,
                                        it
                                )
                            }
                            transaction.commit()
                        }
                    }.addOnFailureListener {
                    }


                } else {
                    Log.w(StateSet.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun facebookAuth(facebookSignInButton: LoginButton, activity: MainActivity) {

        facebookSignInButton.setPermissions("email", "public_profile")
        facebookSignInButton.registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        handleFacebookAccessToken(loginResult.accessToken, activity)
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })
    }
    private fun handleFacebookAccessToken(token: AccessToken, activity: MainActivity) {

        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {

                    val request = GraphRequest.newMeRequest(
                            token
                    ) { `object`, response ->
                        // Application code
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,link")
                    request.parameters = parameters
                    request.executeAsync()
                    request.accessToken!!.userId
                    Log.d(
                            TAG,
                            "------ID : ${request.accessToken!!.userId}---------------------------------------"
                    )
                    val user = auth.currentUser
                    val docRef = db.collection("users").document(
                            request.accessToken!!.userId
                    )
                    docRef.get().addOnCompleteListener { firstTask ->
                        Log.d(ContentValues.TAG, "in----------------")
                        Thread.sleep(200)
                        if (firstTask.isSuccessful) {
                            if(firstTask.result!!.exists()){
                                Log.d(TAG, "signInWithCredential:success")

                                if (user != null) {
                                    Log.d(
                                            TAG,
                                            "number : ${user.displayName}---------------------------------------"
                                    )
                                }
                                val manager: FragmentManager = activity.supportFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                transaction.replace(
                                        R.id.fragment_container, RegistrationInfoFragment(
                                        request.accessToken!!.userId
                                )
                                )
                                transaction.commit()
                            }else{
                                val manager: FragmentManager = activity.supportFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                transaction.replace(
                                        R.id.fragment_container,
                                        VerificationFragment(
                                                request.accessToken!!.userId
                                        )
                                )
                                transaction.commit()
                            }
                        } else {
                            val manager: FragmentManager = activity.supportFragmentManager
                            val transaction: FragmentTransaction = manager.beginTransaction()
                            transaction.replace(
                                    R.id.fragment_container,
                                    VerificationFragment(request.accessToken!!.userId)
                            )
                            transaction.commit()
                        }
                    }.addOnFailureListener {
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}