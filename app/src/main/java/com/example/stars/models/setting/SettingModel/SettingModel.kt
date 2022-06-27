package com.example.stars.models.setting.SettingModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SettingModel(var title:String? , var price:String?){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}