package com.example.kopa.fragments.all_declarations

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.kopa.fragments.adapters.DeclarationsAdapter
import com.example.kopa.model.Declaration
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllDeclarationsFragmentVewModel:ViewModel() {
    lateinit var declarations:MutableList<Declaration>
    lateinit var adapt:DeclarationsAdapter
    init {

    }
    fun getList(adapter: DeclarationsAdapter){
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore

        db.collection("declarations")
            .get()
            .addOnSuccessListener { result ->
                declarations = mutableListOf()
                lateinit var singleDeclaration: Declaration
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
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
                            document.data["productId"] as String,
                            document.data["photoArray"] as MutableList<String>)
                    declarations.add(singleDeclaration)
                }
                adapt = adapter
                adapter.submitList(declarations)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
    fun sort(
        adapter: DeclarationsAdapter,
        model:TextInputEditText,material:TextInputEditText,
        sizeLength:TextInputEditText,sizeWidth:TextInputEditText,
        priceLow:TextInputEditText,priceMax:TextInputEditText){
        var newDeclarations = mutableListOf<Declaration>()
        var size = declarations.size
        if(model.text.toString() != ""){
            for (i in 0 until size){
                if(declarations[i].model == model.text.toString()){
                    newDeclarations.add(declarations[i])
                }
            }
            declarations = newDeclarations
        }
        if(material.text.toString() != ""){
            size = declarations.size
            newDeclarations = mutableListOf<Declaration>()
            for (i in 0 until size){
                if(declarations[i].material == material.text.toString()){
                    newDeclarations.add(declarations[i])
                }
            }
            declarations = newDeclarations
        }
        if(sizeLength.text.toString() != "" && sizeWidth.text.toString() != ""){
            size = declarations.size
            newDeclarations = mutableListOf<Declaration>()
            for (i in 0 until size){
                if(declarations[i].sizeLength == sizeLength.text.toString() && declarations[i].sizeWidth == sizeWidth.text.toString()){
                    newDeclarations.add(declarations[i])
                }
            }
            declarations = newDeclarations
        }
        if(priceLow.text.toString() != "" && priceMax.text.toString() != ""){
            size = declarations.size
            newDeclarations = mutableListOf<Declaration>()
            var lowPrice = priceLow.text.toString()
            for (i in 0 until size){
                if(declarations[i].price.toInt() > priceLow.text.toString().toInt() && declarations[i].price.toInt() < priceMax.text.toString().toInt()){
                    newDeclarations.add(declarations[i])
                }
            }
            declarations = newDeclarations
        }
        adapter.submitList(declarations)
    }
}