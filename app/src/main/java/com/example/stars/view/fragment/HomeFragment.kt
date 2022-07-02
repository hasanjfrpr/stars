package com.example.stars.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.HomeAdapter
import com.example.stars.base.BaseFragment
import com.example.stars.db.local.AppDataBase
import com.example.stars.db.local.User
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecycler()
    }

    private fun init(){
        requireActivity().setActionBar(toolbar_home)
    }
    private fun setupRecycler(){
         AppDataBase.getInstance(requireContext()).getDao().getAllUser().observe(this.viewLifecycleOwner){
             var adapter = HomeAdapter(it as MutableList<User> , requireContext())
             RV_home.adapter = adapter
             RV_home.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
         }

    }

}