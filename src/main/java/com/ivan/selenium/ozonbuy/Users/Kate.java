package com.ivan.selenium.ozonbuy.Users;

// Акцентировать внимание на точное определение элемента из корзины, а не элемента ниже, который может выкинуть случайную цену

import com.ivan.selenium.ozonbuy.WebDriver.Main;

public class Kate {
    public static void main(String[] args) {
        String relativePath = "Data/User Data Kate";
        int maxPrice = 3000;
        int timeWait = 3;
        boolean headless = false;

        Main.startWebDriver(relativePath, maxPrice, timeWait, headless);
    }
}
