package com.example.kopa.fragments.registation_info

import android.content.ContentValues
import android.content.Intent
import android.service.controls.ControlsProviderService
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.MainActivity
import com.example.kopa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegistrationInfoFragmentViewModel(userId:String):ViewModel() {
    private val user = Firebase.auth.currentUser
    private var auth: FirebaseAuth = Firebase.auth
    var nameUser = ""
    var surnameUser = ""
    var cityUser = ""
    var telephoneUser = ""
    var photo = ""
    val db = Firebase.firestore
    val pattern = "[aZ]"
    var regex = "[0123456789]"
    var regex2 = "[A-Za-zА-Яа-яА-Яа-я]"
    var b: Boolean = Pattern.matches(pattern, "aaaaab")
    var userID = userId
    lateinit var docRef: DocumentReference
    fun splitName(name: TextView, surname: TextView, city: TextView,telephone:TextView){

        docRef = db.collection("users").document(userID)
            docRef.get().addOnCompleteListener { firstTask ->
                Log.d(ContentValues.TAG,"in----------------")
                if (firstTask.isSuccessful) {
                    if(firstTask.result!!.exists()) {
                        nameUser = firstTask.result!!.data?.get("name") as String
                        surnameUser = firstTask.result!!.data?.get("surname") as String
                        cityUser = firstTask.result!!.data?.get("city") as String
                        telephoneUser = firstTask.result!!.data?.get("number") as String
                        photo = firstTask.result!!.data?.get("photo") as String
                        name.text = nameUser
                        surname.text = surnameUser
                        city.text = cityUser
                        telephone.text = telephoneUser
                    }

                } else {

                }
            }.addOnFailureListener {
            }

    }
    fun saveData(
        name: TextView,
        surname: TextView,
        city: TextView,
        telephone:TextView,
        activity: MainActivity,
        nameError: TextView,
        surnameError: TextView,
        cityError: TextView,
        telephoneError:TextView
    ){
        if(areContains(name) && name.text.isNotBlank()
            && areContains(surname) && surname.text.isNotBlank()
            && city.text.isNotBlank() && telephone.text.isNotBlank() && areContainsNumbers(telephone)){
            if(userID == "telephoneNumber"){
                userID = telephone.text.toString()
            }
            val profileUpdates = userProfileChangeRequest {
                displayName = telephone.text.toString()
            }
            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ControlsProviderService.TAG, "User profile updated.")
                        }
                    }
            docRef = db.collection("users").document(userID)
            docRef.set(
                hashMapOf(
                    "name" to name.text.toString(),
                    "surname" to surname.text.toString(),
                    "city" to city.text.toString(),
                    "number" to  telephone.text.toString(),
                    "photo" to  photo
                )
            )
            val intent = Intent(activity, BottomNavBarActivity::class.java)
            intent.putExtra("id", userID)
            activity.finish()
            activity.startActivity(intent)
        }else {
            if (!areContains(name) || name.text.isBlank()) {
                nameError.visibility = View.VISIBLE
                name.setBackgroundResource(R.drawable.ic_input_bg_red)
            }
            if (!areContains(surname) || surname.text.isBlank()) {
                surnameError.visibility = View.VISIBLE
                surname.setBackgroundResource(R.drawable.ic_input_bg_red)
            }
            if (city.text.isBlank()) {
                cityError.visibility = View.VISIBLE
                city.setBackgroundResource(R.drawable.ic_input_bg_red)
            }
            if (areContains(name) && name.text.isNotBlank()) {
                    nameError.visibility = View.INVISIBLE
                    name.setBackgroundResource(R.drawable.ic_input_bg)
            }
            if (areContains(surname) && surname.text.isNotBlank()) {
                    surnameError.visibility = View.INVISIBLE
                    surname.setBackgroundResource(R.drawable.ic_input_bg)
            }
            if (city.text.isNotBlank()) {
                cityError.visibility = View.INVISIBLE
                city.setBackgroundResource(R.drawable.ic_input_bg)
            }
            if (!areContainsNumbers(telephone) || surname.text.isBlank()) {
                telephoneError.visibility = View.VISIBLE
                telephoneError.setBackgroundResource(R.drawable.ic_input_bg_red)
            }
            if (areContainsNumbers(telephone) && telephone.text.isNotBlank()) {
                telephoneError.visibility = View.INVISIBLE
                telephone.setBackgroundResource(R.drawable.ic_input_bg)
            }
        }

    }
    private fun areContains(mytext: TextView):Boolean{
        return Regex(regex).find(mytext.text) == null
    }
    private fun areContainsNumbers(mytext: TextView):Boolean{
        return Regex(regex2).find(mytext.text) == null
    }
}