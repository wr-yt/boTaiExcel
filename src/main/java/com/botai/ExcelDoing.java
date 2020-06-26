package com.botai;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

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
            CreateMultipleSheetNew createMultipleSheetNew = new CreateMultipleSheetNew();
            createMultipleSheetNew.Doing(wuLiaos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
