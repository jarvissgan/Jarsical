package data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.junit.runner.manipulation.Ordering
import java.security.AccessControlContext

@Database(entities = [User::class], version = 1, exportSchema = false) //good practice to have version history
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile //write to this field are made visable to other threads
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            // checking if database != 0
            val tempInstance = INSTANCE

            //if it exists, return INSTANCE != null, otherwise create new instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                // only use one INSTANCE, very demanding to use multiple
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}