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
        // 获取被拆分的数组个数

        for (int i = 0; i < targe.size(); i++) {
            List<MyRow> sub = new ArrayList<>();
            try {
                sub = ExcelUtils.getMyrows(targe.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            listArr.add(new MyRowTask(sub, targe.get(i).getFilepath()));
        }
        return listArr;
    }

    public void Doing(List<WuLiao> wuLiaos) {
        try {
            List<MyRowTask> listArr3 = createProductCategoryLogSaveTask(wuLiaos);
            ExecutorService executorService = newFixedThreadPool(listArr3.size() / 100);
            executorService.invokeAll(listArr3);
            executorService.shutdown();
            System.out.println("listArr3" + listArr3.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}