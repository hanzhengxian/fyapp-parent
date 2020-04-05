package com.onway.utils.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel
 * 
 * @author yuhang.ni
 * @version $Id: ExportExcel.java, v 0.1 2019年7月23日 下午1:52:19 Administrator Exp $
 */
public class ExportExcel {

    /**
     * 导出数据整理
     * 
     * @param sheetName    excel文件名
     * @param header       表头 
     * @param col          列
     * @param list         列表
     * @param book
     * @return
     */
    public static HSSFWorkbook exportExcel(String sheetName, String[] header, String[] col,
                                           List<Map<String, Object>> list, HSSFWorkbook book) {
        //创建HSSFWorkbook
        if (book == null) {
            book = new HSSFWorkbook();
        }
        //第二步 在book中添加一个excel
        HSSFSheet sheet = book.createSheet(sheetName);
        //第三步添加表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i < header.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header[i]);
        }
        //添加内容
        int x = 1;
        for (Map<String, Object> map : list) {
            row = sheet.createRow(x);
            for (int j = 0; j < col.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(null == map.get(col[j]) ? "" : String.valueOf(map.get(col[j])));
            }
            x++;
        }
        return book;
    }

    public static HSSFWorkbook exportExcelS(String sheetName, String[] header, String[] col,
                                            List<Map<String, Object>> list, HSSFWorkbook book) {
        //创建HSSFWorkbook
        if (book == null) {
            book = new HSSFWorkbook();
        }
        //第二步 在book中添加一个excel
        HSSFSheet sheet = book.createSheet(sheetName);
        
        HSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
        
        //第三步添加固定表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i < header.length; i++) {
            cell = row.createCell(i);
            if (i == 6) {
                cell.setCellValue("寄件方信息");
            } else if (i == 10) {
                cell.setCellValue("收件方信息");
            } else if (i == 14) {
                cell.setCellValue("商品信息");
            } else if (i == 20) {
                cell.setCellValue("发货信息");
            }
            cell.setCellStyle(cellStyle);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 13));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 19));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 20, 54));

        //第四步添加表头
        row = sheet.createRow(1);
        for (int i = 0; i < header.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }

        //添加内容
        int x = 2;
        for (Map<String, Object> map : list) {
            row = sheet.createRow(x);
            for (int j = 0; j < col.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(null == map.get(col[j]) ? "" : String.valueOf(map.get(col[j])));
                cell.setCellStyle(cellStyle);
            }
            x++;
        }

        //        sheet.addMergedRegion(new CellRangeAddress(0, 10, 0, 13));
        //        sheet.addMergedRegion(new CellRangeAddress(0, 14, 0, 19));
        //        sheet.addMergedRegion(new CellRangeAddress(0, 20, 0, 54));

        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return book;
    }

}
