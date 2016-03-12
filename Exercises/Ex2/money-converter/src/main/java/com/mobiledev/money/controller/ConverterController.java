package com.mobiledev.money.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobiledev.money.model.Money;
import com.mobiledev.money.model.Currency;
import com.mobiledev.money.helper.JsonHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConverterController {

    //---------------------------------PRIVATE FIELDS----------------------------------
    private static ConverterController instance;
    private final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=%s&format=json&env=%s";
    private final String TABLE = "store://datatables.org/alltableswithkeys";
    private final String API_KEY = "63e3eb21650f4e8b8ac02a0e3b7c2015";
    private LinkedHashMap<String, String> fullName;

    //---------------------------------PUBLIC METHODS----------------------------------
    public String convert(String bases, Double amount, String symbols) throws UnsupportedEncodingException, JsonProcessingException {
        List<Money> moneyList = new ArrayList();

        bases = bases.toUpperCase(Locale.US);
        symbols = symbols.toUpperCase(Locale.US);

        String[] baseList = bases.split(",");
        String[] symbolList = symbols.split(",");

        for (String base : baseList) {
            List<Currency> currencyList = new ArrayList();
            String pairs = "";

            // Make a string of pair currencies separated by comma
            for (String symbol : symbolList) {
                pairs += base + symbol + ",";
            }

            pairs = pairs.substring(0, pairs.length() - 1);

            // Get JSON from URL
            String query = "select * from yahoo.finance.xchange where pair=\"" + pairs + "\"";

            String url = String.format(
                    BASE_URL,
                    URLEncoder.encode(query, "UTF-8"),
                    URLEncoder.encode(TABLE, "UTF-8")
            );

            // Get all currencies rates
            JSONObject queryResult = (JSONObject)JsonHelper.getJson(url, 10000);

            Object rates = ((JSONObject) ((JSONObject) queryResult.get("query")).get("results")).get("rate");

            if (rates instanceof JSONObject) {
                String name = symbolList[0];
                Double ratio = Double.parseDouble(((JSONObject) rates).get("Rate").toString());

                Currency currency = new Currency(
                        name,
                        fullName.get(name),
                        ratio,
                        amount * ratio
                );

                currencyList.add(currency);
            } else {
                Iterator<JSONObject> rate = ((JSONArray) rates).iterator();

                for (int i = 0; rate.hasNext(); i++) {
                    String name = symbolList[i];
                    Double ratio = Double.parseDouble(rate.next().get("Rate").toString());

                    Currency currency = new Currency(
                            name,
                            fullName.get(name),
                            ratio,
                            amount * ratio
                    );

                    currencyList.add(currency);
                }
            }

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Money money = new Money(base, amount, dateFormat.format(date), currencyList);
            moneyList.add(money);
        }

        return JsonHelper.getJsonString(moneyList);
    }

    public String getCurrencies() throws JsonProcessingException {
        return JsonHelper.getJsonString(fullName);
    }

    public static ConverterController getInstance() {
        if (instance == null) {
            instance = new ConverterController();
        }

        return instance;
    }

    //---------------------------------PRIVATE METHODS----------------------------------
    private ConverterController() {
        getCurrenciesNames();
    }

    private void getCurrenciesNames() {
        String url = "http://openexchangerates.org/api/currencies.json";
        fullName = (LinkedHashMap) JsonHelper.getOrderedJson(url, 10000);
    }
}
