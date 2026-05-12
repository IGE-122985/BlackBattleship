package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    private void clickWhenReady(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement clickableElement = wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );

        try {
            clickableElement.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    clickableElement
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    clickableElement
            );
        }
    }
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.jetbrains.com/");

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        driver.get("https://www.jetbrains.com/search/?q=Selenium");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("q=Selenium"));

        assertTrue(driver.getCurrentUrl().contains("q=Selenium"));
        assertTrue(driver.getCurrentUrl().contains("s=full"));
        assertTrue(driver.getTitle().toLowerCase().contains("jetbrains"));
    }
    @Test
    public void toolsMenu() {
        mainPage.toolsMenu.click();

        WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"));
        assertTrue(menuPopup.isDisplayed());
    }

    @Test
    public void navigationToAllTools() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        clickWhenReady(mainPage.toolsMenu);

        WebElement findYourTool = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a[data-test='suggestion-action']")
                )
        );

        String href = findYourTool.getAttribute("href");

        assertNotNull(href);
        assertTrue(href.contains("/products/"));

        driver.get(href);

        wait.until(ExpectedConditions.urlContains("/products/"));

        assertTrue(driver.getCurrentUrl().contains("/products/"));
        assertTrue(driver.getTitle().toLowerCase().contains("jetbrains"));
    }
}
