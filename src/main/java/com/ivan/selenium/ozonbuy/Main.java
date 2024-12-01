package com.ivan.selenium.ozonbuy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static WebDriver driver;

    private static void cleanUpWebDriver() {
        if (driver != null) {
            driver.quit();
        }
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            int exitCode;

            if (osName.contains("win")) {
                // Завершение процесса для Windows
                exitCode = executeCommand("taskkill", "/F", "/IM", "chromedriver.exe", "/T");
            } else {
                // Завершение процесса для Linux/Mac
                exitCode = executeCommand("pkill", "-f", "chromedriver");
            }

            if (exitCode == 0) {
                LOGGER.info("Процесс chromedriver успешно завершен.");
            } else {
                LOGGER.warning("Не удалось завершить процесс chromedriver. Код выхода: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Произошла ошибка при завершении процесса chromedriver.", e);
            Thread.currentThread().interrupt(); // Восстановление флага прерывания
        }
    }

    private static int executeCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        return process.waitFor(); // Ждем завершения процесса и возвращаем код выхода
    }

    public static void startWebDriver(String relativePath, int maxPrice, int timeWait, boolean headless) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Программа завершена. Освобождаем ресурсы...");
            cleanUpWebDriver();
        }));

        String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
        chromeOptions.addArguments("user-data-dir=" + absolutePath);
        chromeOptions.addArguments("profile-directory=Default");
        if (headless) {
            chromeOptions.addArguments("--headless");
        }

        driver = new ChromeDriver(chromeOptions);

        try {
            driver.get("https://www.ozon.ru/cart");
            TimeUnit.SECONDS.sleep(timeWait);

            Random random = new Random();
            while (true) {
                try {
                    WebElement priceElement = driver.findElement(By.xpath("//span[@class='c3022-a1 tsHeadline400Small c3022-b1 c3022-a5']"));
                    String priceText = priceElement.getText();
                    int price = Integer.parseInt(priceText.replaceAll("\\D", ""));

                    if (price < maxPrice) {
                        break;
//                         System.out.println(price);
                    }

                    driver.navigate().refresh();
                    TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(1500));
                } catch (Exception e) {
                    driver.navigate().refresh();
                    TimeUnit.MILLISECONDS.sleep(500 + random.nextInt(1500));
                }
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'b2120-a0') and .//div[text()='Перейти к оформлению']]")));
            checkoutButton.click();

            WebElement payOnlineButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'b2120-a0') and .//div[text()='Оплатить онлайн']]")));
            payOnlineButton.click();

            System.out.println("bought");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Произошла ошибка.", e);
        } finally {
            try {
                TimeUnit.SECONDS.sleep(120);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Пауза была прервана.", e);
            }
            driver.quit();
        }
    }
}