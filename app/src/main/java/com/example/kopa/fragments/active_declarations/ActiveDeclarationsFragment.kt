package com.example.kopa.fragments.active_declarations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.R
import com.example.kopa.databinding.ActiveDeclarationsLayoutBinding
import com.example.kopa.fragments.adapters.DeclarationListener
import com.example.kopa.fragments.adapters.DeclarationsAdapter

class ActiveDeclarationsFragment(userId: String): Fragment() {
    private lateinit var binding: ActiveDeclarationsLayoutBinding
    private lateinit var viewModel: ActiveDeclarationsFragmentViewModel
    lateinit var  adapter: DeclarationsAdapter
    val userID = userId

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.active_declarations_layout,
                container,
                false)
        adapter = DeclarationsAdapter(
                DeclarationListener { lessonName ->

                },"sold")
        viewModel = ViewModelProvider(this).get(ActiveDeclarationsFragmentViewModel::class.java)
        binding.activeDeclarationsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getList(adapter,userID)
        binding.declarationsList.adapter = adapter
        val manager = GridLayoutManager(requireActivity() as BottomNavBarActivity, 1)
        binding.declarationsList.layoutManager = manager
        return binding.root
    }
}