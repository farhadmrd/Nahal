package com.nahal.developer.family.nahal.database.excel;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteListToExcel {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private String mExportPath;
    private HSSFWorkbook workbook;

    private List<String> mExcludeColumns = null;
    private HashMap<String, String> mPrettyNameMapping = null;
    private ExportCustomFormatter mCustomFormatter = null;


    public SQLiteListToExcel(Context context, String exportPath) {
        mExportPath = exportPath;

    }

    /**
     * Set the exclude columns list
     *
     * @param excludeColumns
     */
    public void setExcludeColumns(List<String> excludeColumns) {
        mExcludeColumns = excludeColumns;
    }

    /**
     * Set the pretty name mapping
     *
     * @param prettyNameMapping
     */
    public void setPrettyNameMapping(HashMap<String, String> prettyNameMapping) {
        mPrettyNameMapping = prettyNameMapping;
    }

    /**
     * Set a the custom formatter for the column value output
     *
     * @param customFormatter
     */
    public void setCustomFormatter(ExportCustomFormatter customFormatter) {
        mCustomFormatter = customFormatter;
    }

    private void exportTables(Cursor cursor, final String fileName) throws Exception {
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            createSheet(cursor, sheet);


            File file = new File(mExportPath, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();
            fos.close();
            workbook.close();
        }catch (Exception e) {

        }

    }

    public void exportTable(final Cursor cursor, final String fileName, ExportListener listener) {
        try {
            startExportTables(cursor, fileName, listener);
        } catch (Exception e) {
            Log.e(TAG, "exportTable: " + e.getMessage());
        }

    }

    private void startExportTables(final Cursor cursor, final String fileName, final ExportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    exportTables(cursor, fileName);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mExportPath + fileName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (listener != null)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                }
            }
        }).start();
    }

    private void createSheet(Cursor cursor, HSSFSheet sheet) {
        try {
            HSSFRow rowA = sheet.createRow(0);
            ArrayList<String> columns = new ArrayList<>();

            columns.add("ID");
            columns.add("SurveyResponseID");
            columns.add("FullName");
            columns.add("Phone");
            columns.add("Staff");
            columns.add("Question");
            columns.add("AnswerType");
            columns.add("Answer");
            columns.add("FA_CREATE_DATE");
            columns.add("IS_ACTIVE");

            int cellIndex = 0;
            for (int i = 0; i < columns.size(); i++) {
                String columnName = prettyNameMapping("" + columns.get(i));
                if (!excludeColumn(columnName)) {
                    HSSFCell cellA = rowA.createCell(cellIndex);
                    CellStyle cellStyle=cellA.getCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    cellA.setCellStyle(cellStyle);
                    cellA.setCellValue(new HSSFRichTextString(columnName));
                    cellIndex++;
                }
            }
            insertItemToSheet(cursor, sheet, columns);
        }catch (Exception e) {

        }

    }

    private void insertItemToSheet(Cursor cursor, HSSFSheet sheet, ArrayList<String> columns) {
        try {
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            cursor.moveToFirst();
            int n = 1;
            while (!cursor.isAfterLast()) {
                HSSFRow rowA = sheet.createRow(n);
                int cellIndex = 0;
                for (int j = 0; j < columns.size(); j++) {
                    String columnName = "" + columns.get(j);
                    if (!excludeColumn(columnName)) {
                        HSSFCell cellA = rowA.createCell(cellIndex);
                        if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cellIndex,
                                    n, (short) (cellIndex + 1), n + 1);
                            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                            patriarch.createPicture(anchor, workbook.addPicture(cursor.getBlob(j),
                                    HSSFWorkbook.PICTURE_TYPE_JPEG));
                        } else {
                            String value = cursor.getString(j);
                            if (null != mCustomFormatter) {
                                value = mCustomFormatter.process(columnName, value);
                            }
                            CellStyle cellStyle=cellA.getCellStyle();
                            cellStyle.setAlignment(HorizontalAlignment.CENTER);
                            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                            cellA.setCellStyle(cellStyle);
                            cellA.setCellValue(new HSSFRichTextString(value));
                        }
                        cellIndex++;
                    }
                }
                n++;
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e) {

        }

    }

    /**
     * Do we exclude the specified column from the export
     *
     * @param column
     * @return boolean
     */
    private boolean excludeColumn(String column) {
        boolean exclude = false;
        if (null != mExcludeColumns) {
            return mExcludeColumns.contains(column);
        }

        return exclude;
    }

    /**
     * Convert the specified name to a `pretty` name if a mapping exists
     *
     * @param name
     * @return
     */
    private String prettyNameMapping(String name) {
        if (null != mPrettyNameMapping) {
            if (mPrettyNameMapping.containsKey(name)) {
                name = mPrettyNameMapping.get(name);
            }
        }
        return name;
    }

    public interface ExportListener {
        void onStart();

        void onCompleted(String filePath);

        void onError(Exception e);
    }

    /**
     * Interface class for the custom formatter
     */
    public interface ExportCustomFormatter {
        String process(String columnName, String value);
    }
}