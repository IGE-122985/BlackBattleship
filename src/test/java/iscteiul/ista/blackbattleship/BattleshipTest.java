package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

public class BattleshipTest {
    private WebDriver driver;
    private BattleshipPage battleshipPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://papergames.io/en/battleship");

        // Nova estratégia: Procurar e clicar no botão de "Consent"
        try {
            // Damos até 5 segundos para o banner aparecer
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Procura um botão que contenha a palavra "Consent" (ignorando maiúsculas/minúsculas)
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'consent')]")
            ));

            consentButton.click();
            System.out.println("Sucesso: Botão de Consentimento aceite!");

            // Pausa rápida para deixar a animação do banner desaparecer do ecrã
            Thread.sleep(1000);

        } catch (TimeoutException e) {
            System.out.println("Info: Banner de consentimento não apareceu.");
        } catch (Exception e) {
            // Se o clique normal falhar (por vezes outras coisas sobrepõem-se), forçamos com Javascript
            System.out.println("A tentar forçar o clique no consentimento com JS...");
            try {
                WebElement btn = driver.findElement(By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'consent')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            } catch (Exception ex) {
                System.out.println("Não foi possível encontrar o botão de consentimento.");
            }
        }

        battleshipPage = new BattleshipPage(driver);
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
    }
}