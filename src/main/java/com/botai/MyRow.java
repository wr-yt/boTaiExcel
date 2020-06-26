package com.botai;

/**
 * @program: ExcelDoing
 * @description: demo
 * @author: 杨韬
 * @create: 2020-06-26 06:15
 **/
public class MyRow {

    private int rowCoun;
    private String mainNo;
    private String boWenSon;
    private WuLiao wuLiao;
    private String wuLiaoQuFen;
    private String caiZhi;
    private String xingHao;
    private double houDu;

    private int start;
    private int end;

    public MyRow() {
    }

    public MyRow(String mainNo, String boWenSon, WuLiao wuLiao, String wuLiaoQuFen, String caiZhi, String xingHao, int start, int end) {
        this.start = start;
        this.end = end;
        this.rowCoun = rowCoun;
        this.mainNo = mainNo;
        this.boWenSon = boWenSon;
        this.wuLiao = wuLiao;
        this.wuLiaoQuFen = wuLiaoQuFen;
        this.caiZhi = caiZhi;
        this.xingHao = xingHao;
    }

    public int getRowCoun() {
        return rowCoun;
    }

    public String getMainNo() {
        return mainNo;
    }

    public String getBoWenSon() {
        return boWenSon;
    }

    public WuLiao getWuLiao() {
        return wuLiao;
    }

    public String getWuLiaoQuFen() {
        return wuLiaoQuFen;
    }

    public String getCaiZhi() {
        return caiZhi;
    }

    public String getXingHao() {
        return xingHao;
    }

    public void setRowCoun(int rowCoun) {
        this.rowCoun = rowCoun;
    }

    public void setMainNo(String mainNo) {
        this.mainNo = mainNo;
    }

    public void setBoWenSon(String boWenSon) {
        this.boWenSon = boWenSon;
    }

    public void setWuLiao(WuLiao wuLiao) {
        this.wuLiao = wuLiao;
    }

    public void setWuLiaoQuFen(String wuLiaoQuFen) {
        this.wuLiaoQuFen = wuLiaoQuFen;
    }

    public void setCaiZhi(String caiZhi) {
        this.caiZhi = caiZhi;
    }

    public void setXingHao(String xingHao) {
        this.xingHao = xingHao;
    }

    public double getHouDu() {
        return houDu;
    }

    public void setHouDu(double houDu) {
        this.houDu = houDu;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "MyRow{" +
                "rowCoun=" + rowCoun +
                ", mainNo='" + mainNo + '\'' +
                ", boWenSon='" + boWenSon + '\'' +
                ", wuLiao=" + wuLiao +
                ", wuLiaoQuFen='" + wuLiaoQuFen + '\'' +
                ", caiZhi='" + caiZhi + '\'' +
                ", xingHao='" + xingHao + '\'' +
                ", houDu=" + houDu +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
