package com.example.kopa.activity_view_models

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.CreateDeclarationActivity
import com.example.kopa.DetailActivity
import com.example.kopa.R
import com.example.kopa.image_slider_components.ImageSlider
import com.example.kopa.image_slider_components.SlideModel
import com.example.kopa.model.Declaration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.delete_element_layout.view.*
import java.util.regex.Pattern


class DetailActivityViewModel:ViewModel() {
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
    lateinit var docRef: DocumentReference
    fun initUser(slider: ImageSlider, price: TextView,
                 model: TextView, material: TextView,
                 sizeRegion: TextView, sizeSize: TextView,
                 sizeLenght: TextView, sizeWeight: TextView,
                 description: TextView, productId: String,
                 sellerPhoto: CircleImageView, sellerName: TextView,
                 sellerCity: TextView, sellerPhoneIcon: FloatingActionButton,
                 activity: DetailActivity, type: String,
                 likeImage: ImageView, penImage: ImageView,
                 deleteDeclarationsButton: Button,userId: String
    ) {
        if (type == "archived") {
            db.collection("archived_users_declarations").document(userId).collection("archived_declarations").document(productId)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            likeImage.visibility = View.INVISIBLE
                            penImage.visibility = View.INVISIBLE
                            sellerPhoneIcon.visibility = View.INVISIBLE
                            sellerCity.visibility = View.INVISIBLE
                            sellerName.visibility = View.INVISIBLE
                            sellerPhoto.visibility = View.INVISIBLE
                            price.text = task.result!!.data!!["price"] as String
                            model.text = task.result!!.data!!["model"] as String
                            material.text = task.result!!.data!!["material"] as String
                            sizeSize.text = task.result!!.data!!["size"] as String
                            sizeRegion.text = task.result!!.data!!["sizeRegion"] as String
                            sizeLenght.text = task.result!!.data!!["sizeLength"] as String
                            sizeWeight.text = task.result!!.data!!["sizeWidth"] as String
                            description.text = task.result!!.data!!["description"] as String
                            var links = task.result!!.data!!["photoArray"] as MutableList<String>
                            var id = task.result!!.data!!["id"] as String
                            val imageList = ArrayList<SlideModel>()
                            for (i in 0 until links.size) {
                                imageList.add(SlideModel(links[i], "0"))
                            }
                            slider.setImageList(imageList)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                    }
        } else {
            db.collection("declarations").document(productId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    price.text = task.result!!.data!!["price"] as String
                    model.text = task.result!!.data!!["model"] as String
                    material.text = task.result!!.data!!["material"] as String
                    sizeSize.text = task.result!!.data!!["size"] as String
                    sizeRegion.text = task.result!!.data!!["sizeRegion"] as String
                    sizeLenght.text = task.result!!.data!!["sizeLength"] as String
                    sizeWeight.text = task.result!!.data!!["sizeWidth"] as String
                    description.text = task.result!!.data!!["description"] as String
                    var links = task.result!!.data!!["photoArray"] as MutableList<String>
                    var id = task.result!!.data!!["id"] as String
                    val imageList = ArrayList<SlideModel>()
                    for (i in 0 until links.size) {
                        imageList.add(SlideModel(links[i], "0"))
                    }
                    slider.setImageList(imageList)
                    if (type == "like") {
                        check(likeImage, price,
                                model, material,
                                sizeRegion, sizeSize,
                                sizeLenght, sizeWeight,
                                description, productId, userId, links)
                        penImage.visibility = View.INVISIBLE
                        val docRef = db.collection("users").document(userId)
                        docRef.get().addOnCompleteListener { firstTask ->
                            Log.d(ContentValues.TAG, "in----------------")
                            if (firstTask.isSuccessful) {
                                val nameUser = firstTask.result!!.data?.get("name") as String
                                val surnameUser = firstTask.result!!.data?.get("surname") as String
                                val cityUser = firstTask.result!!.data?.get("city") as String
                                val telephoneUser = firstTask.result!!.data?.get("number") as String
                                val photoUrl = firstTask.result!!.data?.get("photo") as String
                                sellerName.text = "$nameUser"
                                sellerCity.text = cityUser
                                sellerPhoneIcon.setOnClickListener {
                                    checkPermission(activity, telephoneUser)
                                }
                                if (photoUrl != "") {
                                    Picasso
                                            .get()
                                            .load(photoUrl.toUri())
                                            .fit()
                                            .centerInside()
                                            .into(sellerPhoto)
                                }
                            }
                        }.addOnFailureListener {

                        }
                    } else if (type == "active") {
                        likeImage.visibility = View.INVISIBLE
                        deleteDeclarationsButton.visibility = View.VISIBLE
                        sellerPhoneIcon.visibility = View.INVISIBLE
                        sellerCity.visibility = View.INVISIBLE
                        sellerName.visibility = View.INVISIBLE
                        sellerPhoto.visibility = View.INVISIBLE
                        penImage.setOnClickListener {
                            val intent = Intent(activity, CreateDeclarationActivity::class.java)
                            intent.putExtra("id", userId)
                            intent.putExtra("productId", productId)
                            intent.putExtra("type", "update")
                            activity.finish()
                            activity.startActivity(intent)
                        }
                        deleteDeclarationsButton.setOnClickListener {
                            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                            val dialog: AlertDialog = builder.create()
                            val inflater: LayoutInflater = LayoutInflater.from(activity)
                            val layout: View = inflater.inflate(R.layout.delete_element_layout, null)
                            dialog.setView(layout)

                            dialog.show()

                            layout.no_button.setOnClickListener {
                                dialog.cancel()
                            }
                            layout.yes_button.setOnClickListener {
                                dialog.cancel()
                                val docRefArchived = db.collection("archived_users_declarations").document(userId).collection("archived_declarations").document(productId)
                                docRefArchived.get().addOnCompleteListener { firstTask ->
                                    Log.d(ContentValues.TAG, "in----------------")
                                    if (firstTask.isSuccessful) {
                                        docRefArchived.set(hashMapOf(
                                                "description" to description.text,
                                                "id" to id,
                                                "price" to price.text,
                                                "model" to model.text,
                                                "material" to material.text,
                                                "size" to sizeSize.text,
                                                "sizeRegion" to sizeRegion.text,
                                                "sizeLength" to sizeLenght.text,
                                                "sizeWidth" to sizeWeight.text,
                                                "photoArray" to links,
                                                "productId" to productId
                                        )).addOnCompleteListener {
                                            db.collection("declarations").document(productId).delete().addOnCompleteListener {
                                                val docRefLiked = db.collection("liked_users_declarations").document(id).collection("liked_declarations").document(productId)
                                                docRefLiked.get().addOnCompleteListener { newTask ->
                                                    Log.d(ContentValues.TAG, "in----------------")
                                                    if (newTask.isSuccessful) {
                                                        if (newTask.result!!.exists()) {
                                                            docRefLiked.delete().addOnSuccessListener {
                                                                val intent = Intent(activity, BottomNavBarActivity::class.java)
                                                                intent.putExtra("id", userId)
                                                                activity.finish()
                                                                activity.startActivity(intent)
                                                            }
                                                        }else{
                                                            val intent = Intent(activity, BottomNavBarActivity::class.java)
                                                            intent.putExtra("id", userId)
                                                            activity.finish()
                                                            activity.startActivity(intent)
                                                        }
                                                    } else {
                                                        val intent = Intent(activity, BottomNavBarActivity::class.java)
                                                        intent.putExtra("id", userId)
                                                        activity.finish()
                                                        activity.startActivity(intent)
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }

                        }
                    } else if (type == "archived") {
                        likeImage.visibility = View.INVISIBLE
                        penImage.visibility = View.INVISIBLE
                        sellerPhoneIcon.visibility = View.INVISIBLE
                        sellerCity.visibility = View.INVISIBLE
                        sellerName.visibility = View.INVISIBLE
                        sellerPhoto.visibility = View.INVISIBLE
                    }

                }
            }
        }
    }
    fun checkPermission(activity: DetailActivity, telephone: String) {
        if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.CALL_PHONE)) {

            } else {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        42)
            }
        } else {
            // Permission has already been granted
            callPhone(activity, telephone)
        }
    }



