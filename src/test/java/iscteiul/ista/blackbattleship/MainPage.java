package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
    @FindBy(xpath = "//button[contains(., 'Solutions')]")
    public WebElement solutionsMenu;

    // Este é o link exato que tinhas selecionado no teu print (data-test="banner-link") [cite: 53, 80]
    @FindBy(css = "[data-test='banner-link']")
    public WebElement seeDeveloperToolsButton;

    @FindBy(xpath = "//*[@data-test='suggestion-action']")
    public WebElement findYourToolsButton;

    @FindBy(css = "[data-test='main-menu-item-action']")
    public WebElement toolsMenu;

    @FindBy(css = "[data-test='site-header-search-action']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
