package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
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