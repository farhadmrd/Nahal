package com.nahal.developer.family.nahal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.nahal.developer.family.nahal.database.entity.SettingModel

@Dao
interface SettingDao : BaseDao<SettingModel?> {
    @get:Query("SELECT * FROM SettingModel")
    val allSettingModel: List<SettingModel?>?

    @Query("SELECT * FROM SettingModel WHERE SettingId = :SettingId LIMIT 1")
    fun getSettingModelById(SettingId: Int): SettingModel
}