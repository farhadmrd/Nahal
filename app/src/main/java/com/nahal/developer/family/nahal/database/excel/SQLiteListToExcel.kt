package com.nahal.developer.family.nahal.database.excel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.apache.poi.hssf.usermodel.HSSFClientAnchor
import org.apache.poi.hssf.usermodel.HSSFRichTextString
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.ClientAnchor
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import java.io.File
import java.io.FileOutputStream

class SQLiteListToExcel(context: Context?, private val mExportPath: String) {
    private var workbook: HSSFWorkbook? = null
    private var mExcludeColumns: List<String?>? = null
    private var mPrettyNameMapping: HashMap<String?, String>? = null
    private var mCustomFormatter: ExportCustomFormatter? = null

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

    @Throws(Exception::class)
    private fun exportTables(cursor: Cursor, fileName: String) {
        try {
            workbook = HSSFWorkbook()
            val sheet = workbook!!.createSheet("Sheet1")
            createSheet(cursor, sheet)
            val file = File(mExportPath, fileName)
            val fos = FileOutputStream(file)
            workbook!!.write(fos)
            fos.flush()
            fos.close()
            workbook!!.close()
        } catch (e: Exception) {
        }
    }

    fun exportTable(cursor: Cursor, fileName: String, listener: ExportListener?) {
        try {
            startExportTables(cursor, fileName, listener)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "exportTable: " + e.message)
        }
    }

    private fun startExportTables(cursor: Cursor, fileName: String, listener: ExportListener?) {
        listener?.onStart()
        Thread {
            try {
                exportTables(cursor, fileName)
                if (listener != null) {
                    handler.post { listener.onCompleted(mExportPath + fileName) }
                }
            } catch (e: Exception) {
                if (listener != null) handler.post { listener.onError(e) }
            }
        }.start()
    }

    private fun createSheet(cursor: Cursor, sheet: HSSFSheet) {
        try {
            val rowA = sheet.createRow(0)
            val columns = ArrayList<String>()
            columns.add("ID")
            columns.add("SurveyResponseID")
            columns.add("FullName")
            columns.add("Phone")
            columns.add("Staff")
            columns.add("Question")
            columns.add("AnswerType")
            columns.add("Answer")
            columns.add("FA_CREATE_DATE")
            columns.add("IS_ACTIVE")
            var cellIndex = 0
            for (i in columns.indices) {
                val columnName = prettyNameMapping("" + columns[i])
                if (!excludeColumn(columnName)) {
                    val cellA = rowA.createCell(cellIndex)
                    val cellStyle: CellStyle = cellA.cellStyle
                    cellStyle.alignment = HorizontalAlignment.CENTER
                    cellStyle.verticalAlignment = VerticalAlignment.CENTER
                    cellA.setCellStyle(cellStyle)
                    cellA.setCellValue(HSSFRichTextString(columnName))
                    cellIndex++
                }
            }
            insertItemToSheet(cursor, sheet, columns)
        } catch (e: Exception) {
        }
    }

    private fun insertItemToSheet(cursor: Cursor, sheet: HSSFSheet, columns: ArrayList<String>) {
        try {
            val patriarch = sheet.createDrawingPatriarch()
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
                                0, 0, 0, 0, cellIndex.toShort(),
                                n, (cellIndex + 1).toShort(), n + 1
                            )
                            anchor.anchorType = ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE
                            patriarch.createPicture(
                                anchor, workbook!!.addPicture(
                                    cursor.getBlob(j),
                                    HSSFWorkbook.PICTURE_TYPE_JPEG
                                )
                            )
                        } else {
                            var value = cursor.getString(j)
                            if (null != mCustomFormatter) {
                                value = mCustomFormatter!!.process(columnName, value)
                            }
                            val cellStyle: CellStyle = cellA.cellStyle
                            cellStyle.alignment = HorizontalAlignment.CENTER
                            cellStyle.verticalAlignment = VerticalAlignment.CENTER
                            cellA.setCellStyle(cellStyle)
                            cellA.setCellValue(HSSFRichTextString(value))
                        }
                        cellIndex++
                    }
                }
                n++
                cursor.moveToNext()
            }
            cursor.close()
        } catch (e: Exception) {
        }
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