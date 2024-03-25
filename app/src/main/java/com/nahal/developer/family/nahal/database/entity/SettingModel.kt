package com.nahal.developer.family.nahal.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SettingModel")
class SettingModel : BaseEntity() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SettingId", index = true)
    var SettingId = 0

    @ColumnInfo(name = "SettingValue")
    var SettingValue: String? = null
}