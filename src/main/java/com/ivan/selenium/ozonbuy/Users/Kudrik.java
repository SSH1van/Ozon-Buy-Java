package com.ivan.selenium.ozonbuy.Users;

import com.ivan.selenium.ozonbuy.WebDriver.Main;

public class Kudrik {
    public static void main(String[] args) {
        String relativePath = "Data/User Data Kudrik";
        int maxPrice = 1000;
        int timeWait = 5;
        boolean headless = true;

        Main.startWebDriver(relativePath, maxPrice, timeWait, headless);
    }
}
