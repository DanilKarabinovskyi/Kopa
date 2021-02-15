package com.example.kopa.fragments.all_declarations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kopa.*
import com.example.kopa.databinding.AllDeclarationsLayoutBinding
import com.example.kopa.databinding.SplashScreenLayoutBinding
import com.example.kopa.fragments.adapters.DeclarationListener
import com.example.kopa.fragments.adapters.DeclarationsAdapter
import com.example.kopa.fragments.splash_screen.SplashScreenFragmentViewModel
import com.google.android.material.textfield.TextInputEditText

class AllDeclarationsFragment(userID:String): Fragment() {
    private lateinit var binding: AllDeclarationsLayoutBinding
    private lateinit var viewModel: AllDeclarationsFragmentVewModel
    lateinit var  adapter: DeclarationsAdapter
    val userId = userID
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.all_declarations_layout,
                container,
                false)
        adapter = DeclarationsAdapter(
                DeclarationListener { productId,userID,description ->
                    val activity = requireActivity() as BottomNavBarActivity
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("productId", productId)
                    intent.putExtra("id", userId)
                    intent.putExtra("type", "like")
                    activity.finish()
                    activity.startActivity(intent)
                },"like")
        viewModel = ViewModelProvider(this).get(AllDeclarationsFragmentVewModel::class.java)
        viewModel.getList(adapter)
        binding.allDeaclarationsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.declarationsList.adapter = adapter
        val manager = GridLayoutManager(requireActivity() as BottomNavBarActivity, 1)
        binding.declarationsList.layoutManager = manager
        return binding.root
    }
//    fun sort(model: TextInputEditText, material: TextInputEditText,
//             sizeLength: TextInputEditText, sizeWidth: TextInputEditText,
//             priceLow: TextInputEditText, priceMax: TextInputEditText){
//        viewModel.sort(adapter, model,material,sizeLength,sizeWidth,priceLow,priceMax)
//    }
}