package com.example.kopa.fragments.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.kopa.R
import com.example.kopa.databinding.ProfileLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.*
import java.util.*

class ProfileFragmentViewModel:ViewModel() {
    private val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    val storageRef = Firebase.storage.reference
    var photoUrl:Uri? = null
    @ObsoleteCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.P)
    fun getNewPhoto(
        requestCode: Int, resultCode: Int, data: Intent?,
        photoView: CircleImageView,userID: String
    ){
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl = data.data!!
            Picasso
                .get()
                .load(photoUrl)
                .fit()
                .centerInside()
                .into(photoView)
            saveChanges(userID)

        }
    }
    @SuppressLint("SetTextI18n")
    fun initialization(binding: ProfileLayoutBinding, userID:String){
        val docRef = db.collection("users").document(userID)
        docRef.get().addOnCompleteListener { firstTask ->
            Log.d(ContentValues.TAG,"in----------------")
            if (firstTask.isSuccessful) {
                val nameUser = firstTask.result!!.data?.get("name") as String
                val surnameUser = firstTask.result!!.data?.get("surname") as String
                val cityUser = firstTask.result!!.data?.get("city") as String
                val telephoneUser = firstTask.result!!.data?.get("number") as String
                val photoUrl = firstTask.result!!.data?.get("photo") as String
                binding.userName.setText("$nameUser $surnameUser")
                binding.city.setText(cityUser)
                binding.number.setText(telephoneUser)
                if(photoUrl != ""){
                    Picasso
                        .get()
                        .load(photoUrl.toUri())
                        .fit()
                        .centerInside()
                        .into(binding.profileImage)
                }else{
                    Picasso
                        .get()
                        .load(R.drawable.avatar)
                        .fit()
                        .centerInside()
                        .into(binding.profileImage)
                }
//                storageRef.child("/images/$photoUrl").downloadUrl.addOnSuccessListener {
//
//                }.addOnFailureListener {
//                }

            } else {

            }
        }.addOnFailureListener {
        }


    }

    fun saveChanges(userID: String){

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        val docRef = db.collection("users").document(userID)
        photoUrl?.let {
            ref.putFile(it)
                .addOnSuccessListener {
                    Log.d(ControlsProviderService.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(ControlsProviderService.TAG, "File Location: $it")
                        docRef.get().addOnCompleteListener { firstTask ->
                            Log.d(ContentValues.TAG,"in----------------")
                            if (firstTask.isSuccessful) {
                                docRef.update("photo",it.toString())
                            } else {

                            }
                        }.addOnFailureListener {
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(ControlsProviderService.TAG, "Failed to upload image to storage: ${it.message}")
                }
        }


    }

}