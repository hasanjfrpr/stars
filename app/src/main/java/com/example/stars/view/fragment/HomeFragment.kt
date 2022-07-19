package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.HomeAdapter
import com.example.stars.base.BaseFragment
import com.example.stars.db.local.AppDataBase
import com.example.stars.db.local.User
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment() {

    var toolbar : androidx.appcompat.widget.Toolbar?  = null
    lateinit var adapter : HomeAdapter
    var userList : MutableList<User> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setupRecycler()
    }

    private fun init( view: View){
        toolbar  = view.findViewById(R.id.toolbar_home);
        toolbar!!.setTitleTextColor(ContextCompat.getColor(requireContext() , R.color.on_primary))
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    }
    private fun setupRecycler(){


         AppDataBase.getInstance(requireContext()).getDao().getAllUser().observe(this.viewLifecycleOwner){
             if(it.isEmpty()){
                 empty_layout.visibility = View.VISIBLE
                 RV_home.visibility = View.GONE
             }else {
                 empty_layout.visibility = View.GONE
                 RV_home.visibility = View.VISIBLE
             }
             userList.clear()
             userList.addAll(it)
             adapter = HomeAdapter(userList , requireContext())
             RV_home.adapter = adapter
             RV_home.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
         }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.ss, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView =
            MenuItemCompat.getActionView(searchItem) as androidx.appcompat.widget.SearchView


        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("newText", "onQueryTextChange: " + newText)
                adapter.filter.filter(newText)
                adapter.notifyDataSetChanged()
                return false
            }
        })


        super.onCreateOptionsMenu(menu, inflater)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.sort){

            var inflate = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_main , null)
            var dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(inflate)
            var cancel = inflate.findViewById<TextView>(R.id.TV_bottomSheet_cancel)
            var ok = inflate.findViewById<TextView>(R.id.TV_bottomSheet_ok)
            var radioGroupe = inflate.findViewById<RadioGroup>(R.id.radio_groupe_main)
            cancel.setOnClickListener { dialog.dismiss() }
            ok.setOnClickListener {
                adapter = HomeAdapter(userList , requireContext())
                RV_home.adapter = adapter
                RV_home.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
                adapter.notifyDataSetChanged()

                dialog.dismiss()
            }
            radioGroupe.setOnCheckedChangeListener { radioGroup, chekedId ->
                Log.i("radioGr", "onOptionsItemSelected: "+ radioGroup.id + "  "+R.id.radio_most_bed  + "  " + chekedId)
                when(chekedId){
                    R.id.radio_most_bed->{
                        Collections.sort(userList, object : Comparator<User>{
                            override fun compare(p0: User?, p1: User?): Int {
                                return p0!!.bedbes!!.toDouble().compareTo(p1!!.bedbes!!.toDouble())
                            }

                        })



                       }
                    R.id.radio_most_bes->{
                        Collections.sort(userList , object : Comparator<User>{
                            override fun compare(p0: User?, p1: User?): Int {
                                return  p1!!.bedbes!!.toDouble().compareTo(p0!!.bedbes!!.toDouble())
                            }

                        })
                    }
                    R.id.radio_latest->{
                        Collections.sort(userList , object : Comparator<User>{
                            override fun compare(p0: User?, p1: User?): Int {
                             var a =  adapter.calculateReminderTime(p0!!.signUpDate.toString())
                             var  b =  adapter.calculateReminderTime(p1!!.signUpDate.toString())
                                return  a.toDouble().compareTo(b.toDouble())
                            }

                        })
                    }
                    R.id.radio_newest->{
                        Collections.sort(userList , object : Comparator<User>{
                            override fun compare(p0: User?, p1: User?): Int {
                                var a =  adapter.calculateReminderTime(p0!!.signUpDate.toString())
                                var  b =  adapter.calculateReminderTime(p1!!.signUpDate.toString())
                                return  b.toDouble().compareTo(a.toDouble())
                            }

                        })
                    }
                }
            }


            dialog.setCancelable(false)

            dialog.show()

        }

        return true
    }





}