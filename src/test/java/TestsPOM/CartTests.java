package TestsPOM;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseTest {

    String productId = "386";
    String productURL = "https://fakestore.testelka.pl/product/egipt-el-gouna/";
    String categoryURL = "https://fakestore.testelka.pl/product-category/windsurfing/";
    String[] productPages = {"/egipt-el-gouna/","/wspinaczka-via-ferraty/","/wspinaczka-island-peak/",
            "/fuerteventura-sotavento/", "/grecja-limnos/", "/windsurfing-w-karpathos/",
            "/wyspy-zielonego-przyladka-sal/", "/wakacje-z-yoga-w-kraju-kwitnacej-wisni/",
            "/wczasy-relaksacyjne-z-yoga-w-toskanii/", "/yoga-i-pilates-w-hiszpanii/"};
    int expectedQuantity = 8;

    @Test
    public void addToCartFromProductPageTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productURL);
        productPage.footer.closeDemoNotice();
        boolean isProductInCart = productPage.addToCart().viewCart().isProductInCart(productId);
        assertTrue(isProductInCart,"Product was not found in cart.");
    }

    @Test
    public void addToCartFromCategoryPageTest() {
        CategoryPage categoryPage = new CategoryPage(driver).goTo(categoryURL);
        categoryPage.footer.closeDemoNotice();
        boolean isProductInCart = categoryPage.addToCart(productId).viewCart().isProductInCart(productId);
        assertTrue(isProductInCart,"Product was not found in cart.");
    }

    @Test
    public void addOneProductTenTimesTest() {
        ProductPage productPage = new ProductPage(driver).goTo(productURL);
        productPage.footer.closeDemoNotice();
        int productQuantity = productPage.addToCart(10).viewCart().getProductQuantity();
        assertEquals(productQuantity, 10, "Product quantity does not equal expected value.");
    }

    @Test
    public void addTenProductsToCartTest(){
        ProductPage productPage = new ProductPage(driver);
        for (String product: productPages) {
            productPage.goTo("https://fakestore.testelka.pl/product" + product).addToCart();
        }
        int numberOfItems = productPage.header.viewCart().getNumberOfProducts();
        assertEquals(10, numberOfItems,
                "Number of items in the cart is not correct. Expected: 10, but was: " + numberOfItems);
    }

    @Test
    public void changeNumberOfProductsTest(){
        ProductPage productPage = new ProductPage(driver).goTo(productURL);
        productPage.footer.closeDemoNotice();
        int quantity = productPage.addToCart().viewCart().changeProductQuantity(expectedQuantity);
        assertEquals(expectedQuantity, quantity,
                "Quantity of the product is not what expected. Expected:" + expectedQuantity + ", but was " + quantity);
    }

    @Test
    public void removePositionFromCartTest(){
        ProductPage productPage = new ProductPage(driver).goTo(productURL);
        productPage.footer.closeDemoNotice();
        boolean isCartEmpty = productPage.addToCart().viewCart().removeProduct(productId).isCartEmpty();
        assertTrue(isCartEmpty,"Cart is not empty");
    }
}
