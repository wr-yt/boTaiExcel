package com.botai;

/**
 * @program: ExcelDoing
 * @description: 物料区分
 * @author: 杨韬
 * @create: 2020-06-26 02:17
 **/
public enum WuLiaoQuFen {

    A("0000", "板片");
    private String code;
    private String msg;

    WuLiaoQuFen(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getByWuLiaoQuFenCode(String code) {
        switch (code) {
            case "0000":
                return "板片";
            case "0000-G":
                return "板片粘胶垫";
            case "1234":
                return "板片";
            case "1234-G":
                return "板片粘胶垫";
            case "T":
                return "板片";
            default:
                return "";
        }

    }
}
