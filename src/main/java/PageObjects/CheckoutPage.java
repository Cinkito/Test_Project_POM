package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage{
    private WebDriverWait wait;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
    }

    private By countryCodeArrowLocator = By.cssSelector(".select2-selection__arrow");
    private String countryCodeCssSelector = "li[id*='-<country_code>']";
    private By firstNameFieldLocator = By.cssSelector("#billing_first_name");
    private By lastNameFieldLocator = By.cssSelector("#billing_last_name");
    private By addressFieldLocator = By.cssSelector("#billing_address_1");
    private By postalCodeFieldLocator = By.cssSelector("#billing_postcode");
    private By cityFieldLocator = By.cssSelector("#billing_city");
    private By phoneFieldLocator = By.cssSelector("#billing_phone");
    private By emailFieldLocator = By.cssSelector("#billing_email");
    private By loadingIconLocator = By.cssSelector(".blockOverlay");
    private By cardNumberFrameLocator = By.cssSelector("div#stripe-card-element iframe");
    private By cardNumberFieldLocator = By.cssSelector("[name='cardnumber']");
    private By expirationDateFrameLocator = By.cssSelector("div#stripe-exp-element iframe");
    private By expirationDateFieldLocator = By.cssSelector("[name='exp-date']");
    private By cvcFrameLocator = By.cssSelector("div#stripe-cvc-element iframe");
    private By cvcFieldLocator = By.cssSelector("[name='cvc']");
    private By termsLocator = By.cssSelector("#terms");
    private By orderButtonLocator = By.cssSelector("#place_order");

    public CheckoutPage typeName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameFieldLocator)).sendKeys(name);
        return this;
    }

    public CheckoutPage typeLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(lastNameFieldLocator)).sendKeys(lastName);
        return this;
    }

    public CheckoutPage chooseCountry(String countryCode) {
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeArrowLocator)).click();
        By countryCodeLocator = By.cssSelector(countryCodeCssSelector.replace("<country_code>", countryCode));
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeLocator)).click();
        return this;
    }

    public CheckoutPage typeAddress(String address) {
        wait.until(ExpectedConditions.elementToBeClickable(addressFieldLocator)).sendKeys(address);
        return this;
    }

    public CheckoutPage typePostalCode(String postalCode) {
        wait.until(ExpectedConditions.elementToBeClickable(postalCodeFieldLocator)).sendKeys(postalCode);
        return this;
    }

    public CheckoutPage typeCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(cityFieldLocator)).sendKeys(city);
        return this;
    }

    public CheckoutPage typePhone(String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(phoneFieldLocator)).sendKeys(phone);
        return this;
    }

    public CheckoutPage typeEmail(String emailAddress) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator)).sendKeys(emailAddress);
        return this;
    }

    public CheckoutPage typeCardNumber(String cardNumber) {
        findElementInFrame(cardNumberFrameLocator, cardNumberFieldLocator).sendKeys(cardNumber);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardExpirationDate(String cardExpirationDate) {
        findElementInFrame(expirationDateFrameLocator, expirationDateFieldLocator).sendKeys(cardExpirationDate);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardCVC(String cardCVC) {
        findElementInFrame(cvcFrameLocator, cvcFieldLocator).sendKeys(cardCVC);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage acceptTerms() {
        wait.until(ExpectedConditions.elementToBeClickable(termsLocator)).click();
        return this;
    }

    public OrderReceivedPage order() {
        driver.findElement(orderButtonLocator).click();
        return new OrderReceivedPage(driver);
    }

    private WebElement findElementInFrame(By frameLocator, By elementLocator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        return wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
    }

}
