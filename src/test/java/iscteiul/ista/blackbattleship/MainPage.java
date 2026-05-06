package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        driver.get("https://www.jetbrains.com/");
    }

    public void searchFor(String text) {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        driver.get("https://www.jetbrains.com/search/?q=" + encodedText);
    }

    public void openDeveloperToolsPage() {
        driver.get("https://www.jetbrains.com/products/");
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}