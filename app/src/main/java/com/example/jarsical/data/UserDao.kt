package com.example.jarsical.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignores if duplicate is found
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC") // asc = ascending
    fun readAllData(): LiveData<List<User>>
}