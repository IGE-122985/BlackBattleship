package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

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
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("UserStoryTest5: Procurar adversário online")
    public void testSearchOnlineAdversary() {
        // 1. Clicar no botão para jogar
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", battleshipPage.playOnlineButton);

        // 2. Esperar que o botão "Cancel" apareça
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.visibilityOf(battleshipPage.cancelQueueButton));
            assertTrue(battleshipPage.cancelQueueButton.isDisplayed(), "O botão Cancel apareceu!");

            // Opcional: Clica no Cancel para voltar ao menu e deixar o browser limpo para o próximo teste
            battleshipPage.cancelQueueButton.click();

        } catch (TimeoutException e) {
            fail("O botão 'Cancel' não apareceu. O robô não entrou na fila ou o seletor está errado.");
        }
    }

    @Test
    @DisplayName("US06: Criar torneio")
    public void testCreateTournament() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Clicar em Tournaments
        wait.until(ExpectedConditions.elementToBeClickable(battleshipPage.tournamentsMenu)).click();

        // 2. Clicar no dropdown do Jogo para abrir as opções
        wait.until(ExpectedConditions.elementToBeClickable(battleshipPage.gameDropdown)).click();

        // 3. AGORA sim, clicar na opção 'Battleship' (que já não usa o ID dinâmico)
        wait.until(ExpectedConditions.elementToBeClickable(battleshipPage.battleshipOption)).click();

        wait.until(ExpectedConditions.visibilityOf(battleshipPage.tournamentNameInput));
        battleshipPage.tournamentNameInput.clear();
        // Mandamos escrever este texto:
        battleshipPage.tournamentNameInput.sendKeys("Torneio do Grupo BlackBattleship");

        assertEquals("Torneio do Grupo BlackBattleship",
                battleshipPage.tournamentNameInput.getAttribute("value"),
                "O título do torneio não foi preenchido corretamente.");
        // 2. Clicar em Create
        wait.until(ExpectedConditions.elementToBeClickable(battleshipPage.createButton)).click();
    }

    @Test
    @DisplayName("US07: Visualizar ranking diário")
    public void testViewDailyLeaderboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. O ranking está na página principal, por isso só precisamos de esperar que ele carregue
        wait.until(ExpectedConditions.visibilityOf(battleshipPage.dailyLeaderboardLabel));

        // 2. Validar se o cabeçalho/rodapé do ranking está visível
        assertTrue(battleshipPage.dailyLeaderboardLabel.isDisplayed(),
                "O painel do ranking diário não está visível na página principal.");

        wait.until(ExpectedConditions.elementToBeClickable(battleshipPage.seeAll)).click();
    }

    @Test
    @DisplayName("US08: Consultar jogos semelhantes (Navegar para Home)")
    public void testNavigateToHomeGames() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Esperar que o botão Home esteja visível
        wait.until(ExpectedConditions.visibilityOf(battleshipPage.homeMenuButton));

        // 2. Clicar no botão (usando o nosso truque do JS para garantir que não há interceções)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", battleshipPage.homeMenuButton);

        // 3. Validação: Verificar que o URL do site mudou (saiu da página '/battleship')
        try {
            // Espera até que o URL DEIXE de conter a palavra "battleship"
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("battleship")));

            // Se chegou aqui sem dar Timeout, significa que mudou de página com sucesso!
            System.out.println("US08: Navegou para a página principal com sucesso. URL: " + driver.getCurrentUrl());
            assertTrue(true);

        } catch (TimeoutException e) {
            fail("O botão Home foi clicado, mas a página não mudou. URL atual: " + driver.getCurrentUrl());
        }
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