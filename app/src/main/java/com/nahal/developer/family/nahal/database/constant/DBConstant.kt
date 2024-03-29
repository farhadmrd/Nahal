package com.nahal.developer.family.nahal.database.constant

/**
 * Copyright (c) 2020 farhad moradi
 * farhadmrd@gmail.com
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
object DBConstant {
    const val DATABASE_NAME = "NahalDb.db"
    const val DATABASE_ADMIN_USER = "admin"
    const val DATABASE_ADMIN_PASSWORD = "1367"
    const val DATABASE_SECRET_KEY = "Nahal"
    const val DATABASE_VERSION = 1
    const val EXIT_CODE_ERROR = 1
    const val EXIT_CODE_ERROR_MESSAGE = "با خطا مواجه شد"

    /** Error while choosing backup to restore. Maybe no file selected  */
    const val EXIT_CODE_ERROR_BACKUP_FILE_CHOOSER = 2
    const val EXIT_CODE_ERROR_BACKUP_FILE_CHOOSER_MESSAGE = "خطای انتخاب فایل پشتیبان"

    /** Error while choosing backup file to create. Maybe no file selected  */
    const val P_FILE_CREATOR = 3
    const val P_FILE_CREATOR_MESSAGE = "خطای ایجاد فایل در حافظه"

    /** [BACKUP_FILE_LOCATION_CUSTOM_FILE] is set but [RoomBackup.backupLocationCustomFile] is not set  */
    const val EXIT_CODE_ERROR_BACKUP_LOCATION_FILE_MISSING = 4
    const val EXIT_CODE_ERROR_BACKUP_LOCATION_FILE_MISSING_MESSAGE = "فایل پشتیبان یافت نشد"

    /** [RoomBackup.backupLocation] is not set  */
    const val EXIT_CODE_ERROR_BACKUP_LOCATION_MISSING = 5
    const val EXIT_CODE_ERROR_BACKUP_LOCATION_MISSING_MESSAGE = "مسیر فایل پشتیبان یافت نشد"

    /** Restore dialog for internal/external storage was canceled by user  */
    const val EXIT_CODE_ERROR_BY_USER_CANCELED = 6
    const val EXIT_CODE_ERROR_BY_USER_CANCELED_MESSAGE = "توسط کاربر لغو شد"

    /** Cannot decrypt provided backup file  */
    const val EXIT_CODE_ERROR_DECRYPTION_ERROR = 7
    const val EXIT_CODE_ERROR_DECRYPTION_ERROR_MESSAGE = "خطای رمزگشایی فایل پشتیبان"

    /** Cannot encrypt database backup  */
    const val EXIT_CODE_ERROR_ENCRYPTION_ERROR = 8
    const val EXIT_CODE_ERROR_ENCRYPTION_ERROR_MESSAGE = "خطای رمزنگاری فایل پشتیبان"

    /** You tried to restore a encrypted backup but [RoomBackup.backupIsEncrypted] is set to false  */
    const val EXIT_CODE_ERROR_RESTORE_BACKUP_IS_ENCRYPTED = 9
    const val EXIT_CODE_ERROR_RESTORE_BACKUP_IS_ENCRYPTED_MESSAGE =
        "فایل پشتیبان انتخاب شده رمزنگاری شده است"

    /** No backups to restore are available in internal/external sotrage  */
    const val EXIT_CODE_ERROR_RESTORE_NO_BACKUPS_AVAILABLE = 10
    const val EXIT_CODE_ERROR_RESTORE_NO_BACKUPS_AVAILABLE_MESSAGE = "فایل پشتیبان یافت نشد"

    /** No room database to backup is provided   */
    const val EXIT_CODE_ERROR_ROOM_DATABASE_MISSING = 11
    const val EXIT_CODE_ERROR_ROOM_DATABASE_MISSING_MESSAGE = "پایگاه داده یافت نشد"

    /** Storage permissions not granted for custom dialog  */
    const val EXIT_CODE_ERROR_STORAGE_PERMISSONS_NOT_GRANTED = 12
    const val EXIT_CODE_ERROR_STORAGE_PERMISSONS_NOT_GRANTED_MESSAGE =
        "مجوز دسترسی به حافظه وجود ندارد. لطفا از طریق تنظیمات گوشی یا تبلت خود این مجوز را به برنامه بدهید"

    /** Cannot decrypt provided backup file because the password is incorrect  */
    const val EXIT_CODE_ERROR_WRONG_DECRYPTION_PASSWORD = 13
    const val EXIT_CODE_ERROR_WRONG_DECRYPTION_PASSWORD_MESSAGE =
        "کلمه عبور بازیابی پایگاه داده اشتباه است"

    /** No error, action successful  */
    const val EXIT_CODE_SUCCESS = 0
    const val EXIT_CODE_SUCCESS_MESSAGE = "عملیات با موفقیت انجام شد"
}