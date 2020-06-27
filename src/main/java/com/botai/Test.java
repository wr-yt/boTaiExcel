package com.botai;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ExcelDoing
 * @description: dd
 * @author: 杨韬
 * @create: 2020-06-27 09:37
 **/
public class Test {

    public static void main(String[] args) {


        MyRow myRow = new MyRow();


        List<MyRow> list = new ArrayList<>();
        list.add(myRow);

        myRow.setEnd(123);
        System.out.println(list.get(0) .getEnd());
        System.out.println(myRow.getEnd());
    }
}
