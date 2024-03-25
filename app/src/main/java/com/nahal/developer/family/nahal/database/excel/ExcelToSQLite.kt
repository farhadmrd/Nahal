package com.nahal.developer.family.nahal.database.excel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ExcelToSQLite {
    private var mContext: Context
    private var database: SQLiteDatabase? = null
    private var mDbName: String
    private var dropTable = false

    constructor(context: Context, dbName: String) {
        mContext = context
        mDbName = dbName
        try {
            database = SQLiteDatabase.openOrCreateDatabase(
                mContext.getDatabasePath(mDbName).absolutePath,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    constructor(context: Context, dbName: String, dropTable: Boolean) {
        mContext = context
        mDbName = dbName
        this.dropTable = dropTable
        try {
            database = SQLiteDatabase.openOrCreateDatabase(
                mContext.getDatabasePath(mDbName).absolutePath,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun importFromAsset(assetFileName: String?, listener: ImportListener?) {
        listener?.onStart()
        Thread {
            try {
                working(mContext.assets.open(assetFileName!!))
                if (listener != null) {
                    handler.post { listener.onCompleted(mDbName) }
                }
            } catch (e: Exception) {
                if (database != null && database!!.isOpen) {
                    database!!.close()
                }
                if (listener != null) {
                    handler.post { listener.onError(e) }
                }
            }
        }.start()
    }

    fun importFromFile(filePath: String?, listener: ImportListener?) {
        importFromFile(File(filePath), listener)
    }

    private fun importFromFile(file: File, listener: ImportListener?) {
        listener?.onStart()
        Thread {
            try {
                working(FileInputStream(file))
                if (listener != null) {
                    handler.post { listener.onCompleted(mDbName) }
                }
            } catch (e: Exception) {
                if (database != null && database!!.isOpen) {
                    database!!.close()
                }
                if (listener != null) {
                    handler.post { listener.onError(e) }
                }
            }
        }.start()
    }

    @Throws(Exception::class)
    private fun working(stream: InputStream) {
        val workbook = HSSFWorkbook(stream)
        val sheetNumber = workbook.numberOfSheets
        for (i in 0 until sheetNumber) {
            createTable(workbook.getSheetAt(i))
        }
        database!!.close()
    }

    private fun createTable(sheet: Sheet) {
        var cursor: Cursor? = null
        try {
            val createTableSql = StringBuilder("CREATE TABLE IF NOT EXISTS ")
            createTableSql.append(sheet.sheetName)
            createTableSql.append("(")
            val rit = sheet.rowIterator()
            val rowHeader = rit.next()
            val columns: MutableList<String> = ArrayList()
            for (i in 0 until rowHeader.physicalNumberOfCells) {
                createTableSql.append(rowHeader.getCell(i).stringCellValue)
                if (i == rowHeader.physicalNumberOfCells - 1) {
                    createTableSql.append(" TEXT")
                } else {
                    createTableSql.append(" TEXT,")
                }
                columns.add(rowHeader.getCell(i).stringCellValue)
            }
            createTableSql.append(")")
            if (dropTable) database!!.execSQL("DROP TABLE IF EXISTS " + sheet.sheetName)
            database!!.execSQL(createTableSql.toString())
            for (column in columns) {
                cursor = database!!.rawQuery("SELECT * FROM " + sheet.sheetName, null)
                // grab cursor for all data
                val deleteStateColumnIndex =
                    cursor.getColumnIndex(column) // see if the column is there
                if (deleteStateColumnIndex < 0) {
                    val type = "TEXT"
                    // missing_column not there - add it
                    database!!.execSQL(
                        "ALTER TABLE " + sheet.sheetName
                                + " ADD COLUMN " + column + " " + type + " NULL;"
                    )
                }
            }
            while (rit.hasNext()) {
                val row = rit.next()
                val values = ContentValues()
                for (n in 0 until row.physicalNumberOfCells) {
                    if (row.getCell(n).cellType == CellType.NUMERIC) {
                        values.put(columns[n], row.getCell(n).numericCellValue)
                    } else {
                        values.put(columns[n], row.getCell(n).stringCellValue)
                    }
                }
                val result = database!!.insertWithOnConflict(
                    sheet.sheetName,
                    null, values, SQLiteDatabase.CONFLICT_IGNORE
                )
                if (result < 0) {
                    throw RuntimeException("Insert value failed!")
                }
            }
        } finally {
            cursor?.close()
        }
    }

    interface ImportListener {
        fun onStart()
        fun onCompleted(dbName: String?)
        fun onError(e: Exception?)
    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())
    }
}