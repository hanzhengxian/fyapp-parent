package com.onway.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel<T> {
     /** 
     * @author liaoshengzhe
     * 
     * @param title 
     *            �������� 
     * @param headers 
     *            ��������������� ����һ�б��⣩
     * @param Col 
     *            ��Ҫ��ʾ�ı�������������� �����javabean ������ֶ�����һֱ ���ΪMap ����ΪMap��key���ֶ�Ӧ
     * @param dataset 
     *            ��Ҫ��ʾ�����ݼ���,���Ϸ���֧�����֣�1������javabean������Ķ��� 2��Map���͡��˷���֧�ֵ� 
     *            javabean���Ե����������л����������ͼ�String,Date,byte[](ͼƬ����) 
     * @param pattern 
     *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyy-MM-dd HH:mm:ss" 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HSSFWorkbook exportExcel(String title, String[] headers,String[] Col,Collection<T> dataset, String pattern) {
        if(pattern == null || pattern.equals("")) pattern = "yyy-MM-dd HH:mm:ss";
        // ����һ��������
        HSSFWorkbook workbook = new HSSFWorkbook();
        // ����һ�����
        HSSFSheet sheet = workbook.createSheet(title);
        // ���ñ��Ĭ���п��Ϊ15���ֽ�
        sheet.setDefaultColumnWidth(15);
        // ����һ����ʽ
        HSSFCellStyle style = workbook.createCellStyle();
        // ������Щ��ʽ
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // ����һ������
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // ������Ӧ�õ���ǰ����ʽ
        style.setFont(font);
        // ���ɲ�������һ����ʽ
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // ������һ������
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // ������Ӧ�õ���ǰ����ʽ
        style2.setFont(font2);
        // ����һ����ͼ�Ķ���������
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // ������������
        HSSFRow row = sheet.createRow(0);
        int Cell = 0;
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(Cell);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            Cell ++ ;
        }
        // �����������ݣ�����������
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            String[] fields = Col;
            Cell = 0;
            for (short i = 0; i < fields.length; i++) {
                String fieldName = fields[i];
                HSSFCell cell = row.createCell(Cell);
                cell.setCellStyle(style2);
                try {
                    Object value = "";
                    Class tCls = null;
                    Map map = null;
                    if(t instanceof Map){
                        map = (Map)t;
                        value = map.get(fieldName);
                    } else {
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
                        tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
                        value = getMethod.invoke(t, new Object[] {});
                    }
                    if(value == null ) value = "";
                    // �ж�ֵ�����ͺ����ǿ������ת��
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // ��ͼƬʱ�������и�Ϊ60px;
                        row.setHeightInPoints(60);
                        // ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������
                        sheet.setColumnWidth(Cell, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // �����������Ͷ������ַ����򵥴���
                        textValue = value.toString();
                    }
                    // �������ͼƬ���ݣ�������������ʽ�ж�textValue�Ƿ�ȫ�����������
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // �����ֵ���double����
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    Cell ++ ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }
}