package com.gabylentino.conversordemoneda.principal;

import com.gabylentino.conversordemoneda.modelos.CurrencyConverter;

import java.util.*;

public class Principal {
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.fetchExchangeRates();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Bienvenidos al conversor de monedas!\nElige una opcion para convertir:");
            System.out.println("\nMenu:");
            System.out.println("1. Dolar a Peso Argentino");
            System.out.println("2. Peso Argentino a Dolar");
            System.out.println("3. Dolar a Real Brasileño");
            System.out.println("4. Real Brasileño a Dolar");
            System.out.println("5. Dolar a Peso Colombiano");
            System.out.println("6. Peso Colombiano a Dolar");
            System.out.println("7. Ver Historial de Conversiones");
            System.out.println("8. Salir del programa");

            System.out.print("Elige una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    convertCurrency(converter, "USD", "ARS");
                    break;
                case "2":
                    convertCurrency(converter, "ARS", "USD");
                    break;
                case "3":
                    convertCurrency(converter, "USD", "BRL");
                    break;
                case "4":
                    convertCurrency(converter, "BRL", "USD");
                    break;
                case "5":
                    convertCurrency(converter, "USD", "COP");
                    break;
                case "6":
                    convertCurrency(converter, "COP", "USD");
                    break;
                case "7":
                    converter.printHistory();
                    break;
                case "8":
                    System.out.println("Saliendo del programa, gracias por usar nuestro conversor!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opcion incorrecta, intenta de nuevo!");
            }
        }
    }

    private static void convertCurrency(CurrencyConverter converter, String fromCurrency, String toCurrency) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el monto a convertir " + fromCurrency + ": ");
        double amount = scanner.nextDouble();
        double convertedAmount = converter.convertCurrency(amount, fromCurrency, toCurrency);
        if (convertedAmount >= 0) {
            System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        }
    }
}
