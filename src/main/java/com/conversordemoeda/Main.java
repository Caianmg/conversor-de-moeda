package com.conversordemoeda;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateApiService exchangeService = new ExchangeRateApiService();

        try {
            exchangeService.sendRequest();

            boolean running = true;

            while (running) {
                System.out.println("\n*** Conversor de Moedas ***");
                System.out.println("1. Dólar para Peso Argentino");
                System.out.println("2. Peso Argentino para Dólar");
                System.out.println("3. Dólar para Real Brasileiro");
                System.out.println("4. Real Brasileiro para Dólar");
                System.out.println("5. Dólar para Peso Colombiano");
                System.out.println("6. Peso Colombiano para Dólar");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        convertCurrency(exchangeService, "USD", "ARS", scanner);
                        break;
                    case 2:
                        convertCurrency(exchangeService, "ARS", "USD", scanner);
                        break;
                    case 3:
                        convertCurrency(exchangeService, "USD", "BRL", scanner);
                        break;
                    case 4:
                        convertCurrency(exchangeService, "BRL", "USD", scanner);
                        break;
                    case 5:
                        convertCurrency(exchangeService, "USD", "COP", scanner);
                        break;
                    case 6:
                        convertCurrency(exchangeService, "COP", "USD", scanner);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Saindo do programa...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao obter taxas de câmbio: " + e.getMessage());
        } finally {
            scanner.close();
        }

    }

    private static void convertCurrency(ExchangeRateApiService exchangeService, String fromCurrency, String toCurrency, Scanner scanner) {
        try {
            System.out.print("Digite o valor a ser convertido: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            double convertedAmount = exchangeService.convert(amount, fromCurrency, toCurrency);
            System.out.printf("%.2f %s é igual a %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}