package com.conversordemoeda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateApiService {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/42d83de84438ba1ff0607351/latest/USD";
    private JsonObject exchangeRates;

    public HttpRequest createRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(API_URL))
                .build();
    }

    public void sendRequest() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String reqResult = jsonObject.get("result").getAsString();
        if (!"success".equals(reqResult)) {
            throw new Exception("Erro na requisição da API: " + reqResult);
        }

        this.exchangeRates = jsonObject.getAsJsonObject("conversion_rates");
    }

    public double convert(double amount, String fromCurrency, String toCurrency) {
        if (exchangeRates == null) {
            throw new IllegalArgumentException("Taxas de câmbio não foram carregadas.");
        }

        double fromRate = exchangeRates.get(fromCurrency).getAsDouble();
        double toRate = exchangeRates.get(toCurrency).getAsDouble();

        return (amount / fromRate) * toRate;
    }

}