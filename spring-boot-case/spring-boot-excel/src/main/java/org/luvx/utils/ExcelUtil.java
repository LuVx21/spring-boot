package org.luvx.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Util提供的所有静态方法返回的对象都是Workbook，最后一步根据需求再做处理
 * @author learnhow
 *
 */
public class ExcelUtil {
    public static final int Excel2003 = 0;
    public static final int Excel2007 = 1;

    /**
     * 根据版本号，获取Excel poi对象
     *
     * @param edition
     * @param in
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(int edition, InputStream in) throws IOException {
        if (edition == 0) {
            return new HSSFWorkbook(in);
        } else if (edition == 1) {
            return new XSSFWorkbook(in);
        }
        return null;
    }

    /**
     * 从指定excel表格中逐行读取数据
     *
     * @param workbook
     * @param startRow
     * @param startCol
     * @param indexSheet
     * @return
     */
    public static List<List<String>> getExcelString(Workbook workbook, int startRow, int startCol, int indexSheet) {
        List<List<String>> stringTable = new ArrayList<List<String>>();
        // 获取指定表对象
        Sheet sheet = workbook.getSheetAt(indexSheet);
        // 获取最大行数
        int rowNum = sheet.getLastRowNum();
        for (int i = startRow; i <= rowNum; i++) {
            List<String> oneRow = new ArrayList<String>();
            Row row = sheet.getRow(i);
            // 根据当前指针所在行数计算最大列数
            int colNum = row.getLastCellNum();
            for (int j = startCol; j <= colNum; j++) {
                // 确定当前单元格
                Cell cell = row.getCell(j);
                String cellValue = null;
                if (cell != null) {
                    // 验证每一个单元格的类型
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            // 表格中返回的数字类型是科学计数法因此不能直接转换成字符串格式
                            cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            cellValue = Boolean.toString(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            cellValue = "ERROR";
                            break;
                        default:
                            cellValue = "UNDEFINE";
                    }
                } else {
                    cellValue = "";
                }
                // 生成一行数据
                oneRow.add(cellValue);
            }
            stringTable.add(oneRow);
        }
        return stringTable;
    }

    /**
     * 根据给定的数据直接生成workbook
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @return
     */
    public static Workbook createExcel(Workbook workbook, String sheetName, List<List<String>> data) {
        Sheet sheet = workbook.createSheet(sheetName);
        for (int i = 0; i < data.size(); i++) {
            List<String> oneRow = data.get(i);
            Row row = sheet.createRow(i);
            for (int j = 0; j < oneRow.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(oneRow.get(j));
            }
        }
        return workbook;
    }

    /**
     * 往指定的sheet表中插入数据，插入的方法是提供一组valueMap。int[]是2维数组代表需要插入的数据坐标，从0开始
     *
     * @param workbook
     * @param sheetIndex
     * @param valueMap
     * @return
     */
    public static Workbook insertExcel(Workbook workbook, int sheetIndex, Map<int[], String> valueMap) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Iterator<Map.Entry<int[], String>> it = valueMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<int[], String> cellEntry = it.next();
            int x = cellEntry.getKey()[0];
            int y = cellEntry.getKey()[1];
            String value = cellEntry.getValue();
            Row row = sheet.getRow(y);
            Cell cell = row.getCell(x);
            cell.setCellValue(value);
        }
        return workbook;
    }

    /**
     * 设置指定行的行高
     *
     * @param workbook
     * @param rowHight
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static Workbook setRowHeight(Workbook workbook, int rowHight, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowIndex);
        row.setHeight((short) rowHight);
        return workbook;
    }

    /**
     * 设置列宽
     *
     * @param workbook
     * @param columnWidth
     * @param sheetIndex
     * @param columnIndex
     * @return
     */
    public static Workbook setColumnWidth(Workbook workbook, int columnWidth, int sheetIndex, int columnIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        sheet.setColumnWidth(columnIndex, columnWidth);
        return workbook;
    }

    /**
     * 删除指定行
     *
     * @param workbook
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static Workbook removeRow(Workbook workbook, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            sheet.removeRow(sheet.getRow(rowIndex));
        }
        return workbook;
    }

    /**
     * 在指定位置插入空白行
     *
     * @param workbook
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static Workbook insertBlankRow(Workbook workbook, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex <= lastRowNum) {
            sheet.shiftRows(rowIndex, lastRowNum, 1);
            // 获得上一行的Row对象
            Row preRow = sheet.getRow(rowIndex - 1);
            short rowNum = preRow.getLastCellNum();
            Row curRow = sheet.createRow(rowIndex);
            // 新生成的Row创建与上一个行相同风格的Cell
            for (short i = preRow.getFirstCellNum(); i < rowNum; i++) {
                Cell cell = preRow.getCell(i);
                CellStyle style = cell.getCellStyle();
                curRow.createCell(i).setCellStyle(style);
            }
            return workbook;
        }
        return null;
    }

    /**
     * 根据sheet(0)作为模板重建workbook
     *
     * @param workbook
     * @param sheetNum
     * @param sheetNames
     * @return
     */
    public static Workbook rebuildWorkbook(Workbook workbook, int sheetNum, String... sheetNames) {
        if(sheetNames.length == sheetNum){
            for (int i = 0; i < sheetNum; i++) {
                workbook.cloneSheet(0);
                // 生成后面的工作表并指定表名
                workbook.setSheetName(i + 1, sheetNames[i]);
            }
            // 删除第一张工作表
            workbook.removeSheetAt(0);
            return workbook;
        }
        return null;
    }
}