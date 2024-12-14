package com.ivan.selenium.ozonbuy.Users;

// Акцентировать внимание на точное определение элемента из корзины, а не элемента ниже, который может выкинуть случайную цену.
// Подобрать время обновления страницы

import com.ivan.selenium.ozonbuy.WebDriver.Main;

public class Kate {
    public static void main(String[] args) {
        String relativePath = "Data/User Data Kate";
        int maxPrice = 1000;
        long timeRefresh = 400;
        long timeSleep = 3;
        boolean headless = false;

        Main.startWebDriver(relativePath, maxPrice, timeRefresh, timeSleep, headless);
    }
}
