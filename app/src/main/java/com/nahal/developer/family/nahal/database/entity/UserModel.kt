package com.nahal.developer.family.nahal.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserModel")
class UserModel : BaseEntity() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID", index = true)
    var ID: Long = 0

    @ColumnInfo(name = "FULL_NAME")
    var FULL_NAME: String? = null

    @ColumnInfo(name = "USER_NAME")
    var USER_NAME: String? = null

    @ColumnInfo(name = "PHONE")
    var PHONE: String? = null

    @ColumnInfo(name = "PASSWORD")
    var PASSWORD: String? = null
}