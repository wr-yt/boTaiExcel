package com.botai;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ExcelDoing
 * @description: 主函数
 * @author: 杨韬
 * @create: 2020-06-25 23:07
 **/
public class ExcelDoing {

    public static void main(String[] args) {


        try {
            ExcelUtils.getTemplateSheet(new FileInputStream(args[1]));
            List<WuLiao> wuLiaos = ExcelUtils.readFromXLS2003(args[0]);
            wuLiaos = Collections.synchronizedList(wuLiaos);
            WuLiao wuLiao = null;
            for (int i = 0; i < wuLiaos.size(); i++) {
                wuLiao = wuLiaos.get(i);
                wuLiao.setFilepath(CopyFile.copyFiles(args[1], wuLiao.getMainNo()));
            }


            List<WuLiao> boWenBlank = wuLiaos.stream().filter(item -> StringUtils.isBlank(item.getBoWenJiaoDu())).collect(Collectors.toList());
            List<WuLiao> boWennotBlak = wuLiaos.stream().filter(item -> StringUtils.isNoneBlank(item.getBoWenJiaoDu())).collect(Collectors.toList());
            CreateMultipleSheetNew createMultipleSheetNew = new CreateMultipleSheetNew();
            createMultipleSheetNew.Doing(boWennotBlak);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
