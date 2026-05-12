package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleshipPageTest {
    private WebDriver driver;
    private BattleshipPage page;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        page = new BattleshipPage(driver);
        page.open();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void UserStoryTest1_consultarInstrucoesDoJogo() {
        assertTrue(page.isBattleshipPageVisible());
        assertTrue(page.areRulesVisible());
    }

    @Test
    public void UserStoryTest2_jogarComUmAmigo() {
        page.playWithFriend();

        assertTrue(page.hasGameInteractionStarted());
    }

    @Test
    public void UserStoryTest3_jogarContraRobot() {
        page.playVsRobot();

        assertTrue(page.hasGameInteractionStarted());
    }

    @Test
    public void UserStoryTest4_criarTorneio() {
        page.createTournament();

        assertTrue(page.isTournamentPageOrDialogVisible());
    }
}