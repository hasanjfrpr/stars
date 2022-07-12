package com.example.stars.db.local

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.stars.models.setting.SettingModel.SettingModel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class User(
    var name: String?, var lastName: String?, var phoneNumber: String?,
    var signUpDate: String?, var periodPrice: String? , var buy:String?,
    var bedbes:String?,
    var renew:String?,
    var renewCount:String?,
    var note : String?
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


}


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)




    @Query("update User set name =:name , lastName=:lastName , phoneNumber=:phoneNumber , signUpDate=:signUpDate , periodPrice=:periodPrice , buy=:buy , bedbes=:bedbes,renew=:renew , renewCount=:renewCount , note=:note , id=:id  where id=:id ")
    fun updateUser(name:String?, lastName: String? , phoneNumber: String? , signUpDate: String? ,periodPrice: String? , buy: String? ,
    bedbes: String? , renew: String? , renewCount: String? , note: String? ,id:Int?)


    @Delete
    fun deleteUser(user: User)

    @Query("select * from User order by id desc")
    fun getAllUser() : LiveData<List<User>>

    ///settingmodel
    @Insert
    fun insertSettingPrice(settingModdl: SettingModel)

    @Query("delete from SettingModel where title = :title")
    fun deleteSettingPrice(title: String)

    @Query("select * from SettingModel ")
    fun getAllSettingModel(): List<SettingModel>

    @Query("select price from SettingModel where title ='شهریه'")
    fun getPeroidPrice(): String

    @Query("select title from SettingModel where title=:title")
    fun getTitleSettingModel(title:String): String

    @Query("UPDATE SettingModel SET title=:title,price=:price  WHERE title = :title")
    fun updateItemSettingModel(title:String , price:String )



}

@Database(entities = [User::class, SettingModel::class], version = 6, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getDao(): AppDao

    companion object {
        var appDb: AppDataBase? = null

        public fun getInstance(context: Context): AppDataBase {
            if (appDb == null) {
                appDb = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "StarsDb"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return appDb!!;
        }
    }

}