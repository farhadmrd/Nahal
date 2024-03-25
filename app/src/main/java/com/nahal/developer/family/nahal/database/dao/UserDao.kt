package com.nahal.developer.family.nahal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.nahal.developer.family.nahal.database.entity.UserModel

@Dao
interface UserDao : BaseDao<UserModel?> {
    @get:Query("SELECT * FROM UserModel")
    val allUserModel: List<UserModel?>?

    //    LiveData<List<UserModel>> getAllUserModel();
    @Query("SELECT * FROM UserModel WHERE ID IN (:userIds)")
    fun getUserDataByIds(userIds: IntArray): List<UserModel>

    @Query("SELECT * FROM UserModel WHERE USER_NAME like :UserName LIMIT 1")
    fun getUserDataByUserName(UserName: String?): UserModel

    @Query("SELECT * FROM UserModel WHERE USER_NAME like :UserName  LIMIT 1")
    fun checkUserNameIsExist(UserName: String?): UserModel
}