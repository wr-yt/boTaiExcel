package com.botai;

/**
 * @program: ExcelDoing
 * @description: 波纹中的汉子
 * @author: 杨韬
 * @create: 2020-06-26 00:19
 **/
public enum BoWenHanZi {
    /**
     * 竖
     */
    SHU("竖"),
    /**
     * 横
     */
    HENG("横");
    private String msg;


    BoWenHanZi(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
