package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;

/**
 * Page Object Class da User Story 4.
 *
 * Representa o cenário em que o jogador/organizador
 * escolhe criar um torneio.
 */
public class UserStory4 extends BattleshipPage {

    /**
     * Cria o Page Object da User Story 4.
     *
     * @param driver instância do WebDriver usada para controlar o browser
     */
    public UserStory4(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre a página da batalha naval.
     */
    public void abrirPaginaDoJogo() {
        open();
    }

    /**
     * Seleciona a opção de criar torneio.
     */
    public void escolherCriarTorneio() {
        createTournament();
    }

    /**
     * Verifica se apareceu uma página ou diálogo relacionado com torneios.
     *
     * @return true se a página ou diálogo de torneio estiver visível
     */
    public boolean paginaOuDialogoDeTorneioEstaVisivel() {
        return isTournamentPageOrDialogVisible();
    }
}