package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BattleshipPage {

    // Substitui o ID pelo que o Selenium IDE gravou no passo 2
    @FindBy(css=".btn-secondary:nth-child(2)")
    public WebElement playOnlineButton;

    // Procura um botão que tenha exatamente o texto "Cancel"
    @FindBy(xpath = "//button[contains(., 'Cancel')]")
    public WebElement cancelQueueButton;

    // Localizador para o menu de Torneios (baseado no teu passo 1)
    @FindBy(xpath = "/html/body/app-root/app-navigation/div/div[2]/main/app-game-landing/div/div/div/div/div[1]/app-juicy-button[3]")
    public WebElement tournamentsMenu;

    // Substitui 'name' pelo valor exato que encontrares no atributo formcontrolname
    @FindBy(css = "input[formcontrolname='name']")
    public WebElement tournamentNameInput;

    // Menu Dropdown do Jogo (Game) - Geralmente é um elemento select ou div
    @FindBy(xpath = "/html/body/app-root/app-navigation/div/div[2]/main/app-create-tournament/section/div/div/div/div/div/app-tournament-form/form/div[1]/mat-form-field")
    public WebElement gameDropdown;

    @FindBy(xpath = "//mat-option[contains(., 'Battleship')]")
    public WebElement battleshipOption;

    // Localizador para o botão Create (baseado no teu passo 2)
    @FindBy(xpath = "/html/body/app-root/app-navigation/div/div[2]/main/app-create-tournament/section/div/div/div/div/div/app-tournament-form/form/div[9]/button") // Confirma se este é o seletor do botão 'Create'
    public WebElement createButton;


    // --- US07: Ranking Diário ---

    // Procura o texto específico do painel do ranking
    @FindBy(xpath = "//*[contains(text(), 'Daily leaderboard')]")
    public WebElement dailyLeaderboardLabel;

    // Procura o elemento do 1º classificado para provar que a lista não está vazia
    // Substitui a variável firstRankedPlayer por esta:
    @FindBy(xpath = "/html/body/app-root/app-navigation/div/div[2]/main/app-game-landing/div/div/div/div/div[1]/div[1]/div[2]/a")
    public WebElement seeAll;

    // --- US08: Consultar jogos semelhantes (Home) ---

    // Esquece o XPath gigante. Este procura um link no menu lateral que contenha o texto 'Home'
    @FindBy(xpath = "/html/body/app-root/app-navigation/div/div[1]/app-sidenav/div/div/ul[2]/li[1]/a")
    public WebElement homeMenuButton;


    public BattleshipPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
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