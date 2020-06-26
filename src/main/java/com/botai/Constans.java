package com.botai;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: ExcelDoing
 * @description: 静态数据
 * @author: 杨韬
 * @create: 2020-06-25 23:27
 **/
public class Constans {

    /**
     * 材质
     */
    public static List<String> caiZhiList = new ArrayList<>();

    // 列对应的静态数据
    public static final Map<Integer, Object> lieMapFinallData = new ConcurrentHashMap<Integer, Object>();
    // 型号map
    public static Map<String, String> xingHaoMap = new ConcurrentHashMap<>();

    // 型号后缀
    public static List<String> xingHaoHouZuiList = new ArrayList<>();
    /**
     * 波纹临界值
     * 小于等于90的，【型号】里面都是【-L】
     * 大于90的，【型号】里面是【-H】
     */
    public static final int boWenKINJIEZHI = 90;

    static {
        caiZhiList.add("254SMo");
        caiZhiList.add("304");
        caiZhiList.add("316L");
        caiZhiList.add("904L");
        caiZhiList.add("C2000");
        caiZhiList.add("C276");
        caiZhiList.add("国产钛");
        caiZhiList.add("进口钛");
        caiZhiList.add("进口钛钯");
        caiZhiList.add("镍201");
        caiZhiList = Collections.synchronizedList(caiZhiList);

        //死数据
        lieMapFinallData.put(1, "成品板片");
        lieMapFinallData.put(2, "LB1");
        lieMapFinallData.put(3, "成品板片");
        lieMapFinallData.put(4, "片");
        lieMapFinallData.put(5, "自制件,成品");
        lieMapFinallData.put(16, "1.000000");
        lieMapFinallData.put(17, "0.00");
        lieMapFinallData.put(18, "0.000000");

        //型号后缀
        xingHaoHouZuiList.add("0000");
        xingHaoHouZuiList.add("0000-G");
        xingHaoHouZuiList.add("1234");
        xingHaoHouZuiList.add("1234-G");
        xingHaoHouZuiList.add("T");
        xingHaoHouZuiList = Collections.synchronizedList(xingHaoHouZuiList);

    }
}
