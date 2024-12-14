package com.ivan.selenium.ozonbuy.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PageActions {
    private final WebDriver driver;

    public PageActions(WebDriver driver) {
        this.driver = driver;
    }

    // Открытие ссылки
    public void openUrl(String url, long timeSleep) throws InterruptedException {
        try {
            driver.get(url);
        } catch (Exception TimeoutException) {
            // Ошибка загрузки страницы ожидаема
        }
        TimeUnit.SECONDS.sleep(timeSleep);
    }

    // Ожидаем, когда цена опуститься нижу указанной
    private static final String PRICE_ELEMENT_XPATH = "//span[text()='С Ozon Картой']/ancestor::div[1]//font";
    public void waitBestPrice(int maxPrice) throws InterruptedException {
        Random random = new Random();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(300));

        while (true) {
            try {
                WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRICE_ELEMENT_XPATH)));
                String priceText = priceElement.getText();
                int price = Integer.parseInt(priceText.replaceAll("\\D", ""));

                if (price < maxPrice) {
                    break;
                }

                try {
                    driver.navigate().refresh();
                } catch (Exception e) {
                    // Ошибка обновления страницы ожидаема
                }
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(250));
            } catch (Exception e) {
                try {
                    driver.navigate().refresh();
                } catch (Exception ex) {
                    // Ошибка обновления страницы ожидаема
                }
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(250));
            }
        }
    }

    // Найти и нажать на кнопку
    public void findAndClickOnButton(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(xpath)));
        try {
            checkoutButton.click();
        } catch (Exception ex) {
            // Ошибка обновления страницы ожидаема
        }

    }
}
