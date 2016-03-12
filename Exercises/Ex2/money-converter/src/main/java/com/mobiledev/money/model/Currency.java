package com.mobiledev.money.model;

public class Currency {
    String abbr;
    String name;
    Double ratio;
    Double amount;

    public Currency(String abbr, String name, Double ratio, Double amount) {
        this.abbr = abbr;
        this.name = name;
        this.ratio = ratio;
        this.amount = amount;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
}
