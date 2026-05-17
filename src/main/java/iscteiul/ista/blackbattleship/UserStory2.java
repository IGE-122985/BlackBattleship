package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

/**
 * Page Object Class da User Story 2.
 *
 * Representa o cenário em que o jogador escolhe a opção
 * de jogar com um amigo.
 */
public class UserStory2 extends BattleshipPage {

    /**
     * Cria o Page Object da User Story 2.
     *
     * @param driver instância do WebDriver usada para controlar o browser
     */
    public UserStory2(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre a página da batalha naval.
     */
    public void abrirPaginaDoJogo() {
        open();
    }

    /**
     * Seleciona a opção de jogar com um amigo.
     */
    public void escolherJogarComAmigo() {
        playWithFriend();
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