package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class BattleshipTest {
    private WebDriver driver;
    private BattleshipPage battleshipPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        battleshipPage = new BattleshipPage(driver);
        battleshipPage.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void US01_consultarPaginaDoJogo() {
        assertTrue(
                battleshipPage.isBattleshipPageVisible(),
                "URL atual: " + battleshipPage.getCurrentUrl()
                        + " | Título atual: " + battleshipPage.getTitle()
        );
    }

    @Test
    public void US02_consultarRegrasDoJogo() {
        assertTrue(
                battleshipPage.areRulesVisible(),
                "As regras do jogo não foram encontradas na página."
        );
    }
}