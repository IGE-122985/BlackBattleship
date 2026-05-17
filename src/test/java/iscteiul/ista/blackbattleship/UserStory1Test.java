package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Test Class da User Story 1.
 *
 * Testa se o jogador consegue consultar a página do jogo
 * e visualizar as instruções/regras da batalha naval.
 */
public class UserStory1Test {
    private WebDriver driver;
    private UserStory1 userStory1;

    /**
     * Prepara o browser antes da execução do teste.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        userStory1 = new UserStory1(driver);
        userStory1.abrirPaginaDoJogo();
    }

    /**
     * Fecha o browser depois da execução do teste.
     */
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * UserStoryTest1:
     * Como jogador, quero consultar as instruções do jogo,
     * para compreender as regras antes de jogar.
     */
    @Test
    public void UserStoryTest1_consultarInstrucoesDoJogo() {
        assertTrue(userStory1.paginaDaBatalhaNavalEstaVisivel());
        assertTrue(userStory1.instrucoesDoJogoEstaoVisiveis());
    }
}