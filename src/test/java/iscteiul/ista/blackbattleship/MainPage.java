package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainPage {
    @FindBy(xpath = "//button[contains(., 'Solutions')]")
    public WebElement solutionsMenu;

    // Este é o link exato que tinhas selecionado no teu print (data-test="banner-link") [cite: 53, 80]
    @FindBy(css = "[data-test='banner-link']")
    public WebElement seeDeveloperToolsButton;
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

    @FindBy(css = "[data-test='main-menu-item-action']")
    public WebElement toolsMenu;
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