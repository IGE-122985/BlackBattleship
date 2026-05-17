package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

/**
 * Page Object Class da User Story 3.
 *
 * Representa o cenário em que o jogador escolhe jogar
 * contra um robot.
 */
public class UserStory3 extends BattleshipPage {

    /**
     * Cria o Page Object da User Story 3.
     *
     * @param driver instância do WebDriver usada para controlar o browser
     */
    public UserStory3(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre a página da batalha naval.
     */
    public void abrirPaginaDoJogo() {
        open();
    }

    /**
     * Seleciona a opção de jogar contra o robot.
     */
    public void escolherJogarContraRobot() {
        playVsRobot();
    }

    /**
     * Verifica se a interação com o jogo começou após selecionar a opção.
     *
     * @return true se a página apresentar sinais de início/configuração do jogo
     */
    public boolean interacaoComOJogoComecou() {
        return hasGameInteractionStarted();
    }
}