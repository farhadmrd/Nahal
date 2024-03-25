package com.nahal.developer.family.nahal.database.excel

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.os.Handler
import android.os.Looper
import org.apache.poi.hssf.usermodel.HSSFClientAnchor
import org.apache.poi.hssf.usermodel.HSSFRichTextString
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.ClientAnchor
import java.io.File
import java.io.FileOutputStream

class SQLiteToExcel @JvmOverloads constructor(
    context: Context,
    dbName: String?,
    private val mExportPath: String = Environment.getExternalStorageDirectory()
        .toString() + File.separator
) {
    private var database: SQLiteDatabase? = null
    private var workbook: HSSFWorkbook? = null
    private var mExcludeColumns: List<String?>? = null
    private var mPrettyNameMapping: HashMap<String?, String>? = null
    private var mCustomFormatter: ExportCustomFormatter? = null

    init {
        try {
            database = SQLiteDatabase.openOrCreateDatabase(
                context.getDatabasePath(dbName).absolutePath,
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Set the exclude columns list
     *
     * @param excludeColumns
     */
    fun setExcludeColumns(excludeColumns: List<String?>?) {
        mExcludeColumns = excludeColumns
    }

    /**
     * Set the pretty name mapping
     *
     * @param prettyNameMapping
     */
    fun setPrettyNameMapping(prettyNameMapping: HashMap<String?, String>?) {
        mPrettyNameMapping = prettyNameMapping
    }

    /**
     * Set a the custom formatter for the column value output
     *
     * @param customFormatter
     */
    fun setCustomFormatter(customFormatter: ExportCustomFormatter?) {
        mCustomFormatter = customFormatter
    }

    private val allTables: ArrayList<String>
        private get() {
            val tables = ArrayList<String>()
            val cursor = database!!.rawQuery(
                "select name from sqlite_master where type='table' order by name",
                null
            )
            while (cursor.moveToNext()) {
                tables.add(cursor.getString(0))
            }
            cursor.close()
            return tables
        }

    private fun getColumns(table: String): ArrayList<String> {
        val columns = ArrayList<String>()
        val cursor = database!!.rawQuery("PRAGMA table_info($table)", null)
        while (cursor.moveToNext()) {
            columns.add(cursor.getString(1))
        }
        cursor.close()
        return columns
    }

    @Throws(Exception::class)
    private fun exportTables(tables: List<String>, fileName: String) {
        workbook = HSSFWorkbook()
        for (i in tables.indices) {
            if (tables[i] != "android_metadata") {
                val sheet = workbook!!.createSheet(prettyNameMapping(tables[i]))
                createSheet(tables[i], sheet)
            }
        }
        val file = File(mExportPath, fileName)
        val fos = FileOutputStream(file)
        workbook!!.write(fos)
        fos.flush()
        fos.close()
        workbook!!.close()
        database!!.close()
    }

    fun exportSingleTable(table: String, fileName: String, listener: ExportListener?) {
        val tables: MutableList<String> = ArrayList()
        tables.add(table)
        startExportTables(tables, fileName, listener)
    }

    fun exportSpecificTables(tables: List<String>, fileName: String, listener: ExportListener?) {
        startExportTables(tables, fileName, listener)
    }

    fun exportAllTables(fileName: String, listener: ExportListener?) {
        val tables = allTables
        startExportTables(tables, fileName, listener)
    }

    private fun startExportTables(
        tables: List<String>,
        fileName: String,
        listener: ExportListener?
    ) {
        listener?.onStart()
        Thread {
            try {
                exportTables(tables, fileName)
                if (listener != null) {
                    handler.post { listener.onCompleted(mExportPath + fileName) }
                }
            } catch (e: Exception) {
                if (database != null && database!!.isOpen()) {
                    database!!.close()
                }
                if (listener != null) handler.post { listener.onError(e) }
            }
        }.start()
    }

    private fun createSheet(table: String, sheet: HSSFSheet) {
        val rowA = sheet.createRow(0)
        val columns = getColumns(table)
        var cellIndex = 0
        for (i in columns.indices) {
            val columnName = prettyNameMapping("" + columns[i])
            if (!excludeColumn(columnName)) {
                val cellA = rowA.createCell(cellIndex)
                cellA.setCellValue(HSSFRichTextString(columnName))
                cellIndex++
            }
        }
        insertItemToSheet(table, sheet, columns)
    }

    private fun insertItemToSheet(table: String, sheet: HSSFSheet, columns: ArrayList<String>) {
        val patriarch = sheet.createDrawingPatriarch()
        val cursor = database!!.rawQuery("select * from $table", null)
        cursor.moveToFirst()
        var n = 1
        while (!cursor.isAfterLast) {
            val rowA = sheet.createRow(n)
            var cellIndex = 0
            for (j in columns.indices) {
                val columnName = "" + columns[j]
                if (!excludeColumn(columnName)) {
                    val cellA = rowA.createCell(cellIndex)
                    if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                        val anchor = HSSFClientAnchor(
                            0,
                            0,
                            0,
                            0,
                            cellIndex.toShort(),
                            n,
                            (cellIndex + 1).toShort(),
                            n + 1
                        )
                        anchor.anchorType = ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE
                        patriarch.createPicture(
                            anchor,
                            workbook!!.addPicture(cursor.getBlob(j), HSSFWorkbook.PICTURE_TYPE_JPEG)
                        )
                    } else {
                        var value = cursor.getString(j)
                        if (null != mCustomFormatter) {
                            value = mCustomFormatter!!.process(columnName, value)
                        }
                        cellA.setCellValue(HSSFRichTextString(value))
                    }
                    cellIndex++
                }
            }
            n++
            cursor.moveToNext()
        }
        cursor.close()
    }

    /**
     * Do we exclude the specified column from the export
     *
     * @param column
     * @return boolean
     */
    private fun excludeColumn(column: String?): Boolean {
        val exclude = false
        return if (null != mExcludeColumns) {
            mExcludeColumns!!.contains(column)
        } else exclude
    }

    /**
     * Convert the specified name to a `pretty` name if a mapping exists
     *
     * @param name
     * @return
     */
    private fun prettyNameMapping(name: String): String? {
        var name: String? = name
        if (null != mPrettyNameMapping) {
            if (mPrettyNameMapping!!.containsKey(name)) {
                name = mPrettyNameMapping!![name]
            }
        }
        return name
    }

    interface ExportListener {
        fun onStart()
        fun onCompleted(filePath: String?)
        fun onError(e: Exception?)
    }

    /**
     * Interface class for the custom formatter
     */
    interface ExportCustomFormatter {
        fun process(columnName: String?, value: String?): String?
    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())
    }
}