package com.mobiledev.money.model;

import java.util.List;

public class Money {
    String base;
    Double amount;
    String date;
    List<Currency> currencies;

    public Money(String base, Double amount, String date, List<Currency> currencies) {
        this.base = base;
        this.amount = amount;
        this.date = date;
        this.currencies = currencies;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }
    
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

}
