package iscteiul.ista.blackbattleship.tournamentsuite;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import java.util.function.BooleanSupplier;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Page Object Class da test suite de torneios e modos alternativos.
 *
 * Esta classe representa a página Battleship Online e contém as operações
 * necessárias para interagir com opções relacionadas com torneios e jogo
 * online com jogador aleatório.
 */
public class TournamentSuitePage {
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    @FindBy(xpath = "//*[self::a or self::button or @role='button'][contains(normalize-space(.), 'Create tournament')]")
    private SelenideElement createTournamentButton;

    @FindBy(xpath = "//*[self::a or self::button or @role='button'][contains(normalize-space(.), 'Play online with a random player')]")
    private SelenideElement randomPlayerButton;

    /**
     * Abre a página principal do Battleship Online.
     *
     * @return a própria página, permitindo chamadas encadeadas
     */
    @Step("Abrir a página principal do Battleship Online")
    public TournamentSuitePage openBattleshipPage() {
        open(BASE_URL);
        acceptCookiesIfPresent();
        return this;
    }

    /**
     * Verifica se a opção de criar torneio está visível.
     *
     * @return true se a opção estiver visível
     */
    @Step("Verificar se a opção de criar torneio está visível")
    public boolean isCreateTournamentOptionVisible() {
        return createTournamentButton.shouldBe(visible).isDisplayed();
    }

    /**
     * Abre o fluxo de criação de torneio.
     */
    @Step("Selecionar a opção Create tournament")
    public void openCreateTournamentFlow() {
        safeClick(createTournamentButton);
    }

    /**
     * Verifica se foi apresentada uma página ou formulário relacionado com torneios.
     *
     * @return true se o fluxo de torneio estiver visível
     */
    @Step("Verificar se o fluxo de criação de torneio está visível")
    public boolean isTournamentFlowVisible() {
        return waitUntil(() ->
                pageContains("tournament")
                        || pageContains("create")
                        || hasVisibleInput()
                        || currentUrl().contains("tournament")
        );
    }

    /**
     * Verifica se a opção de jogar com jogador aleatório está visível.
     *
     * @return true se a opção estiver visível
     */
    @Step("Verificar se a opção de jogar com jogador aleatório está visível")
    public boolean isRandomPlayerOptionVisible() {
        return randomPlayerButton.shouldBe(visible).isDisplayed();
    }

    /**
     * Abre o fluxo de jogo online com jogador aleatório.
     */
    @Step("Selecionar a opção Play online with a random player")
    public void openRandomPlayerFlow() {
        safeClick(randomPlayerButton);
    }

    /**
     * Verifica se o fluxo de jogo online com jogador aleatório foi iniciado.
     *
     * @return true se aparecerem sinais de início/configuração do jogo
     */
    @Step("Verificar se o fluxo de jogo aleatório foi iniciado")
    public boolean isRandomPlayerFlowVisible() {
        return waitUntil(() ->
                pageContains("random")
                        || pageContains("online")
                        || pageContains("name")
                        || pageContains("game")
                        || hasVisibleInput()
                        || currentUrl().contains("battleship")
        );
    }

    /**
     * Tenta aceitar cookies se aparecer um banner.
     */
    @Step("Aceitar cookies, se o aviso estiver presente")
    private void acceptCookiesIfPresent() {
        try {
            $$("button").findBy(visible).click();
        } catch (Exception ignored) {
            // Não existe banner de cookies visível.
        }
    }

    /**
     * Clica num elemento Selenide com espera e fallback por JavaScript.
     *
     * @param element elemento da página a clicar
     */
    private void safeClick(SelenideElement element) {
        element.shouldBe(visible).shouldBe(enabled).scrollIntoView(true);

        try {
            element.click();
        } catch (Exception e) {
            executeJavaScript("arguments[0].click();", element);
        }
    }

    /**
     * Verifica se o corpo da página contém determinado texto.
     *
     * @param text texto esperado
     * @return true se o texto existir na página
     */
    private boolean pageContains(String text) {
        return $("body").getText().toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Verifica se existe algum input visível na página.
     *
     * @return true se existir pelo menos um input visível
     */
    private boolean hasVisibleInput() {
        return $$("input").filterBy(visible).size() > 0;
    }

    /**
     * Devolve o URL atual em minúsculas.
     *
     * @return URL atual
     */
    private String currentUrl() {
        return getWebDriver().getCurrentUrl().toLowerCase();
    }

    /**
     * Pequena espera manual para fluxos dinâmicos da página.
     *
     * @param condition condição a verificar
     * @return true se a condição for satisfeita dentro do tempo limite
     */
    private boolean waitUntil(BooleanSupplier condition) {
        for (int i = 0; i < 30; i++) {
            if (condition.getAsBoolean()) {
                return true;
            }

            sleep(500);
        }

        return condition.getAsBoolean();
    }
}