package data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import androidx.lifecycle.LiveData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignores if duplicate is found
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC") // asc = ascending
    fun readAllData(): LiveData<List<User>>
}