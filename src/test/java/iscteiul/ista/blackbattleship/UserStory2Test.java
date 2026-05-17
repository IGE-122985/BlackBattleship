package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Test Class da User Story 2.
 *
 * Testa se o jogador consegue selecionar a opção
 * de jogar com um amigo.
 */
public class UserStory2Test {
    private WebDriver driver;
    private UserStory2 userStory2;

    /**
     * Prepara o browser antes da execução do teste.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        userStory2 = new UserStory2(driver);
        userStory2.abrirPaginaDoJogo();
    }

    /**
     * Fecha o browser depois da execução do teste.
     */
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * UserStoryTest2:
     * Como jogador, quero jogar com um amigo,
     * para poder partilhar uma partida com outro jogador.
     */
    @Test
    public void UserStoryTest2_jogarComUmAmigo() {
        userStory2.escolherJogarComAmigo();

        assertTrue(userStory2.interacaoComOJogoComecou());
    }
}