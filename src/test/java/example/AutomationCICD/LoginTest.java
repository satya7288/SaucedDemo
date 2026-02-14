package example.AutomationCICD;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Pages.LoginPage;
import Pages.InventoryPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeEach
    void setup() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    void validLoginTest() {

        loginPage.login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void invalidLoginTest() {

        loginPage.login("standard_user", "1234");

        assertEquals(
            "Epic sadface: Username and password do not match any user in this service",
            loginPage.getErrorMessage()
        );
    }

    @Test
    void emptyLoginTest() {

        loginPage.clickLogin();

        assertEquals(
            "Epic sadface: Username is required",
            loginPage.getErrorMessage()
        );
    }

    @Test
    void addToCartTest() {

        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstProductToCart();

        assertEquals("1", inventoryPage.getCartBadgeCount());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
