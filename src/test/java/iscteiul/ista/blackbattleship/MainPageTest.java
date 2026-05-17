package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

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
    public void search() {
        mainPage.searchFor("Selenium");

        String url = mainPage.getCurrentUrl();
        String title = mainPage.getTitle();

        assertTrue(
                url.contains("jetbrains.com") || title.contains("JetBrains"),
                "URL atual: " + url + " | Título atual: " + title
        );
    }

    @Test
    public void toolsMenu() {
        mainPage.openHomePage();

        assertTrue(mainPage.getCurrentUrl().contains("jetbrains.com"));
    }

    @Test
    public void navigationToAllTools() {
        mainPage.openDeveloperToolsPage();

        assertTrue(mainPage.getCurrentUrl().contains("products"));
        assertTrue(mainPage.getTitle().contains("JetBrains"));
    }
}