package com.botai;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 读取Excel文件的方法
 *
 * @author lmb
 * @date 2017-3-15
 */
public class ExcelUtils {

    public static HSSFSheet sheet = null;

    /**
     * 读取Excel2003的主表数据 （单个sheet）
     *
     * @param filePath
     * @return
     */
    public static List<WuLiao> readFromXLS2003(String filePath) {

        List<WuLiao> boWenJiaoDuBlank = new ArrayList<>();
        File excelFile = null;// Excel文件对象  
        InputStream is = null;// 输入流对象  
        String cellStr = null;// 单元格，最终按字符串处理  
        List<WuLiao> employeeList = new ArrayList<>();// 返回封装数据的List
        WuLiao employee = null;// 每一个雇员信息对象
        try {
            excelFile = new File(filePath);
            is = new FileInputStream(excelFile);// 获取文件输入流  
            HSSFWorkbook workbook2003 = new HSSFWorkbook(is);// 创建Excel2003文件对象  
            HSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
            String goodsNo = "";
            // 开始循环遍历行，表头不处理，从1开始  
            exitRow:
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);// 获取行对象
                employee = new WuLiao();// 实例化Student对象
                if (row == null) {// 如果为空，不处理  
                    continue;
                }
                // 循环遍历单元格  
                for (int j = 0; j <= row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);// 获取单元格对象  
                    if (cell == null) {// 单元格为空设置cellStr为空串  
                        cellStr = "";
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理  
                        cellStr = String.valueOf(cell.getBooleanCellValue());
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理  
                        cellStr = cell.getNumericCellValue() + "";
                    } else {// 其余按照字符串处理  
                        cellStr = cell.getStringCellValue();
                    }
                    // 下面按照数据出现位置封装到bean中  
                    if (j == 0) {

                        if (StringUtils.equals(cellStr.trim(), "end")) {
                            break exitRow;
                        }

                        goodsNo = cellStr.trim().split("-")[0];
                        employee.setXuHao(cellStr.trim());
                        employee.setGoodsPre("BP" + setGoodsNo(goodsNo));
                    } else if (j == 1) {
                        employee.setMainNo(cellStr.trim());
                    } else if (j == 2) {
                        employee.setHouDu(cellStr.trim());
                    } else if (j == 3) {

                        if (StringUtils.isBlank(cellStr.trim())) {

                        }
                        employee.setBoWenJiaoDu(cellStr.trim());
                    } else if (j == 4) {
                        employee.setLength(StringUtils.isNotBlank(cellStr.trim()) ? new BigDecimal(cellStr.trim()).intValue() : 0);
                    } else if (j == 5) {
                        employee.setWith(StringUtils.isNotBlank(cellStr.trim()) ? new BigDecimal(cellStr.trim()).intValue() : 0);
                    }
                }
                employeeList.add(employee);// 数据装入List  
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流  
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return employeeList;
    }

    /**
     * 设置goodsNo
     *
     * @param goodsNo
     * @return
     */
    private static String setGoodsNo(String goodsNo) {
        int integer = Integer.valueOf(goodsNo);

        if (integer < 10) {
            return "00" + goodsNo;
        } else if (integer < 100) {
            return "0" + goodsNo;
        } else if (integer < 999) {
            return goodsNo;
        } else {
            System.out.println("扩展编码位数");
            return null;
        }
    }

    /**
     * 读取Excel2003的表头
     *
     * @param filePath 需要读取的文件路径
     * @return
     */
    public static String[] readHeaderFromXLS2003(String filePath) {
        String[] excelTitle = null;
        FileInputStream is = null;
        try {
            File excelFile = new File(filePath);
            is = new FileInputStream(excelFile);
            HSSFWorkbook workbook2003 = new HSSFWorkbook(is);
            //循环读取工作表
            for (int i = 0; i < workbook2003.getNumberOfSheets(); i++) {
                HSSFSheet hssfSheet = workbook2003.getSheetAt(i);
                //*************获取表头是start*************
                HSSFRow sheetRow = hssfSheet.getRow(i);
                excelTitle = new String[sheetRow.getLastCellNum()];
                for (int k = 0; k < sheetRow.getLastCellNum(); k++) {
                    HSSFCell hssfCell = sheetRow.getCell(k);
                    excelTitle[k] = hssfCell.getStringCellValue();
//		            	System.out.println(excelTitle[k] + " ");
                }
                //*************获取表头end*************
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return excelTitle;
    }

    /**
     * 读取Excel2007的示例方法 （单个sheet）
     *
     * @param sheetFrom
     * @param sheetTo
     * @return
     */
    public static HSSFSheet copySheet(HSSFSheet sheetFrom, HSSFSheet sheetTo) {
        CellRangeAddress region = null;
        Row rowFrom = null;
        Row rowTo = null;
        Cell cellFrom = null;
        Cell cellTo = null;
        for (int i = 0; i < sheetFrom.getNumMergedRegions(); i++) {
            region = sheetFrom.getMergedRegion(i);
            if ((region.getFirstColumn() >= sheetFrom.getFirstRowNum())
                    && (region.getLastRow() <= sheetFrom.getLastRowNum())) {
                sheetTo.addMergedRegion(region);
            }
        }
        for (int intRow = sheetFrom.getFirstRowNum(); intRow < sheetFrom.getLastRowNum(); intRow++) {
            rowFrom = sheetFrom.getRow(intRow);
            rowTo = sheetTo.createRow(intRow);
            if (null == rowFrom) {
                continue;
            }
            rowTo.setHeight(rowFrom.getHeight());
            for (int intCol = 0; intCol < rowFrom.getLastCellNum(); intCol++) {
                sheetTo.setDefaultColumnStyle(intCol, sheetFrom.getColumnStyle(intCol));
                sheetTo.setColumnWidth(intCol, sheetFrom.getColumnWidth(intCol));
                cellFrom = rowFrom.getCell(intCol);
                cellTo = rowTo.createCell(intCol);
                if (null == cellFrom) {
                    continue;
                }
                cellTo.setCellStyle(cellFrom.getCellStyle());
                cellTo.setCellType(cellFrom.getCellType());
                if (null != cellFrom.getStringCellValue() && !"".equals(cellFrom.getStringCellValue().trim())) {
                    cellTo.setCellValue(cellFrom.getStringCellValue());
                }
            }
        }
        sheetTo.setDisplayGridlines(true);//是否显示网格线
        sheetTo.setZoom(50, 100);//设置表格缩放比例
        return sheetTo;

    }


    /**
     * 模版sheet
     *
     * @param fileInputStream
     * @return
     */
    public static Sheet getTemplateSheet(FileInputStream fileInputStream) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
            //获取到工作表，因为一个excel可能有多个工作表
            sheet = wb.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }


    public static List<MyRow> getMyrows(WuLiao wuLiao) throws InvocationTargetException, IllegalAccessException {
        String boWenJiaoDu = "";
        String boWenSon = "";
        String[] boWenJiaoDuSplit = null;
        String mainNo = wuLiao.getMainNo();
        String houDu = wuLiao.getHouDu();
        List<MyRow> myRows = new ArrayList<>();

        //厚度分割获取厚度范围
        String[] split_houDu = null;
        if (houDu.contains("-")) {
            split_houDu = houDu.split("-");
        } else {
            split_houDu = houDu.split("/");
        }
        int start = new BigDecimal(split_houDu[0]).multiply(BigDecimal.valueOf(10)).intValue();
        int end = new BigDecimal(split_houDu[split_houDu.length > 1 ? 1 : 0]).multiply(BigDecimal.valueOf(10)).intValue();


        boWenJiaoDu = wuLiao.getBoWenJiaoDu();
        // 波纹为空
        if (StringUtils.isBlank(boWenJiaoDu)) {
            boWenBlank(myRows, wuLiao, boWenJiaoDu, start, end);
            return myRows;
        }
        String[] s = boWenJiaoDu.split(" ");
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            String[] split = s[i].split("/");

            for (int j = 0; j < split.length; j++) {
                temp.add(split[j].trim());
            }
        }

        boWenJiaoDuSplit = temp.stream().filter(item -> StringUtils.isNotBlank(item)).toArray(String[]::new);


        String xinghaoALl = wuLiao.setXingHao(mainNo, boWenJiaoDuSplit, boWenJiaoDu);
        //,
        int length = boWenJiaoDuSplit.length;
        String[] values = null;
        String xingHao = "";
        if (length > 1) {
            values = xinghaoALl.split("@");
        } else {
            xingHao = xinghaoALl;
        }
        //波纹角度
        for (int j = 0; j < length; j++) {


            boWenSon = boWenJiaoDuSplit[j];
            doWuliaoQufenAndCaizhi(length, xingHao, values, j, xinghaoALl, myRows, mainNo, boWenSon, wuLiao,
                    start, end);
            ///物料区分
            /*for (int k = 0; k < Constans.xingHaoHouZuiList.size(); k++) {
                // 循环材质
                for (int m = 0; m < Constans.caiZhiList.size(); m++) {
                    if (length > 1) {
                        xingHao = values[j];
                    } else {
                        xingHao = xinghaoALl;
                    }
                    if (StringUtils.isNotBlank(xingHao)) {
                        xingHao += Constans.xingHaoHouZuiList.get(k);
                    }
                    myRows.add(new MyRow(mainNo,
                            boWenSon,
                            wuLiao,
                            WuLiaoQuFen.getByWuLiaoQuFenCode(Constans.xingHaoHouZuiList.get(k)),
                            Constans.caiZhiList.get(m), xingHao, start, end));
                }
            }*/
        }
        return myRows;
    }

    //物料循环 和材质
    public static void doWuliaoQufenAndCaizhi(int length, String xingHao, String[] values, int j, String xinghaoALl, List<MyRow> myRows, String mainNo, String boWenSon, WuLiao wuLiao,
                                              int start, int end) {
        ///物料区分
        for (int k = 0; k < Constans.xingHaoHouZuiList.size(); k++) {
            // 循环材质
            for (int m = 0; m < Constans.caiZhiList.size(); m++) {
                if (length > 1) {
                    xingHao = values[j];
                } else {
                    xingHao = xinghaoALl;
                }
                if (StringUtils.isNotBlank(xingHao)) {
                    xingHao += Constans.xingHaoHouZuiList.get(k);
                }
                myRows.add(new MyRow(mainNo,
                        boWenSon,
                        wuLiao,
                        WuLiaoQuFen.getByWuLiaoQuFenCode(Constans.xingHaoHouZuiList.get(k)),
                        Constans.caiZhiList.get(m), xingHao, start, end));
            }
        }
    }

    // 波纹角度为空
    public static void boWenBlank(List<MyRow> myRows, WuLiao wuLiao, String boWenJiaoDu, int start, int end) {
        if (StringUtils.isNotBlank(boWenJiaoDu)) {
            return;
        }
        doWuliaoQufenAndCaizhi(0, wuLiao.getMainNo(), null, 0, wuLiao.getMainNo(), myRows, wuLiao.getMainNo(), "", wuLiao, start, end);
    }
}