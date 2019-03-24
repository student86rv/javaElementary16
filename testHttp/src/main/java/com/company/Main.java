package com.company;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Введите дату в формате dd.mm.yyyy:");
        Scanner scanner = new Scanner(System.in);
        String inputDate = scanner.nextLine();

//        Date dateNow = new Date();
//        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.yyyy");
//        try {
//            Date input = simple.parse(inputDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        long time = System.currentTimeMillis();
        String response = HttpUtil.sendRequest("https://api.privatbank.ua/p24api/exchange_rates?json=true&date=" + inputDate, null, null);
        time = System.currentTimeMillis() - time;
        System.out.println(response);
        System.out.println("Request time: " + time / 1000.0 + "s");

        Gson gson = new Gson();
        Bank bankAtDate = gson.fromJson(response, Bank.class);

        for (Currency currency: bankAtDate.getExchangeRate()) {
            if (currency.getCurrency() != null && currency.getCurrency().equals("USD")) {
                System.out.println("Курс покупки: " + currency.getPurchaseRate());
                System.out.println("Курс продажи: " + currency.getSaleRate());
            }
        }
    }
}
