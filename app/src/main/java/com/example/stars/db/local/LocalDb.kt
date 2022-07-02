package com.example.stars.db.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.stars.models.setting.SettingModel.SettingModel

@Entity
class User(
    var name: String?, var lastName: String?, var phoneNumber: String?,
    var signUpDate: String?, var periodPrice: String? , var buy:String?,
    var bedbes:String?
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


}


@Dao
interface AppDao {

    @Insert
    fun insertUser(user: User)

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

@Database(entities = [User::class, SettingModel::class], version = 4, exportSchema = false)
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