package com.ivan.selenium.ozonbuy.Users;

import com.ivan.selenium.ozonbuy.WebDriver.Main;

public class Korsh {
    public static void main(String[] args) {
        String relativePath = "Data/User Data Korsh";
        int maxPrice = 1000;
        long timeRefresh = 1800;
        long timeSleep = 3000;
        boolean headless = true;

        Main.startWebDriver(relativePath, maxPrice, timeRefresh, timeSleep, headless);
    }
}
