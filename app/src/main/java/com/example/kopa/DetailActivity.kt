package com.example.kopa

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.activity_view_models.DetailActivityViewModel
import com.example.kopa.databinding.DetailActivityBinding

class DetailActivity: AppCompatActivity() {
    private lateinit var viewModel: DetailActivityViewModel
    private lateinit var binding: DetailActivityBinding
    lateinit var userID:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        val userID = arguments!!["id"].toString()
        val productId = arguments["productId"].toString()
        val type = arguments["type"].toString()
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity)
        viewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)
        binding.detailViewModel = viewModel

        viewModel.initUser(binding.imageSlider,binding.price,
                binding.textModel,binding.textMatheralMaterial,
                binding.textSizeCountry,binding.textSizeSize,
                binding.textSizeLenght,binding.textSizeWeight,
                binding.textDescription,productId,binding.profileImage,
                binding.sellerName,binding.sellerCity,
                binding.telephoneButton,this,
                type,binding.likeImageButton,
                binding.penImageButton,binding.deleteDeclarationButton,userID)
        binding.backButton.setOnClickListener {
            val intent = Intent(this, BottomNavBarActivity::class.java)
            intent.putExtra("id", userID)
            this.finish()
            this.startActivity(intent)
        }
    }
//    TODO: ADD userID into onRequest
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 42) {
//            // If request is cancelled, the result arrays are empty.
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                // permission was granted, yay!
//
//                viewModel.getTelephoneUser(userID,this)
//            } else {
//                // permission denied, boo! Disable the
//                // functionality
//            }
//            return
//        }
//    }
}