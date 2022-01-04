package com.example.covid_19;

public class DataHandler {

    private String[] data;
    private String Reg_Name;
    private String Pro_Name;

    @Override
    public String toString() {
        return "DataHandler{" +
                "Reg_Name='" + Reg_Name + '\'' +
                ", Pro_Name='" + Pro_Name + '\'' +
                ", Mun_Name='" + Mun_Name + '\'' +
                ", Date='" + Date + '\'' +
                ", active_muni='" + active_muni + '\'' +
                ", confirmed=" + confirmed +
                ", total_active=" + total_active +
                ", recovered=" + recovered +
                ", deceased=" + deceased +
                '}';
    }

    private String Mun_Name;
    private String Date;
    private String active_muni;
    private int confirmed;
    private int total_active;
    private int recovered;
    private int deceased;

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getTotal_active() {
        return total_active;
    }

    public void setTotal_active(int total_active) {
        this.total_active = total_active;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public String getReg_Name() {
        return Reg_Name;
    }

    public void setReg_Name(String reg_Name) {
        this.Reg_Name = reg_Name;
    }

    public String getPro_Name() {
        return Pro_Name;
    }

    public void setPro_Name(String pro_Name) {
        this.Pro_Name = pro_Name;
    }

    public String getMun_Name() {
        return Mun_Name;
    }

    public void setMun_Name(String mun_Name) {
        this.Mun_Name = mun_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getActive_muni() {
        return active_muni;
    }

    public void setActive_muni(String active_muni) {
        this.active_muni = active_muni;
    }


}
