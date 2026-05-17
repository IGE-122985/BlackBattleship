package iscteiul.ista.blackbattleship.tournamentsuite;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Test Class da test suite de torneios e modos alternativos.
 *
 * Esta classe contém testes de aceitação implementados com Selenide,
 * JUnit 5, Allure e Page Factory.
 */
@Epic("Battleship Online")
@Feature("Torneios e modos alternativos")
public class TournamentSuiteTest {
    private TournamentSuitePage tournamentPage;

    /**
     * Configura o browser e inicializa a Page Object Class antes de cada teste.
     */
    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;

        tournamentPage = page(TournamentSuitePage.class);
        tournamentPage.openBattleshipPage();
    }

    /**
     * Fecha o browser depois de cada teste.
     */
    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    /**
     * US01:
     * Como jogador/organizador, quero aceder à opção de criação de torneio,
     * para poder organizar uma competição de Battleship.
     */
    @Test
    @Story("US01 - Aceder à opção de criação de torneio")
    @Description("Verifica se a opção Create tournament está visível na página Battleship Online.")
    public void UserStoryTest1_verOpcaoCriarTorneio() {
        assertTrue(tournamentPage.isCreateTournamentOptionVisible());
    }

    /**
     * US02:
     * Como jogador/organizador, quero abrir o fluxo de criação de torneio,
     * para preencher os dados necessários à competição.
     */
    @Test
    @Story("US02 - Abrir fluxo de criação de torneio")
    @Description("Seleciona Create tournament e verifica se aparece uma página ou formulário relacionado com torneios.")
    public void UserStoryTest2_abrirCriacaoDeTorneio() {
        tournamentPage.openCreateTournamentFlow();

        assertTrue(tournamentPage.isTournamentFlowVisible());
    }

    /**
     * US03:
     * Como jogador, quero aceder à opção de jogo online com jogador aleatório,
     * para poder jogar sem escolher diretamente um adversário.
     */
    @Test
    @Story("US03 - Ver opção de jogador aleatório")
    @Description("Verifica se a opção Play online with a random player está disponível.")
    public void UserStoryTest3_verOpcaoJogadorAleatorio() {
        assertTrue(tournamentPage.isRandomPlayerOptionVisible());
    }

    /**
     * US04:
     * Como jogador, quero iniciar o fluxo de jogo com jogador aleatório,
     * para confirmar que o modo online está funcional.
     */
    @Test
    @Story("US04 - Iniciar fluxo de jogador aleatório")
    @Description("Seleciona Play online with a random player e verifica se o fluxo de jogo foi iniciado.")
    public void UserStoryTest4_abrirFluxoJogadorAleatorio() {
        tournamentPage.openRandomPlayerFlow();

        assertTrue(tournamentPage.isRandomPlayerFlowVisible());
    }
}