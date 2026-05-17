package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BattleshipPage {
    private static final String URL = "https://papergames.io/en/battleship";

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BattleshipPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        acceptCookiesIfPresent();
    }

    public boolean isBattleshipPageVisible() {
        return driver.getTitle().toLowerCase().contains("battleship")
                || pageText().contains("battleship online");
    }

    public boolean areRulesVisible() {
        String text = pageText();

        return text.contains("rules of battleship")
                && text.contains("turn-based")
                && text.contains("10x10");
    }

    public void playWithFriend() {
        clickByVisibleText("Play with a friend");
    }

    public void playVsRobot() {
        clickByVisibleText("Play vs robot");
    }

    public void createTournament() {
        clickByVisibleText("Create tournament");
    }

    public void playWithRandomPlayer() {
        clickByVisibleText("Play online with a random player");
    }

    public boolean hasGameInteractionStarted() {
        wait.until(driver -> {
            String text = pageText();
            String url = currentUrl();

            return url.contains("battleship")
                    || text.contains("nickname")
                    || text.contains("name")
                    || text.contains("friend")
                    || text.contains("robot")
                    || text.contains("game")
                    || text.contains("start")
                    || hasVisibleInput();
        });

        return true;
    }

    public boolean isTournamentPageOrDialogVisible() {
        wait.until(driver -> {
            String text = pageText();
            String url = currentUrl();

            return url.contains("tournament")
                    || text.contains("tournament")
                    || text.contains("create");
        });

        return true;
    }

    public String currentUrl() {
        return driver.getCurrentUrl().toLowerCase();
    }

    private void clickByVisibleText(String text) {
        By locator = By.xpath(
                "//*[self::a or self::button or @role='button']" +
                        "[contains(normalize-space(.), \"" + text + "\")]"
        );

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    element
            );
        }
    }

    private boolean hasVisibleInput() {
        List<WebElement> inputs = driver.findElements(By.cssSelector("input"));

        for (WebElement input : inputs) {
            if (input.isDisplayed()) {
                return true;
            }
        }

        return false;
    }

    private void acceptCookiesIfPresent() {
        try {
            WebElement button = driver.findElement(
                    By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept') " +
                            "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'agree') " +
                            "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'allow') " +
                            "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'ok')]")
            );

            if (button.isDisplayed()) {
                button.click();
            }
        } catch (Exception ignored) {
            // Não apareceu banner de cookies.
        }
    }

    private String pageText() {
        return driver.findElement(By.tagName("body")).getText().toLowerCase();
    }
}