package com.botai;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileInputStream;
import java.util.*;
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
            CommandUtils.delExcelsFiles();

            Constans.setlieMapFinallData(args[2]);
            // 命令删除excels
            ExcelUtils.getTemplateSheet(new FileInputStream(args[1]));
            List<WuLiao> wuLiaos = ExcelUtils.readFromXLS2003(args[0], StringUtils.equals(args[2], "teshu") ? "B" : "");

            Map<String, Long> collectMap = wuLiaos.stream().collect(Collectors.groupingBy(WuLiao::getMainNo, Collectors.counting()));

            wuLiaos = Collections.synchronizedList(wuLiaos);
            WuLiao wuLiao = null;
            String mainNo = "";
            WuLiao wuLiaoTemp = null;
            Map<String, WuLiao> mapBymain = new HashMap<>();
            for (int i = 0; i < wuLiaos.size(); i++) {
                wuLiao = wuLiaos.get(i);
                mainNo = wuLiao.getMainNo();
                if (collectMap.get(mainNo) > 1) {
                    if (mapBymain.containsKey(mainNo)) {
                        wuLiaoTemp = mapBymain.get(mainNo);
                        wuLiaoTemp.setBoWenJiaoDu(wuLiaoTemp.getBoWenJiaoDu() + "/" + wuLiao.getBoWenJiaoDu());
                        wuLiao.setFlag(false);
                    } else {
                        mapBymain.put(mainNo, wuLiao);
                    }
                }
                wuLiao.setFilepath(CopyFile.copyFiles(args[1], wuLiao.getXuHao()));
            }
            CreateMultipleSheetNew createMultipleSheetNew = new CreateMultipleSheetNew();
            createMultipleSheetNew.Doing(wuLiaos);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
