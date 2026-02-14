package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    WebDriver driver;

    private By addToCartBackpack =
            By.id("add-to-cart-sauce-labs-backpack");

    private By cartBadge =
            By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addFirstProductToCart() {
        driver.findElement(addToCartBackpack).click();
    }

    public String getCartBadgeCount() {
        return driver.findElement(cartBadge).getText();
    }
}
