package com.botai;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @program: ExcelDoing
 * @description: dd
 * @author: 杨韬
 * @create: 2020-06-26 13:27
 **/
public class MyRowTask implements Callable<List<MyRow>> {


    private List<MyRow> myRows;
    private String fileName;

    public MyRowTask(List<MyRow> myRows, String fileName) {
        this.myRows = myRows;
        this.fileName = fileName;
    }

    @Override
    public List<MyRow> call() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(fileName)));
        //获取到工作表，因为一个excel可能有多个工作表
        HSSFSheet sheet = wb.getSheetAt(0);
//        ExcelUtils.copySheet(ExcelUtils.sheet, sheet);
        sheet.setZoom(100, 100);
        MyRow myRow = null;
        int index = 0;
        double v = 0.0;
        int caizhiIndex = 0;
        MyRow myRow1 = myRows.get(0);
        int i = myRow1.getStart();
        int end = myRow1.getEnd();
        for (; i <= end; i++) {
            v = i / 1.0;
            for (int j = 0; j < myRows.size(); j++) {
                myRow = myRows.get(j);
                setRow(sheet, index, myRow.getMainNo(), myRow.getBoWenSon(), myRow.getWuLiao(), v, myRow.getWuLiaoQuFen(), myRow.getCaiZhi(), myRow.getXingHao());
                index++;
                if (index % 10 == 0) {
                    caizhiIndex++;
                    if (caizhiIndex > 4) {
                        caizhiIndex = 0;
                    }
                }
            }
        }
        setLastRow(sheet, index++);
        FileOutputStream out = null;
        try {
            //写数据
            out = new FileOutputStream(fileName);
            wb.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return myRows;
    }

    // 最后一行加 end
    public synchronized void setLastRow(HSSFSheet sheet, int rowCoun) {
        HSSFRow row = sheet.createRow(rowCoun + 2);
        //物料编码
        row.createCell(0).setCellValue("end");
    }

    public synchronized void setRow(HSSFSheet sheet, int rowCoun, String mainNo, String bowen, WuLiao wuLiao, double houDu, String wuLiaoQuFen, String caiZhi, String xingHao) {
        //在现有行号后追加数据，我的excel模板中只保留了标题，所以从第一行开始追加数据，如果不是第一行，可以先获取sheet表的最后一行，sheet.getLastRowNum（），获取之后，再追加
        HSSFRow row = sheet.createRow(rowCoun + 2);
        //给需要添加数据的列赋值；这里因为我知道列数，所以直接使用了列号
        int index = 0;
        /**
         * 设置第一个（从0开始）单元格的数据
         */
        //物料编码
        row.createCell(0).setCellValue(wuLiao.getGoodsPre() + setGoodsNoSub(rowCoun+1));

        //物料名称
        row.createCell(1).setCellValue(Constans.lieMapFinallData.get(1).toString());

        // 物料类别编码
        row.createCell(2).setCellValue(Constans.lieMapFinallData.get(2).toString());

        // 物料类别名称
        row.createCell(3).setCellValue(Constans.lieMapFinallData.get(3).toString());

        // 单位
        row.createCell(4).setCellValue(Constans.lieMapFinallData.get(4).toString());

        //物料属性
        row.createCell(5).setCellValue(Constans.lieMapFinallData.get(5).toString());

        //物料区分
        row.createCell(6).setCellValue(wuLiaoQuFen);

        // 型号
        row.createCell(7).setCellValue(xingHao);

        //主型号
        row.createCell(8).setCellValue(mainNo);

        try {
            //波纹角度
            row.createCell(9).setCellValue(getBoWeenJiaoNum(bowen).toString());
        } catch (Exception e) {
            System.err.println("mainNo: " + mainNo + " bowen: " + bowen);
        }

        //规格尺度
        row.createCell(10).setCellValue("");

        //材质
        row.createCell(11).setCellValue(caiZhi);

        // 厚度
        row.createCell(12).setCellValue(String.valueOf(houDu / 10));

        // 长度
        row.createCell(13).setCellValue(String.valueOf(wuLiao.getLength()));

        // 宽度
        row.createCell(14).setCellValue(String.valueOf(wuLiao.getWith()));

        //密度
        row.createCell(15).setCellValue("");
        //单间重量
        row.createCell(16).setCellValue(Constans.lieMapFinallData.get(16).toString());

        // 最低库存
        row.createCell(17).setCellValue(Constans.lieMapFinallData.get(17).toString());

        //最高库存
        row.createCell(18).setCellValue(Constans.lieMapFinallData.get(18).toString());
    }

    /**
     * 设置物料编号后缀
     *
     * @param rowCoun
     * @return
     */
    private String setGoodsNoSub(int rowCoun) {
        if (rowCoun < 10) {
            return "000" + rowCoun;
        } else if (rowCoun < 100) {
            return "00" + rowCoun;

        } else if (rowCoun < 1000) {
            return "0" + rowCoun;
        } else if (rowCoun < 10000) {
            return rowCoun + "";
        } else {
            System.out.println("请扩展物料编码长度");
            return null;
        }
    }

    /**
     * 获取波纹对应的数值
     *
     * @param bowen
     * @return
     */
    public BigDecimal getBoWeenJiaoNum(String bowen) {

        if (StringUtils.isBlank(bowen)) {
            return new BigDecimal("0");
        } else if (bowen.contains(BoWenHanZi.HENG.getMsg())) {
            return new BigDecimal(bowen.split(BoWenHanZi.HENG.getMsg())[1].trim());
        } else if (bowen.contains(BoWenHanZi.SHU.getMsg())) {
            return new BigDecimal(bowen.split(BoWenHanZi.SHU.getMsg())[1].trim());
        } else {
            return new BigDecimal(bowen);
        }
    }
}
