package com.nahal.developer.family.nahal.database.repository

import android.content.Context
import com.nahal.developer.family.nahal.database.dao.UserDao
import com.nahal.developer.family.nahal.database.database.AppDatabase
import com.nahal.developer.family.nahal.database.database.AppDatabase.Companion.databaseExecutor
import com.nahal.developer.family.nahal.database.database.AppDatabase.Companion.getDatabase
import com.nahal.developer.family.nahal.database.entity.UserModel
import java.util.concurrent.atomic.AtomicReference

class UserRepository private constructor(context: Context) {
    private val db: AppDatabase
    private val userDao: UserDao

    init {
        db = getDatabase(context)!!
        userDao = db.userDao()
    }

    val allUsers: List<UserModel?>?
        //region User
        get() = userDao.allUserModel

    //    public LiveData<List<UserModel>> getAllUsers() {
    //        LiveData<List<UserModel>> result=userDao.getAllUserModel();
    //        return result;
    //    }
    fun getUser(UserName: String?): UserModel {
        return userDao.getUserDataByUserName(UserName)
    }

    fun checkUserNameIsExist(UserName: String?, userId: Long): Boolean {
        val model: UserModel = userDao.checkUserNameIsExist(UserName)
        return if (userId > 0) {
            if (model != null) model.ID !== userId else false
        } else {
            model != null
        }
    }

    fun insertAllUsers(models: Array<UserModel?>): Boolean {
        val insertIds = AtomicReference<List<Long>>()
        databaseExecutor.execute { insertIds.set(userDao.insertAll(models)) }
        return insertIds.get().isNotEmpty()
    }

    fun insertUser(model: UserModel?): Boolean {
        return userDao.insert(model) > 0
    }

    fun DeleteUser(model: UserModel?): Boolean {
        return userDao.delete(model) > 0
    }

    fun UpdateUser(model: UserModel?): Boolean {
        return userDao.update(model) > 0
    } //endregion

    companion object {
        private const val TAG = "control repo"
        private var instance: UserRepository? = null
        fun getInstance(context: Context): UserRepository? {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance
        }
    }
}