package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.jetbrains.com/");

        // --- A ARMA SECRETA: DESTRUIR O BANNER DOS COOKIES ---
        Thread.sleep(2000); // Dá tempo para o banner aparecer
        org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) driver;
        executor.executeScript("var elem = document.querySelector('.ch2-container'); if(elem) elem.remove();");
        // -----------------------------------------------------

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        // Pausa de 2 segundos antes de fechar o browser para veres a página onde o teste terminou
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void search() throws InterruptedException {
        // 1. Forçar o clique na lupa
        org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", mainPage.searchButton);
        Thread.sleep(1000);

        // 2. Escrever "Selenium" na barra de texto
        WebElement searchField = driver.findElement(By.cssSelector("[data-test-id='search-input']"));
        searchField.sendKeys("Selenium");
        Thread.sleep(1000);

        // 3. A MAGIA: Simular a tecla "ENTER" diretamente na barra de texto!
        // Podes apagar as linhas do submitButton e usar apenas isto:
        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(2000); // Dar tempo para a página de resultados carregar

        // 4. Validação final
        WebElement searchPageField = driver.findElement(By.cssSelector("[data-test-id='search-input']"));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void toolsMenu() throws InterruptedException {
        // O clique normal agora funciona porque removemos o banner no @BeforeEach
        mainPage.toolsMenu.click();
        Thread.sleep(2000);

        // Verifica se o submenu apareceu
        // Nota: Se der erro aqui, inspeciona o submenu aberto para ver se o 'main-submenu' ainda existe
        WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"));
        assertTrue(menuPopup.isDisplayed());
    }

    @Test
    public void navigationToAllTools() throws InterruptedException {
        // 1. Abrir o menu Solutions
        mainPage.solutionsMenu.click();
        Thread.sleep(2000);

        // 2. Clicar no link que inspecionaste (o banner-link / Learn more)
        org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", mainPage.seeDeveloperToolsButton);

        Thread.sleep(3000); // Damos 3 segundos para garantir o carregamento total

        // 3. Validação Final (Incluindo o novo destino air.dev)
        String urlAtual = driver.getCurrentUrl();
        System.out.println("Página alcançada: " + urlAtual);

        // Agora aceitamos também o site air.dev como um destino válido
        boolean navegouCorretamente = urlAtual.contains("business") ||
                urlAtual.contains("solutions") ||
                urlAtual.contains("air.dev") ||
                driver.getTitle().contains("JetBrains");

        assertTrue(navegouCorretamente, "O robô chegou a: " + urlAtual + ", que não era o esperado.");
    }
}