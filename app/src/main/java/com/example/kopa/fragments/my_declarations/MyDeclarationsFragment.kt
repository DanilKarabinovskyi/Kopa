package com.example.kopa.fragments.my_declarations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.kopa.BottomNavBarActivity
import com.example.kopa.R
import com.example.kopa.databinding.MyDeclarationsLayoutBinding
import com.example.kopa.fragments.active_declarations.ActiveDeclarationsFragment
import com.example.kopa.fragments.adapters.DeclarationListener
import com.example.kopa.fragments.adapters.DeclarationsAdapter
import com.example.kopa.fragments.adapters.PagerAdapter
import com.example.kopa.fragments.all_declarations.AllDeclarationsFragment
import com.example.kopa.fragments.all_declarations.AllDeclarationsFragmentVewModel
import com.example.kopa.fragments.archieved_declarations.ArchivedDeclarationsFragment

class MyDeclarationsFragment(userId:String):Fragment() {
    private lateinit var binding: MyDeclarationsLayoutBinding
    private lateinit var viewModel: MyDeclarationsFragmentViewModel
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager
    val userID = userId
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.my_declarations_layout,
                container,
                false)
        viewModel = ViewModelProvider(this).get(MyDeclarationsFragmentViewModel::class.java)
        binding.myDeclarationsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        pagerAdapter = PagerAdapter(childFragmentManager)

        viewPager = binding.viewpager

        var firstFragment = ActiveDeclarationsFragment(userID)
        var secondFragment = ArchivedDeclarationsFragment(userID)

        viewPager.adapter = pagerAdapter
        pagerAdapter.addFragment(firstFragment,"Вантажники")
        pagerAdapter.addFragment(secondFragment,"Перевізники")
        binding.tabs.setupWithViewPager(viewPager)
        pagerAdapter.notifyDataSetChanged()
        return binding.root
    }
}