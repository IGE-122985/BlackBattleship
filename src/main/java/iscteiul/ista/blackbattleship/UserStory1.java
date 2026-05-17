package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

/**
 * Page Object Class da User Story 1.
 *
 * Representa o cenário em que o jogador consulta a página do jogo
 * e verifica se as instruções/regras da batalha naval estão disponíveis.
 */
public class UserStory1 extends BattleshipPage {

    /**
     * Cria o Page Object da User Story 1.
     *
     * @param driver instância do WebDriver usada para controlar o browser
     */
    public UserStory1(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre a página da batalha naval.
     */
    public void abrirPaginaDoJogo() {
        open();
    }

    /**
     * Verifica se a página da batalha naval foi carregada.
     *
     * @return true se a página da batalha naval estiver visível
     */
    public boolean paginaDaBatalhaNavalEstaVisivel() {
        return isBattleshipPageVisible();
    }

    /**
     * Verifica se as instruções/regras do jogo estão visíveis.
     *
     * @return true se as instruções do jogo estiverem disponíveis
     */
    public boolean instrucoesDoJogoEstaoVisiveis() {
        return areRulesVisible();
    }
}