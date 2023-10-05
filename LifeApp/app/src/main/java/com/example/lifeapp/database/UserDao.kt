package com.example.lifeapp.database

import androidx.room.*
import com.example.lifeapp.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("select * from user_table where userID = :key")
    fun getUser(key: Long): User?
}