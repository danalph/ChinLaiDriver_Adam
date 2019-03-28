package addam.com.my.chinlaicustomer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Addam on 7/1/2019.
 */
@Database(entities = [User::class, Cart::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
}