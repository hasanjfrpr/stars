package com.example.stars.db.local

import androidx.room.*

@Entity
class User(val name:String? , val lastName:String? , val phoneNumber:String?,
val signUpDate:String?) {

    @PrimaryKey(autoGenerate = true)
    val id:Int=0


}

@Dao
interface  UserDao{

    @Insert
    fun insertUser(user:User)

    @Delete
    fun deleteUser(user:User)



}