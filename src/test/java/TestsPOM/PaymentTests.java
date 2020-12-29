package TestsPOM;

import PageObjects.CartPage;
import PageObjects.CheckoutPage;
import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTests extends BaseTest{
    private String productURL = "https://fakestore.testelka.pl/product/egipt-el-gouna/";
    private String name = "Gandalf";
    private String lastName = "Rudy";
    private String country = "PL";
    private String address = "Cool 4/4";
    private String postalCode = "44-555";
    private String city = "Nice";
    private String phone = "555444333";
    private String emailAddress = "testaccount123test@test.test";
    private String cardNumber = "4242424242424242";
    private String cardExpirationDate = "1224";
    private String cardCVC = "456";


    @Test
    public void buyWithoutAccountTest(){
        CheckoutPage checkoutPage = addProductToCartAndGoToCheckoutPage();
        OrderReceivedPage orderReceivedPage = checkoutPage.typeName(name)
                .typeLastName(lastName)
                .chooseCountry(country)
                .typeAddress(address)
                .typePostalCode(postalCode)
                .typeCity(city)
                .typePhone(phone)
                .typeEmail(emailAddress)
                .typeCardNumber(cardNumber)
                .typeCardExpirationDate(cardExpirationDate)
                .typeCardCVC(cardCVC)
                .acceptTerms()
                .order();
        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "Order was not successful");
    }

    public CheckoutPage addProductToCartAndGoToCheckoutPage(){
        ProductPage productPage = new ProductPage(driver).goTo(productURL);
        productPage.footer.closeDemoNotice();
        CartPage cartPage = productPage.addToCart().viewCart();
        return cartPage.goToCheckout();
    }
}
