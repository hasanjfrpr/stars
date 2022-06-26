package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.SettingRecyclerAdapter
import com.example.stars.base.BaseFragment
import com.example.stars.dialog.AddSettingDialog
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.fragment_setting.*

//
class SettingFragment : BaseFragment() ,  AddSettingDialog.SettingDialogInterFace{

     lateinit  var settingModelList:MutableList<SettingModel>
     lateinit var addDialog : AddSettingDialog
     lateinit var adapter : SettingRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDialog = AddSettingDialog()
        settingModelList = ArrayList<SettingModel>()
        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
        event()

        adapter.onLongClickAdapterItem(object : SettingRecyclerAdapter.SettingAdapeterInterFace{
            @SuppressLint("NotifyDataSetChanged")
            override fun onLongClick(position: Int) {
                settingModelList.removeAt(position)
        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
        RV_setting.adapter=adapter
        RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        adapter.notifyDataSetChanged()
            }

        })
    }

    private fun event(){
        IV_add_setting.setOnClickListener {
            addDialog.settingInterFace=this
           addDialog.show(childFragmentManager , "")
            addDialog.isCancelable=false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOkClick(title: String?, price: String?) {
        settingModelList.add(SettingModel(title , price))
        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
        RV_setting.adapter=adapter
        RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        adapter.notifyDataSetChanged()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    override fun onLongClick(position: Int) {
//        settingModelList.removeAt(position)
//        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
//        RV_setting.adapter=adapter
//        RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
//        adapter.notifyDataSetChanged()
//    }


}