    fun callPhone(activity: DetailActivity, telephone: String){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$telephone"))
        activity.startActivity(intent)
    }
    fun getTelephoneUser(userId: String, activity: DetailActivity){
        val docRef = db.collection("users").document(userId)
        docRef.get().addOnCompleteListener { firstTask ->
            Log.d(ContentValues.TAG, "in----------------")
            if (firstTask.isSuccessful) {
                val telephoneUser = firstTask.result!!.data?.get("number") as String
                callPhone(activity, telephoneUser)

            }
        }.addOnFailureListener {

        }
    }
    fun check(
            like: ImageView, price: TextView,
            model: TextView, material: TextView,
            sizeRegion: TextView, sizeSize: TextView,
            sizeLenght: TextView, sizeWeight: TextView,
            description: TextView, productId: String, id: String, links: MutableList<String>,
    ){

        val docRefLiked = db.collection("liked_users_declarations").document(id).collection("liked_declarations").document(productId)
        docRefLiked.get().addOnCompleteListener { firstTask ->
            Log.d(ContentValues.TAG, "in----------------")
            if (firstTask.isSuccessful) {
                if(firstTask.result!!.exists()){
                    like.setImageResource(R.drawable.ic_heart_red)
                    like.setOnClickListener {
                        docRefLiked.delete().addOnCompleteListener {
                            like.setImageResource(R.drawable.ic_heart_white)
                            like.setOnClickListener {
                                docRefLiked.set(hashMapOf(
                                        "description" to description.text,
                                        "id" to id,
                                        "price" to price.text,
                                        "model" to model.text,
                                        "material" to material.text,
                                        "size" to sizeSize.text,
                                        "sizeRegion" to sizeRegion.text,
                                        "sizeLength" to sizeLenght.text,
                                        "sizeWidth" to sizeWeight.text,
                                        "photoArray" to links,
                                        "productId" to productId
                                )).addOnCompleteListener {
                                    like.setImageResource(R.drawable.ic_heart_red)
                                    check(like, price,
                                            model, material,
                                            sizeRegion, sizeSize,
                                            sizeLenght, sizeWeight,
                                            description, productId, id, links)
                                }
                            }
                        }
                    }
                }else{
                    like.setImageResource(R.drawable.ic_heart_white)
                    like.setOnClickListener {
                        docRefLiked.set(hashMapOf(
                                "description" to description.text,
                                "id" to id,
                                "price" to price.text,
                                "model" to model.text,
                                "material" to material.text,
                                "size" to sizeSize.text,
                                "sizeRegion" to sizeRegion.text,
                                "sizeLength" to sizeLenght.text,
                                "sizeWidth" to sizeWeight.text,
                                "photoArray" to links,
                                "productId" to productId
                        )).addOnCompleteListener {
                            like.setImageResource(R.drawable.ic_heart_red)
                            check(like, price,
                                    model, material,
                                    sizeRegion, sizeSize,
                                    sizeLenght, sizeWeight,
                                    description, productId, id, links)
                        }
                    }
                }
            }
        }
    }
}