package com.example.stars.view.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stars.R
import com.example.stars.adapter.SettingRecyclerAdapter
import com.example.stars.base.BaseFragment
import com.example.stars.base.formatNumber
import com.example.stars.db.local.AppDataBase
import com.example.stars.dialog.AddSettingDialog
import com.example.stars.dialog.SavePeriodPriceDialog
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.synthetic.main.fragment_setting.*


//
class SettingFragment : BaseFragment() ,  AddSettingDialog.SettingDialogInterFace , SavePeriodPriceDialog.onClickOkPric {

     lateinit  var settingModelList:MutableList<SettingModel>
    // lateinit var addDialog : AddSettingDialog
   //  lateinit var priceDialog: SavePeriodPriceDialog
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
       // priceDialog = SavePeriodPriceDialog()
       // addDialog = AddSettingDialog()
        settingModelList = ArrayList<SettingModel>()
        settingModelList.addAll(AppDataBase.getInstance(requireContext()).getDao().getAllSettingModel())
        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
        RV_setting.adapter=adapter
        RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)

        for(i in 0 until settingModelList.size){
            if (settingModelList[i].title=="شهریه"){
                IV_add_setting.visibility = View.VISIBLE
                TV_periodPrice.text = formatNumber(settingModelList[i].price!!.toDouble())
            }
        }

        event()


    }



    private fun event(){
        IV_add_setting.setOnClickListener {
          var  addDialog = AddSettingDialog()
            addDialog.settingInterFace=this
           addDialog.show(requireActivity().supportFragmentManager , "")
            addDialog.isCancelable=false
        }

        Mbtn_periodPrice_save.setOnClickListener {
          var  priceDialog = SavePeriodPriceDialog()
            priceDialog.oc=this
           priceDialog.show(requireActivity().supportFragmentManager,"")
            priceDialog.isCancelable=false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOkClick(title: String?, price: String?) {
        var titles = AppDataBase.getInstance(requireContext()).getDao().getTitleSettingModel(title.toString())
            if(titles.isNullOrEmpty()){
                settingModelList.add(SettingModel(title , price))
                adapter = SettingRecyclerAdapter(settingModelList,requireContext())
                RV_setting.adapter=adapter
                RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                adapter.notifyDataSetChanged()
                AppDataBase.getInstance(requireContext()).getDao().insertSettingPrice(SettingModel(title,
                    price))

            }else{
                AppDataBase.getInstance(requireContext()).getDao().updateItemSettingModel(title.toString() , price.toString())
                    settingModelList.clear()
                settingModelList = ArrayList<SettingModel>()
                    settingModelList.addAll(AppDataBase.getInstance(requireContext()).getDao().getAllSettingModel())
                    adapter = SettingRecyclerAdapter(settingModelList,requireContext())
                    RV_setting.adapter=adapter
                    RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                    adapter.notifyDataSetChanged()

            }




    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(price: String?) {

       var ss= AppDataBase.getInstance(requireContext()).getDao().getTitleSettingModel("شهریه")
            if(ss.isNullOrEmpty()){
                TV_periodPrice.text = formatNumber(price!!.toDouble())
            AppDataBase.getInstance(requireContext()).getDao().insertSettingPrice(SettingModel("شهریه",
                price))
                settingModelList.add(SettingModel("شهریه" , price))
            if (!price.isNullOrBlank()) {
                IV_add_setting.visibility = View.VISIBLE
            }

        adapter = SettingRecyclerAdapter(settingModelList,requireContext())
        RV_setting.adapter=adapter
        RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
        adapter.notifyDataSetChanged()
            }else{
                AppDataBase.getInstance(requireContext()).getDao().updateItemSettingModel("شهریه" , price.toString())
                settingModelList.clear()
                TV_periodPrice.text = formatNumber(price!!.toDouble())
                settingModelList.addAll(AppDataBase.getInstance(requireContext()).getDao().getAllSettingModel())
                adapter = SettingRecyclerAdapter(settingModelList,requireContext())
                RV_setting.adapter=adapter
                RV_setting.layoutManager=GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                adapter.notifyDataSetChanged()

            }
        }






}