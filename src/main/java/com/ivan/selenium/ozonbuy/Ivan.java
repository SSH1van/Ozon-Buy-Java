package com.ivan.selenium.ozonbuy;

public class Ivan {
    public static void main(String[] args) {
        String relativePath = "Data/User Data Ivan";
        int maxPrice = 1000;
        int timeWait = 5;
        boolean headless = true;

        Main.startWebDriver(relativePath, maxPrice, timeWait, headless);
    }
}
