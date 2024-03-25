package com.nahal.developer.family.nahal.database.repository

import android.content.Context
import com.nahal.developer.family.nahal.database.dao.SettingDao
import com.nahal.developer.family.nahal.database.database.AppDatabase
import com.nahal.developer.family.nahal.database.database.AppDatabase.Companion.getDatabase
import com.nahal.developer.family.nahal.database.entity.SettingModel

class SettingRepository private constructor(context: Context) {
    private val db: AppDatabase
    private val settingDao: SettingDao

    init {
        db = getDatabase(context)!!
        settingDao = db.settingDao()
    }

    val allSettingModel: List<SettingModel?>?
        get() = settingDao.allSettingModel

    fun getSettingModelById(SettingId: Int): SettingModel {
        return settingDao.getSettingModelById(SettingId)
    }

    fun insertSetting(model: SettingModel?): Boolean {
        return settingDao.insert(model) > 0
    }

    fun DeleteSetting(model: SettingModel?): Boolean {
        return settingDao.delete(model) > 0
    }

    fun UpdateSetting(model: SettingModel?): Boolean {
        return settingDao.update(model) > 0
    } //endregion

    companion object {
        private const val TAG = "control repo"
        private var instance: SettingRepository? = null
        fun getInstance(context: Context): SettingRepository? {
            if (instance == null) {
                instance = SettingRepository(context)
            }
            return instance
        }
    }
}