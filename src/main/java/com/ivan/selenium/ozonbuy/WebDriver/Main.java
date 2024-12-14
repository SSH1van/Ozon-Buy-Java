package com.ivan.selenium.ozonbuy.WebDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String OZON_CART_XPATH = "https://www.ozon.ru/cart";

    private static final String CHECKOUT_BUTTON_XPATH = "//button[.//text()[normalize-space()='Перейти к оформлению']]";
    private static final String PAY_ONLINE_BUTTON_XPATH = "//button[.//text()[normalize-space()='Оплатить онлайн']]";

    public static void startWebDriver(String relativePath, int maxPrice, long timeRefresh, long timeSleep, boolean headless) {
        WebDriverManager driverManager = new WebDriverManager();
        ChromeOptions options = WebDriverManager.createOptions(relativePath, headless);
        WebDriver driver = driverManager.initDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(timeRefresh));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Программа завершена. Освобождаем ресурсы...");
            driverManager.cleanUp();
        }));

        try {
            PageActions actions = new PageActions(driver);

            // Открываем ссылку на товар
            actions.openUrl(OZON_CART_XPATH, timeSleep);

            // Ожидаем, когда цена опуститься нижу указанной
            actions.waitBestPrice(maxPrice);

            // Найти и нажать на кнопки "Перейти к оформлению" и "Оплатить онлайн"
            actions.findAndClickOnButton(CHECKOUT_BUTTON_XPATH);
            actions.findAndClickOnButton(PAY_ONLINE_BUTTON_XPATH);

            System.out.println("Куплено");
        } catch (Exception e) {
            LOGGER.severe("Произошла ошибка: " + e.getMessage());
        } finally {
            driverManager.cleanUp();
        }
    }
}