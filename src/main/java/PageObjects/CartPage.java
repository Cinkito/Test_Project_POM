package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage{
    private WebDriverWait wait;
    public CartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    private By shopTableLocator = By.cssSelector("form>.shop_table");
    private By productQuantityFieldLocator = By.cssSelector("div.quantity>input");
    private String removeProductButtonCssSelector = "a[data-product_id='<product_id>']";
    private By cartItem = By.cssSelector(".cart_item");
    private By updateCartButtonLocator = By.cssSelector("[name='update_cart']");
    private By blockOverlayLocator = By.cssSelector(".blockOverlay");
    private By checkoutButtonLocator = By.cssSelector(".checkout-button");

    public int getProductQuantity() {
        waitForShopTable();
        String quantityString = driver.findElement(productQuantityFieldLocator).getAttribute("value");
        return Integer.parseInt(quantityString);
    }

    public int changeProductQuantity(int quantity) {
        WebElement quantityField = driver.findElement(productQuantityFieldLocator);
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(quantity));
        WebElement updateButton = driver.findElement(updateCartButtonLocator);
        wait.until(ExpectedConditions.elementToBeClickable(updateButton));
        updateButton.click();
        return getProductQuantity();
    }

    public boolean isProductInCart(String productId) {
        waitForShopTable();
        By removeProductLocator = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
        int productRecords = driver.findElements(removeProductLocator).size();
        boolean presenceOfProduct = false;
        if (productRecords == 1){
            presenceOfProduct = true;
        } else if (productRecords>1) {
            throw  new IllegalArgumentException("There is more than one record for the product in cart.");
        }
        return presenceOfProduct;
    }

    public int getNumberOfProducts() {
        waitForShopTable();
        return driver.findElements(cartItem).size();
    }

    public CartPage removeProduct(String productId) {
        driver.findElement(By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(blockOverlayLocator));
        return this;
    }

    public boolean isCartEmpty() {
        int shopTableElements = driver.findElements(shopTableLocator).size();
        if (shopTableElements == 1){
            return false;
        } else if (shopTableElements == 0){
            return true;
        } else {
            throw new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none");
        }
    }

    public CheckoutPage goToCheckout() {
        driver.findElement(checkoutButtonLocator).click();
        return new CheckoutPage(driver);
    }

    private void waitForShopTable(){
        WebDriverWait wait = new WebDriverWait(driver, 7);
        wait.until(ExpectedConditions.presenceOfElementLocated(shopTableLocator));
    }


}
