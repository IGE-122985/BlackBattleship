package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BattleshipPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final String url = "https://papergames.io/en/battleship";

    private final By playVsRobotButton = By.xpath(
            "//*[contains(normalize-space(.), 'Play vs robot')]"
    );

    private final By playWithFriendButton = By.xpath(
            "//*[contains(normalize-space(.), 'Play with a friend')]"
    );

    public BattleshipPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get(url);
        closeCookiesIfPresent();

        wait.until(driver ->
                driver.getCurrentUrl().toLowerCase().contains("battleship")
                        || driver.getTitle().toLowerCase().contains("battleship")
        );
    }

    private void closeCookiesIfPresent() {
        try {
            WebElement button = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(., 'Accept') or contains(., 'OK') or contains(., 'Agree')]")
                    ));
            button.click();
        } catch (Exception ignored) {
            // Se não aparecer aviso de cookies, continua normalmente.
        }
    }

    private boolean waitForPageText(String text) {
        try {
            return wait.until(driver -> driver.getPageSource().contains(text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    private WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void safeClick(By locator) {
        WebElement element = waitUntilClickable(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public boolean isBattleshipPageVisible() {
        return getCurrentUrl().toLowerCase().contains("battleship")
                || getTitle().toLowerCase().contains("battleship")
                || pageContains("Battleship Online");
    }

    public boolean areRulesVisible() {
        return waitForPageText("Rules of Battleship game online")
                || waitForPageText("Battleship is a turn-based");
    }

    public boolean isPlayVsRobotAvailable() {
        return waitForPageText("Play vs robot");
    }

    public void clickPlayVsRobot() {
        safeClick(playVsRobotButton);
    }

    public boolean isPlayWithFriendAvailable() {
        return waitForPageText("Play with a friend");
    }

    public void clickPlayWithFriend() {
        safeClick(playWithFriendButton);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean pageContains(String text) {
        return driver.getPageSource().contains(text);
    }
}