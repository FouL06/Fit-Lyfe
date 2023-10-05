package com.example.lifeapp.repositories

import android.content.Context
import com.example.lifeapp.database.LifeAppDatabase
import com.example.lifeapp.models.User

class UserRepository private constructor(appContext: Context) {

    var userDao = LifeAppDatabase.getInstance(appContext).userDao()

    companion object Factory {
        private var instance: UserRepository? = null

        fun getInstance(appContext: Context): UserRepository {
            synchronized(this) {
                if (instance == null) {
                    instance = UserRepository(appContext)
                }
            }
            return instance!! // Kotlin is unaware of JVM synchronization
        }
    }

    fun insertUser(user: User) = userDao.insert(user)

    fun updateUser(user: User) = userDao.update(user)

}