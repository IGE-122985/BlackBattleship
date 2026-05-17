package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Test Class da User Story 3.
 *
 * Testa se o jogador consegue selecionar a opção
 * de jogar contra um robot.
 */
public class UserStory3Test {
    private WebDriver driver;
    private UserStory3 userStory3;

    /**
     * Prepara o browser antes da execução do teste.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        userStory3 = new UserStory3(driver);
        userStory3.abrirPaginaDoJogo();
    }

    /**
     * Fecha o browser depois da execução do teste.
     */
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * UserStoryTest3:
     * Como jogador, quero jogar contra um robot,
     * para poder jogar mesmo sem outro jogador disponível.
     */
    @Test
    public void UserStoryTest3_jogarContraRobot() {
        userStory3.escolherJogarContraRobot();

        assertTrue(userStory3.interacaoComOJogoComecou());
    }
}