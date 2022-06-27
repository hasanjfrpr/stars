package com.example.stars.db.local

import android.content.Context
import androidx.room.*
import com.example.stars.models.setting.SettingModel.SettingModel

@Entity
class User(val name:String? , val lastName:String? , val phoneNumber:String?,
val signUpDate:String?) {

    @PrimaryKey(autoGenerate = true)
    val id:Int=0


}



@Dao
interface  AppDao{

    @Insert
    fun insertUser(user:User)

    @Delete
    fun deleteUser(user:User)

    ///settingmodel



}
@Database(entities = [User::class , SettingModel::class] ,version = 1 , exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract fun getDao() : AppDao

    companion object{
        var appDb : AppDataBase? = null

        public fun getInstance(context : Context) : AppDataBase{
            if (appDb==null){
                appDb= Room.databaseBuilder(context.applicationContext , AppDataBase::class.java , "StarsDb" )
                    .allowMainThreadQueries()
                    .build()
            }
            return appDb!!;
        }
    }

}