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

    @Test
    public void US03_jogarContraRobot() {
        assertTrue(
                battleshipPage.isPlayVsRobotAvailable(),
                "O botão/opção Play vs robot não foi encontrado."
        );

        battleshipPage.clickPlayVsRobot();

        assertTrue(
                battleshipPage.getCurrentUrl().toLowerCase().contains("battleship")
                        || battleshipPage.pageContains("robot")
                        || battleshipPage.pageContains("Robot"),
                "Depois de clicar em Play vs robot, a página esperada não foi carregada."
        );
    }

    @Test
    public void US04_jogarComUmAmigo() {
        assertTrue(
                battleshipPage.isPlayWithFriendAvailable(),
                "O botão/opção Play with a friend não foi encontrado."
        );

        battleshipPage.clickPlayWithFriend();

        assertTrue(
                battleshipPage.getCurrentUrl().toLowerCase().contains("battleship")
                        || battleshipPage.pageContains("friend")
                        || battleshipPage.pageContains("Friend")
                        || battleshipPage.pageContains("Invite")
                        || battleshipPage.pageContains("invite"),
                "Depois de clicar em Play with a friend, a página esperada não foi carregada."
        );
    }
}