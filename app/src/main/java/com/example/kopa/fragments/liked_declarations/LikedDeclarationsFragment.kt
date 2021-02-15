package com.example.kopa.fragments.liked_declarations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.DetailActivity
import com.example.kopa.R
import com.example.kopa.databinding.ArchivedDeclarationsLayoutBinding
import com.example.kopa.databinding.LikedDeclarationsLayoutBinding
import com.example.kopa.fragments.adapters.DeclarationListener
import com.example.kopa.fragments.adapters.DeclarationsAdapter

class LikedDeclarationsFragment(userId: String):Fragment() {
    private lateinit var binding: LikedDeclarationsLayoutBinding
    private lateinit var viewModel: LikedDeclarationsFragmentViewModel
    lateinit var  adapter: DeclarationsAdapter
    val userID = userId

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.liked_declarations_layout,
                container,
                false)
        adapter = DeclarationsAdapter(
                DeclarationListener { productId,userId,description ->
                    val activity = requireActivity() as BottomNavBarActivity
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("productId", productId)
                    intent.putExtra("id", userId)
                    intent.putExtra("type", "like")
                    activity.finish()
                    activity.startActivity(intent)
                },"like")
        viewModel = ViewModelProvider(this).get(LikedDeclarationsFragmentViewModel::class.java)
        binding.likedDeclarationsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getList(adapter,userID)
        binding.declarationsList.adapter = adapter
        val manager = GridLayoutManager(requireActivity() as BottomNavBarActivity, 1)
        binding.declarationsList.layoutManager = manager

        return binding.root
    }
}