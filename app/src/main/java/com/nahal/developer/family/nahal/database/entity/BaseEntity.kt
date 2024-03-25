package com.nahal.developer.family.nahal.database.entity

import androidx.room.ColumnInfo
import com.nahal.developer.family.nahal.core.G
import com.nahal.developer.family.nahal.helper.Convert

open class BaseEntity {
    @ColumnInfo(name = "IS_ACTIVE")
    var IS_ACTIVE: Boolean? = null

    @ColumnInfo(name = "Title")
    var Title: String? = null

    @ColumnInfo(name = "CREATE_BY")
    var CREATE_BY: String? = null

    @ColumnInfo(name = "CREATE_DATE")
    var CREATE_DATE: Long? = null

    @ColumnInfo(name = "FA_CREATE_DATE")
    var FA_CREATE_DATE: String? = null

    @ColumnInfo(name = "MODIFIED_BY")
    var MODIFIED_BY: String? = null
        set(MODIFIED_BY) {
            field = MODIFIED_BY ?: G.currentUser?.FULL_NAME
        }

    @ColumnInfo(name = "MODIFIED_DATE")
    var MODIFIED_DATE: Long? = null
        set(MODIFIED_DATE) {
            field = MODIFIED_DATE ?: System.currentTimeMillis()
        }

    @ColumnInfo(name = "FA_MODIFIED_DATE")
    var FA_MODIFIED_DATE: String? = null
        set(FA_MODIFIED_DATE) {
            field = FA_MODIFIED_DATE ?: Convert.GetPersianToday()
        }
}