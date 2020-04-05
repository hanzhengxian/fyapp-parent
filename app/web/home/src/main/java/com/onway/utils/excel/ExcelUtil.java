package com.onway.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.web.multipart.MultipartFile;

import com.onway.common.lang.StringUtils;

public class ExcelUtil {

    /** 总行数 */
    private int    totalRows  = 0;
    /** 总列数 */
    private int    totalCells = 0;
    /** 错误信息 */
    private String errorInfo;

    /**
     * @描述：得到总行数
     * @时间：2014-08-29 下午16:27:15
     * @参数：@return
     * @返回值：int
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @描述：得到总列数
     * @时间：2014-08-29 下午16:27:15
     * @参数：@return
     * @返回值：int
     */
    public int getTotalCells() {
        return totalCells;
    }

    /**
     * @描述：得到错误信息
     * @时间：2014-08-29 下午16:27:15
     * @参数：@return
     * @返回值：String
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * @描述：验证excel文件
     * @时间：2014-08-29 下午16:27:15
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public boolean validateExcel(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;

    }

    /**
     * 
     * @描述：根据文件名读取excel文件
     * @时间：2014-08-29 下午16:27:15
     * @参数：@param filePath 文件完整路径
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(String filePath) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);
                return null;
            }
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * 
     * @描述：根据文件名读取excel文件
     * @时间：2014-08-29 下午16:27:15
     * @参数：@param file 文件
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(File file) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(file.getName())) {
                isExcel2003 = false;
            }
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return dataLst;
    }

    public List<List<String>> read(MultipartFile file) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(file.getName())) {
                isExcel2003 = false;
            }
            is = file.getInputStream();
            dataLst = read(is, isExcel2003);
            is.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * @描述：根据流读取Excel文件
     * @时间：2014-08-29 下午16:40:15
     * @参数：@param inputStream
     * @参数：@param isExcel2003
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            
            dataLst = read(wb, isExcel2003);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLst;

    }

    /**
     * @throws IOException 
     * @描述：读取数据
     * @时间：2014-08-29 下午16:50:15
     * @参数：@param Workbook
     * @参数：@return
     * @返回值：List<List<String>>
     */
    private List<List<String>> read(Workbook wb, boolean isExcel2003) throws IOException {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        this.totalRows = sheet.getPhysicalNumberOfRows();
        /** 得到Excel的列数 */
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        //获取excel sheet总数  
//        int sheetNumbers = wb.getNumberOfSheets();
//        List<Map<String, PictureData>> sheetList = new ArrayList<Map<String, PictureData>>();
//        for (int i = 0; i < sheetNumbers; i++) { 
//            sheet = wb.getSheetAt(i);  
//            // map等待存储excel图片  
//            Map<String, PictureData> sheetIndexPicMap;
//            // 判断用07还是03的方法获取图片  
//            if (isExcel2003) {  
//                sheetIndexPicMap = getSheetPictrues03(i, (HSSFSheet) sheet, (HSSFWorkbook) wb);  
//            } else {  
//                sheetIndexPicMap = getSheetPictrues07(i, (XSSFSheet) sheet, (XSSFWorkbook) wb);  
//            }  
//            // 将当前sheet图片map存入list  
//            sheetList.add(sheetIndexPicMap);  
//        }  
        

//        Map<String, PictureData> maplist = null;
//        if (isExcel2003) {
//            maplist = getPictures1((HSSFSheet) sheet);
//        } else {
//            maplist = getPictures2((XSSFSheet) sheet);
//        }

//        printImg(maplist);

        /** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            DecimalFormat df = new DecimalFormat("0");
                            cellValue = df.format(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;
                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;
                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }

                if (StringUtils.isBlank(cellValue)) {

                }

                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     * 获取图片和位置 (xls)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures1(HSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                map.put(key, pdata);
            }
        }
        return map;
    }

    /**
     * 获取图片和位置 (xlsx)
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures2(XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    //图片写出
    public static void printImg(Map<String, PictureData> sheetList) throws IOException {
        Object key[] = sheetList.keySet().toArray();
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流  
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引  
            String picName = key[i].toString();
            // 获取图片格式  
            String ext = pic.suggestFileExtension();

            byte[] data = pic.getData();

            //图片保存路径 
            FileOutputStream out = new FileOutputStream("D:\\img\\pic" + picName + "." + ext);
            out.write(data);
            out.close();
        }
    }
    
    
    /** 
     * 获取Excel2003图片 
     * @param sheetNum 当前sheet编号 
     * @param sheet 当前sheet对象 
     * @param workbook 工作簿对象 
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData 
     * @throws IOException 
     */  
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum,  
            HSSFSheet sheet, HSSFWorkbook workbook) {  
  
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();  
        List<HSSFPictureData> pictures = workbook.getAllPictures();  
        if (pictures.size() != 0) {  
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {  
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
                if (shape instanceof HSSFPicture) {  
                    HSSFPicture pic = (HSSFPicture) shape;  
                    int pictureIndex = pic.getPictureIndex() - 1;  
                    HSSFPictureData picData = pictures.get(pictureIndex);  
                    String picIndex = String.valueOf(sheetNum) + "_"  
                            + String.valueOf(anchor.getRow1()) + "_"  
                            + String.valueOf(anchor.getCol1());  
                    sheetIndexPicMap.put(picIndex, picData);  
                }  
            }  
            return sheetIndexPicMap;  
        } else {  
            return null;  
        }  
    }  
  
