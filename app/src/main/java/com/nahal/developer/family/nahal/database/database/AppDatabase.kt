package com.nahal.developer.family.nahal.database.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nahal.developer.family.nahal.database.constant.DBConstant
import com.nahal.developer.family.nahal.database.converters.Converters
import com.nahal.developer.family.nahal.database.dao.SettingDao
import com.nahal.developer.family.nahal.database.dao.UserDao
import com.nahal.developer.family.nahal.database.entity.UserModel
import com.nahal.developer.family.nahal.database.entity.SettingModel
import java.util.concurrent.Executors

@Database(
    entities = [UserModel::class, SettingModel::class],
    version = DBConstant.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(*[Converters::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun settingDao(): SettingDao
    private class PopulateDbAsync internal constructor(db: AppDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val userDao: UserDao
        private val settingDao: SettingDao

        init {
            userDao = db!!.userDao()
            settingDao = db.settingDao()
        }


        override fun doInBackground(vararg params: Void?): Void? {
            // Add something to the Database every time it is built
            // Only in the test phase!
            return null
        }

    }

    companion object {
        const val DEBUG = false
        private var INSTANCE: AppDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = databaseBuilder<AppDatabase>(
                        context!!,
                        AppDatabase::class.java, DBConstant.DATABASE_NAME
                    ) // TODO: write a migration
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }
    }
}