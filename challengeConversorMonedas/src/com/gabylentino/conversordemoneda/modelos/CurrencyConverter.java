package com.gabylentino.conversordemoneda.modelos;

import java.util.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;

public class CurrencyConverter {
    private Map<String, Double> rates;
    private List<String> currencies;
    private List<String> history;

    public CurrencyConverter() {
        rates = new HashMap<>();
        currencies = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void fetchExchangeRates() {
        try {
            APIConnector connector = new APIConnector();
            JsonObject jsonResponse = connector.fetchJsonFromAPI();
            JsonObject ratesObject = jsonResponse.getAsJsonObject("conversion_rates");
            for (Map.Entry<String, JsonElement> entry : ratesObject.entrySet()) {
                String currency = entry.getKey();
                double rate = entry.getValue().getAsDouble();
                rates.put(currency, rate);
                currencies.add(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        if (!rates.containsKey(fromCurrency) || !rates.containsKey(toCurrency)) {
            System.out.println("Invalid currency.");
            return -1;
        }

        double fromRate = rates.get(fromCurrency);
        double toRate = rates.get(toCurrency);
        double convertedAmount = amount * toRate / fromRate;

        history.add(String.format("%s %s -> %s %s", amount, fromCurrency, convertedAmount, toCurrency));
        return convertedAmount;
    }

    public void printHistory() {
        for (String entry : history) {
            System.out.println(entry);
        }
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}

class APIConnector {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/fd8ca6fa902ea2a7d20ee299/latest/USD";

    public JsonObject fetchJsonFromAPI() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } else {
            throw new IOException("Failed to fetch exchange rates.");
        }
    }
}