    /** 
     * 获取Excel2007图片 
     * @param sheetNum 当前sheet编号 
     * @param sheet 当前sheet对象 
     * @param workbook 工作簿对象 
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData 
     */  
    public static Map<String, PictureData> getSheetPictrues07(int sheetNum,  
            XSSFSheet sheet, XSSFWorkbook workbook) {  
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();  
  
        for (POIXMLDocumentPart dr : sheet.getRelations()) {  
            if (dr instanceof XSSFDrawing) {  
                XSSFDrawing drawing = (XSSFDrawing) dr;  
                List<XSSFShape> shapes = drawing.getShapes();  
                for (XSSFShape shape : shapes) {  
                    XSSFPicture pic = (XSSFPicture) shape;  
                    XSSFClientAnchor anchor = pic.getPreferredSize();  
                    CTMarker ctMarker = anchor.getFrom();  
                    String picIndex = String.valueOf(sheetNum) + "_"  
                            + ctMarker.getRow() + "_" + ctMarker.getCol();  
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());  
                }  
            }  
        }  
  
        return sheetIndexPicMap;  
    }  
      
    public static void printImg(List<Map<String, PictureData>> sheetList) throws IOException {  
          
        for (Map<String, PictureData> map : sheetList) {  
            Object key[] = map.keySet().toArray();  
            for (int i = 0; i < map.size(); i++) {  
                // 获取图片流  
                PictureData pic = map.get(key[i]);  
                // 获取图片索引  
                String picName = key[i].toString();  
                // 获取图片格式  
                String ext = pic.suggestFileExtension();  
                  
                byte[] data = pic.getData();  
                  
                FileOutputStream out = new FileOutputStream("D:\\pic" + picName + "." + ext);  
                out.write(data);  
                out.close();  
            }  
        }  
          
    }  
    

    /**
     * @描述：main测试方法
     * @时间：2014-08-29 下午17:12:15
     * @参数：@param args
     * @参数：@throws Exception
     * @返回值：void
     */
    public static void main(String[] args) throws Exception {
        ExcelUtil poi = new ExcelUtil();
        List<List<String>> list = poi.read("C:/Users/Administrator/Desktop/商品属性模板.xls");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print("第" + (i) + "行");
                List<String> cellList = list.get(i);
                for (int j = 0; j < cellList.size(); j++) {
                    System.out.print("    " + cellList.get(j));
                }
                System.out.println();
            }
        }
    }

}
