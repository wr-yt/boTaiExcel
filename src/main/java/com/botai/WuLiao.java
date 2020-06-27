package com.botai;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 *
 * 商品品类表
 */
public class WuLiao implements Serializable {
    private static final long serialVersionUID = 1L;

    // 型号map
    public Map<String, String> xingHaoMap = new ConcurrentHashMap<>();
    /**
     * 序号
     */
    private String xuHao;

    /**
     * 主型号
     */
    private String mainNo;

    /**
     * 厚度
     */
    private String houDu;

    /**
     * 波纹角度
     */
    private String boWenJiaoDu;

    /**
     * 长度
     */
    private long length;

    /**
     * 宽度
     */
    private long with;

    private String filepath;

    /**
     * 删除标记
     */
    private boolean flag = true;

    public String getXuHao() {
        return xuHao;
    }

    public void setXuHao(String xuHao) {
        this.xuHao = xuHao;
    }

    public String getMainNo() {
        return mainNo;
    }

    public void setMainNo(String mainNo) {
        this.mainNo = mainNo;
    }


    public String getBoWenJiaoDu() {
        return boWenJiaoDu;
    }

    public void setBoWenJiaoDu(String boWenJiaoDu) {
        this.boWenJiaoDu = boWenJiaoDu;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getWith() {
        return with;
    }

    public void setWith(long with) {
        this.with = with;
    }

    public String getHouDu() {
        return houDu;
    }

    public void setHouDu(String houDu) {
        this.houDu = houDu;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * 设置型号
     *
     * @return
     */
    public String setXingHao(String mainNo, String[] boWenJiaoDuSplit) {
        int length = boWenJiaoDuSplit.length;
        int boWenKINJIEZHI = Constans.boWenKINJIEZHI;
        // 有两个
        String boWenJiaoDu = "";
        StringBuilder val = new StringBuilder("");
        if (length > 1) {
            for (int i = 0; i < length; i++) {
                boWenJiaoDu = boWenJiaoDuSplit[i].trim();
                if (boWenJiaoDu.contains(BoWenHanZi.SHU.getMsg())) {
                    val.append(mainNo + "-L/");
                    if (val.indexOf("&") == -1) {
                        val.append("@");
                    }
                } else if (boWenJiaoDu.contains(BoWenHanZi.HENG.getMsg())) {
                    val.append(mainNo + "-H/");
                    if (val.indexOf("&") == -1) {
                        val.append("@");
                    }
                } else if (length == 2) {
//                    if (StringUtils.isNotBlank(boWenJiaoDu)) {
                    String s = boWenJiaoDuSplit[0];
                    String s2 = boWenJiaoDuSplit[1];


//                    BigDecimal num = new BigDecimal(s.replace(BoWenHanZi.HENG.getMsg(),"").trim().replace(BoWenHanZi.SHU.getMsg(),"").trim());
                    BigDecimal num2 = null;
                    BigDecimal num = null;
                    try {
                        num = new BigDecimal(s.trim());
//                        num2 = new BigDecimal(s2.replace(BoWenHanZi.HENG.getMsg(),"").trim().replace(BoWenHanZi.SHU.getMsg(),"").trim());
                        num2 = new BigDecimal(s2.trim());
                    } catch (Exception e) {
                        num = new BigDecimal(s.replace(BoWenHanZi.HENG.getMsg(), "").trim().replace(BoWenHanZi.SHU.getMsg(), "").trim());
                        num2 = new BigDecimal(s2.replace(BoWenHanZi.HENG.getMsg(), "").trim().replace(BoWenHanZi.SHU.getMsg(), "").trim());
                        if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                            val.append(mainNo + "-L/");
                        } else {
                            val.append(mainNo + "-H/");
                        }
                        val.append("@");
                        if (num2.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                            val.append(mainNo + "-L/");
                        } else {
                            val.append(mainNo + "-H/");
                        }
                        break;
                    }

                    if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0 && num2.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {

                        val.append(mainNo + "-L" + "/" + "@" + mainNo + "-LL" + "/");
                    } else if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) > 0 && num2.compareTo(new BigDecimal(boWenKINJIEZHI)) > 0) {
                        val.append(mainNo + "-H" + "/" + "@" + mainNo + "-HH" + "/");
                    } else {
                        if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                            val.append(mainNo + "-L/");
                        } else {
                            val.append(mainNo + "-H/");
                        }
                        val.append("@");
                        if (num2.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                            val.append(mainNo + "-L/");
                        } else {
                            val.append(mainNo + "-H/");
                        }
                    }
                    break;
                } else if (length > 2) {
                    BigDecimal num = new BigDecimal(boWenJiaoDu.replace(BoWenHanZi.HENG.getMsg(), "").trim().replace(BoWenHanZi.SHU.getMsg(), "").trim());
                    if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                        val.append(mainNo + "-L/");
                    } else {
                        val.append(mainNo + "-H/");
                    }
                    val.append("@");
                }
            }
        } else if (length == 1) {
            boWenJiaoDu = boWenJiaoDuSplit[0];

            if (StringUtils.isBlank(boWenJiaoDu)) {

            } else if (boWenJiaoDu.contains(BoWenHanZi.SHU.getMsg())) {
                val.append(mainNo + "-L/");
            } else if (boWenJiaoDu.contains(BoWenHanZi.HENG.getMsg())) {
                val.append(mainNo + "-H/");
            } else {
                BigDecimal num = new BigDecimal(boWenJiaoDu);
                if (num.compareTo(new BigDecimal(boWenKINJIEZHI)) <= 0) {
                    val.append(mainNo + "-L/");
                } else {
                    val.append(mainNo + "-H/");
                }
            }
        }
        return val.toString();

    }
}
