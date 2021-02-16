package com.example.kopa.activity_view_models

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color.BLACK
import android.graphics.Color.TRANSPARENT
import android.net.Uri
import android.os.Build
import android.service.controls.ControlsProviderService
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.CreateDeclarationActivity
import com.example.kopa.image_slider_components.SlideModel
import com.example.kopa.model.Declaration
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.w3c.dom.Text
import java.util.*

class CreateDeclarationActivityViewModel:ViewModel() {
    private val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    val storageRef = Firebase.storage.reference
    var photoUrl1: Uri? = null
    var photoUrl2: Uri? = null
    var photoUrl3: Uri? = null
    var photoUrl4: Uri? = null
    var photoUrl5: Uri? = null
    var photoUrl6: Uri? = null
    var photoUrl7: Uri? = null
    var photoUrl8: Uri? = null
    var regex = "[A-Za-zА-Яа-яА-Яа-я]"
    var linkArray = mutableListOf<String>()
    @ObsoleteCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.P)
    fun getNewPhoto(
            requestCode: Int, resultCode: Int,
            data: Intent?, userID: String,
            photoView1: ImageView, photoView2: ImageView,
            photoView3: ImageView, photoView4: ImageView,
            photoView5: ImageView, photoView6: ImageView,
            photoView7: ImageView, photoView8: ImageView,
            cameraView:ImageView,linkMutableList: MutableList<Uri>
    ):MutableList<Uri>{
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl1 = data.data!!
            linkMutableList.add(photoUrl1!!)
            Picasso
                    .get()
                    .load(photoUrl1)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(30, 0))
                    .into(photoView1)
            TRANSPARENT.also { photoView1.background = it.toDrawable() }
            cameraView.visibility = View.INVISIBLE


        }
        else if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl2 = data.data!!
            linkMutableList.add(photoUrl2!!)
            Picasso
                    .get()
                    .load(photoUrl2)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView2)
            TRANSPARENT.also { photoView2.foreground = it.toDrawable() }


        }
        else if(requestCode == 3 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl3 = data.data!!
            linkMutableList.add(photoUrl3!!)
            Picasso
                    .get()
                    .load(photoUrl3)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView3)
            TRANSPARENT.also { photoView3.foreground = it.toDrawable() }


        }
        else if(requestCode == 4 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl4 = data.data!!
            linkMutableList.add(photoUrl4!!)
            Picasso
                    .get()
                    .load(photoUrl4)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView4)
            TRANSPARENT.also { photoView4.foreground = it.toDrawable() }


        }
        else if(requestCode == 5 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl5 = data.data!!
            linkMutableList.add(photoUrl5!!)
            Picasso
                    .get()
                    .load(photoUrl5)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView5)
            TRANSPARENT.also { photoView5.foreground = it.toDrawable() }


        }
        else if(requestCode == 6 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl6 = data.data!!
            linkMutableList.add(photoUrl6!!)
            Picasso
                    .get()
                    .load(photoUrl6)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView6)
            TRANSPARENT.also { photoView6.foreground = it.toDrawable() }


        }
        else if(requestCode == 7 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl7 = data.data!!
            linkMutableList.add(photoUrl7!!)
            Picasso
                    .get()
                    .load(photoUrl7)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView7)
            TRANSPARENT.also { photoView7.foreground = it.toDrawable() }

        }
        else if(requestCode == 8 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(ControlsProviderService.TAG, "Photo was selected")
            photoUrl8 = data.data!!
            linkMutableList.add(photoUrl8!!)
            Picasso
                    .get()
                    .load(photoUrl8)
                    .fit()
                    .centerInside()
                    .transform(RoundedCornersTransformation(50, 0))
                    .into(photoView8)
            TRANSPARENT.also { photoView8.foreground = it.toDrawable() }
        }
        return  linkMutableList
    }
    fun createDeclaration(listOfPhotoNumbers:MutableList<Int>,
                          size:String,sizeRegion:String,
                          sizeLength:String,sizeWidth:String,
                          model:String,material:String,
                          description:String,price:String,userID: String,
                          photoView1: ImageView, photoView2: ImageView,
                          photoView3: ImageView, photoView4: ImageView,
                          photoView5: ImageView, photoView6: ImageView,
                          photoView7: ImageView, photoView8: ImageView,
                          activity: CreateDeclarationActivity,
                          listPhotoLinks:MutableList<Uri>,
                          errorTextView: TextView,progressBar: ProgressBar
    ){

        if(listOfPhotoNumbers.size>0 && size != "0"
                && sizeLength != "0" && sizeWidth != "0"
                && model != "" && material != ""
                && description != "" && price != ""
                && areContains(price)){
            progressBar.visibility = View.VISIBLE
            for (i in 0 until listPhotoLinks.size){
                linkArray.add("it.toString()")
            }
            for (i in 0 until listPhotoLinks.size){
                var uri = listPhotoLinks[i]
                val filename = UUID.randomUUID().toString()
                val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
                uri?.let {
                    ref.putFile(it)
                            .addOnSuccessListener {
                                Log.d(ControlsProviderService.TAG, "Successfully uploaded image: ${it.metadata?.path}")
                                ref.downloadUrl.addOnSuccessListener {
                                    Log.d(ControlsProviderService.TAG, "File Location: $it")
                                    linkArray[i] = it.toString()
                                    if(i == listPhotoLinks.size-1){
                                        val filenameNew= UUID.randomUUID().toString()
                                        val docRef = db.collection("declarations").document(filenameNew)
                                        docRef.set(hashMapOf(
                                                "description" to description,
                                                "id" to userID,
                                                "price" to price,
                                                "model" to model,
                                                "material" to material,
                                                "size" to size,
                                                "sizeRegion" to sizeRegion,
                                                "sizeLength" to sizeLength,
                                                "sizeWidth" to  sizeWidth,
                                                "photoArray" to linkArray,
                                                "productId" to filenameNew
                                        )).addOnCompleteListener {
                                            val intent = Intent(activity, BottomNavBarActivity::class.java)
                                            intent.putExtra("id", userID)
                                            activity.finish()
                                            activity.startActivity(intent)
                                        }
                                    }
                                }
                            }
                            .addOnFailureListener {
                                Log.d(ControlsProviderService.TAG, "Failed to upload image to storage: ${it.message}")
                            }
                }
            }
        }else{
            if(listOfPhotoNumbers.size == 0){
                errorTextView.text = "Додайте хоча б одне фото."
                errorTextView.visibility = View.VISIBLE
            }
            else if(size == "0"
                    || sizeLength == "0" || sizeWidth == "0"
                    || model == "" || material == ""
                    || description == "" || price == ""){
                errorTextView.text = "Всі поля мають бути заповнені."
                errorTextView.visibility = View.VISIBLE
            }
            else if(!areContains(price)){
                errorTextView.text = "Введіть корректну ціну"
                errorTextView.visibility = View.VISIBLE
            }
        }

    }
    fun initialize(productId:String,userID: String,listOfPhotoNumbers:MutableList<Int>,
                   size:TextView,sizeRegion:TextView,
                   sizeLength:TextView,sizeWidth:TextView,
                   model:TextInputEditText,material:TextInputEditText,
                   description:TextView,price:TextInputEditText,
                   photoView1: ImageView, photoView2: ImageView,
                   photoView3: ImageView, photoView4: ImageView,
                   photoView5: ImageView, photoView6: ImageView,
                   photoView7: ImageView, photoView8: ImageView,
                   cameraView:ImageView,activity: CreateDeclarationActivity){
        db.collection("declarations").document(productId)
            .get()
            .addOnSuccessListener { result ->
                price.setText(result!!.data!!["price"] as String)
                model.setText(result!!.data!!["model"] as String)
                material.setText(result!!.data!!["material"] as String)
                size.setText(result!!.data!!["size"] as String)
//                model.text = result!!.data!!["model"] as Editable
//                material.text = result!!.data!!["material"] as Editable
//                size.text = result!!.data!!["size"] as Editable
                sizeRegion.text = result!!.data!!["sizeRegion"] as String
                sizeLength.text = result!!.data!!["sizeLength"] as String
                sizeWidth.text = result!!.data!!["sizeWidth"] as String
                description.text = result!!.data!!["description"] as String
                var links = result!!.data!!["photoArray"] as MutableList<String>
                var imageViewList = mutableListOf<ImageView>(photoView1,photoView2,photoView3,photoView4,photoView5,photoView6,photoView7,photoView8)
                for (i in 0 until links.size){
                    TRANSPARENT.also { imageViewList[i].background = it.toDrawable() }
                    TRANSPARENT.also { imageViewList[i].foreground = it.toDrawable() }
                    cameraView.visibility = View.INVISIBLE
                    Picasso
                        .get()
                        .load(links[i].toUri())
                        .fit()
                        .centerInside()
                        .transform(RoundedCornersTransformation(50, 0))
                        .into(imageViewList[i])

                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun areContains(mytext: String):Boolean{
        return Regex(regex).find(mytext) == null
    }



}