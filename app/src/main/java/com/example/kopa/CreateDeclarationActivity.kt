package com.example.kopa

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kopa.activity_view_models.CreateDeclarationActivityViewModel
import com.example.kopa.databinding.CreateDeclarationActivityBinding
import com.example.kopa.fragments.dialog.NumberPickerDialog
import com.google.android.material.textfield.TextInputEditText

class CreateDeclarationActivity: AppCompatActivity() {
    private lateinit var viewModel: CreateDeclarationActivityViewModel
    private lateinit var binding: CreateDeclarationActivityBinding
    var userID = "0"
    var listPhotoLinks:MutableList<Uri> = mutableListOf<Uri>()
    var listPhotoNumbers = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = intent.extras
        val userID = arguments!!["id"].toString()
        val productId = arguments!!["productId"].toString()
        val type = arguments!!["type"].toString()
        binding = DataBindingUtil.setContentView(this, R.layout.create_declaration_activity)
        viewModel = ViewModelProvider(this).get(CreateDeclarationActivityViewModel::class.java)
        binding.createDeclarationViewModel = viewModel
        if(type == "update"){
            viewModel.initialize(productId,userID, listPhotoNumbers,
                binding.sizePickerText,
                binding.euText,
                binding.sizeLengthText,
                binding.sizeWidthText,
                binding.modelInput,
                binding.materialInput,
                binding.descriptionInput,
                binding.priceInput,
                binding.selectPhoto1,
                binding.selectPhoto2,
                binding.selectPhoto3,
                binding.selectPhoto4,
                binding.selectPhoto5,
                binding.selectPhoto6,
                binding.selectPhoto7,
                binding.selectPhoto8,
                binding.cameraIcon,
            this)
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this, BottomNavBarActivity::class.java)
            intent.putExtra("id", userID)
            this.finish()
            this.startActivity(intent)
        }
        binding.selectPhoto1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        binding.selectPhoto2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 2)
        }
        binding.selectPhoto3.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 3)
        }
        binding.selectPhoto4.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 4)
        }
        binding.selectPhoto5.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 5)
        }
        binding.selectPhoto6.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 6)
        }
        binding.selectPhoto7.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 7)
        }
        binding.selectPhoto8.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 8)
        }
        binding.euText.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.materialInput,
                "region",binding.euText,"2"
            )
            newFragment.show(supportFragmentManager, "Виберіть систему розмірів")
        }
        binding.sizeWidthText.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.materialInput,
                "size",binding.sizeWidthText,"2"
            )
            newFragment.show(supportFragmentManager, "Виберіть ширину")
        }
        binding.sizeLengthText.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.materialInput,
                "size",binding.sizeLengthText,"2"
            )
            newFragment.show(supportFragmentManager, "Виберіть довжину")
        }
        binding.sizePickerText.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.materialInput,
                "size",binding.sizePickerText,"2"
            )
            newFragment.show(supportFragmentManager, "Виберіть розмір")
        }
        binding.modelInput.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.modelInput,
                "model",binding.euText,"1"
            )
            newFragment.show(supportFragmentManager, "Виберіть модель")
        }
        binding.materialInput.setOnClickListener {
            val newFragment = NumberPickerDialog(
                binding.materialInput,
                "material",binding.euText,"1"
            )
            newFragment.show(supportFragmentManager, "Виберіть матеріал")
        }
        binding.applyButton.setOnClickListener {
            viewModel.createDeclaration(
                listPhotoNumbers,
                binding.sizePickerText.text.toString(),
                binding.euText.text.toString(),
                binding.sizeLengthText.text.toString(),
                binding.sizeWidthText.text.toString(),
                binding.modelInput.text.toString(),
                binding.materialInput.text.toString(),
                binding.descriptionInput.text.toString(),
                binding.priceInput.text.toString(),
                userID,
                binding.selectPhoto1,
                binding.selectPhoto2,
                binding.selectPhoto3,
                binding.selectPhoto4,
                binding.selectPhoto5,
                binding.selectPhoto6,
                binding.selectPhoto7,
                binding.selectPhoto8,
            this,
                listPhotoLinks,
                binding.errorCreatingDeclaration,
                binding.progress)
        }

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            listPhotoNumbers.add(requestCode)
            listPhotoLinks = viewModel.getNewPhoto(
                    requestCode,
                    resultCode,
                    data!!,
                    userID,
                    binding.selectPhoto1,
                    binding.selectPhoto2,
                    binding.selectPhoto3,
                    binding.selectPhoto4,
                    binding.selectPhoto5,
                    binding.selectPhoto6,
                    binding.selectPhoto7,
                    binding.selectPhoto8,
                    binding.cameraIcon,
                    listPhotoLinks
            )
        }
    }
}