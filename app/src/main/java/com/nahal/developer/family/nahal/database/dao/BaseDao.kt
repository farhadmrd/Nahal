package com.nahal.developer.family.nahal.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    /**
     * @param ts to insert
     * @return the rowId(s) inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ts: Array<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    /**
     * @param ts Objects to insert
     * @return the amount of rows updated
     */
    @Update
    fun update(ts: T): Int

    /**
     * @param ts Objects to delete
     * @return the amount of rows deleted
     */
    @Delete
    fun delete(ts: T): Int
}