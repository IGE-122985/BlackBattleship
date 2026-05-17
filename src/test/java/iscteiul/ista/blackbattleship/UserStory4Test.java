package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Test Class da User Story 4.
 *
 * Testa se o jogador/organizador consegue selecionar
 * a opção de criar um torneio.
 */
public class UserStory4Test {
    private WebDriver driver;
    private UserStory4 userStory4;

    /**
     * Prepara o browser antes da execução do teste.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        userStory4 = new UserStory4(driver);
        userStory4.abrirPaginaDoJogo();
    }

    /**
     * Fecha o browser depois da execução do teste.
     */
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * UserStoryTest4:
     * Como jogador/organizador, quero criar um torneio,
     * para organizar uma competição de batalha naval.
     */
    @Test
    public void UserStoryTest4_criarTorneio() {
        userStory4.escolherCriarTorneio();

        assertTrue(userStory4.paginaOuDialogoDeTorneioEstaVisivel());
    }
}