package com.example.uf_web_mobile.models;

public class History {

    private String priceH;
    private String dateH;
    private String timeH;

    public History(String priceH, String dateH, String timeH) {
        this.priceH = priceH;
        this.dateH = dateH;
        this.timeH = timeH;
    }

    public String getPriceH() {
        return priceH;
    }

    public void setPriceH(String priceH) {
        this.priceH = priceH;
    }

    public String getDateH() {
        return dateH;
    }

    public void setDateH(String dateH) {
        this.dateH = dateH;
    }

    public String getTimeH() {
        return timeH;
    }

    public void setTimeH(String timeH) {
        this.timeH = timeH;
    }
}
