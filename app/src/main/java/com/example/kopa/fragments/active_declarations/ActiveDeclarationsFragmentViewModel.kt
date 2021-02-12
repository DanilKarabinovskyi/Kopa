package com.example.kopa.fragments.active_declarations

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kopa.fragments.adapters.DeclarationsAdapter
import com.example.kopa.model.Declaration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActiveDeclarationsFragmentViewModel:ViewModel() {
    val db = Firebase.firestore
    lateinit var declarations:MutableList<Declaration>
    fun getList(adapter: DeclarationsAdapter,userID:String){
        val user = Firebase.auth.currentUser


        db.collection("declarations")
                .whereEqualTo("id", userID)
                .whereEqualTo("selled", false)
                .get()
                .addOnSuccessListener { result ->
                    declarations = mutableListOf()
                    lateinit var singleDeclaration: Declaration
                    for (document in result) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        singleDeclaration = Declaration(
                            document.data["id"] as String,
                            document.data["price"] as String,
                            document.data["model"] as String,
                            document.data["material"] as String,
                            document.data["size"] as String,
                            document.data["sizeRegion"] as String,
                            document.data["sizeLength"] as String,
                            document.data["sizeWidth"] as String,
                            false,
                            false,
                            document.data["description"] as String,
                            document.data["photoArray"] as MutableList<String>)
                        declarations.add(singleDeclaration)
                    }
                    adapter.submitList(declarations)
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                }
    }
}