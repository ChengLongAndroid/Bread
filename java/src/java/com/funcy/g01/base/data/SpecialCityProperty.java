package com.funcy.g01.base.data;

public class SpecialCityProperty {
    private int id;

    private String province;

    private String city;
    
    public SpecialCityProperty(int id, String province, String city){
    	this.id = id;
    	this.province = province;
    	this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}