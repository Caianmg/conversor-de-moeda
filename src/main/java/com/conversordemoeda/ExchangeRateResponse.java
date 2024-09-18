package com.conversordemoeda;

import java.util.Map;

public class ExchangeRateResponse {

    private String base;
    private Map<String, Double> rates;
    private String date;

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public String getDate() {
        return date;
    }
}