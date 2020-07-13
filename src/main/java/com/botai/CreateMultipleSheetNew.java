package com.botai;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/**
 * @author zkn
 * @date 2017/11/15 23:34
 */
public class CreateMultipleSheetNew {
    /**
     * @param targe
     * @return
     */
    public static List<MyRowTask> createProductCategoryLogSaveTask(List<WuLiao> targe) {
        List<MyRowTask> listArr = new ArrayList<MyRowTask>();
        WuLiao wuLiao = null;
        // 获取被拆分的数组个数
        for (int i = 0; i < targe.size(); i++) {
            try {
                wuLiao = targe.get(i);
                if (!wuLiao.isFlag()) {
                    continue;
                }
                List<MyRow> sub = new ArrayList<>();
                sub = ExcelUtils.getMyrows(targe.get(i));
                listArr.add(new MyRowTask(sub, targe.get(i).getFilepath()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return listArr;
    }

    public void Doing(List<WuLiao> wuLiaos) {
        try {
            List<MyRowTask> listArr3 = createProductCategoryLogSaveTask(wuLiaos);
            ExecutorService executorService = newFixedThreadPool(listArr3.size() / 10 ==0 ?1:(listArr3.size() / 10));
            executorService.invokeAll(listArr3);
            executorService.shutdown();
            System.out.println("listArr3" + listArr3.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}