import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sise.sqe.Product;
import sise.sqe.ShoppingList;
import sise.sqe.Supermarket;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ShoppingListTest {
    private static Supermarket supermarket;
    private static ShoppingList shoppingList;
    private static ArrayList<Product> listOfProductsToAdd = new ArrayList<>();

    public static void addProductWithMock(Product product, double price) { // helper func
        shoppingList.addProduct(product);
        when(supermarket.getPrice(product.productId)).thenReturn(price);
    }

    @BeforeAll
    public static void setUpForTesting() {
        supermarket = mock(Supermarket.class);
        shoppingList = new ShoppingList(supermarket);
        for (int i = 0; i < 16; i++) {
            listOfProductsToAdd.add(new Product(Integer.toString(i), "p" + Integer.toString(i), i + 1));
        }
        for (int i = 0; i < 16; i++) {
            addProductWithMock(listOfProductsToAdd.get(i), listOfProductsToAdd.size() - i);
        }

    }

    public double getTotalMarketPrice() { // helper func
        double sum = 0;
        for (Product product : listOfProductsToAdd) {
            sum += product.getQuantity() * supermarket.getPrice(product.productId);
        }
        return sum;
    }

    @Test
    public void testAddProductToShoppingListSuccess() {
        shoppingList.changeQuantity(20, "0");
        assert listOfProductsToAdd.get(0).getQuantity() == 20; //a creative way to make sure the products added
        shoppingList.changeQuantity(1, "0");
    }

    @Test
    public void testGetMarketPriceEmptyListSuccess(){
        Supermarket sm = mock(Supermarket.class);
        ShoppingList sl = new ShoppingList(sm);
        assert sl.getMarketPrice() == 0 : "GetMarketPrice from shopping list test failed, suppose to be 0, got " + sl.getMarketPrice();
    }

    @Test
    public void testGetMarketPriceWithProductsSuccess() {
        assert shoppingList.getMarketPrice() == getTotalMarketPrice() * 0.9 : "GetMarketPrice from shopping list failed. Expected 734.4, got " + shoppingList.getMarketPrice();
    }
    @Test
    public void testGetMarketPriceWithProductsFail(){
        String result = "";
        Supermarket sm = mock(Supermarket.class);
        ShoppingList sl = new ShoppingList(sm);
        Product p = new Product("a", "a", 1);
        sl.addProduct(p);
        when(sm.getPrice(p.productId)).thenReturn((double) -5);
        try {
            sl.getMarketPrice();
        } catch (Exception e) {
            result = e.getMessage();
        }
        assert result.equals("Price cannot be negative") : "GetMarketPrice from shopping list test failed, can't get discount of negative price"; //suppose to be exception because of the function getDiscount(price)

    }

    @Test
    public void testGetDiscountPriceBelow500Success() {
        assert shoppingList.getDiscount(0) == 1 : "getDiscount failed. Expected 1, got " + shoppingList.getDiscount(0);
        assert shoppingList.getDiscount(500) == 1 : "getDiscount failed. Expected 1, got " + shoppingList.getDiscount(500);
        assert shoppingList.getDiscount(35) == 1 : "getDiscount failed. Expected 1, got " + shoppingList.getDiscount(35);
    }
    @Test
    public void testGetDiscountPriceBelow750Success() {
        assert shoppingList.getDiscount(501) == 0.95 : "getDiscount failed. Expected 0.95, got " + shoppingList.getDiscount(501);
        assert shoppingList.getDiscount(750) == 0.95 : "getDiscount failed. Expected 0.95, got " + shoppingList.getDiscount(750);
        assert shoppingList.getDiscount(666) == 0.95 : "getDiscount failed. Expected 0.95, got " + shoppingList.getDiscount(666);
    }
    @Test
    public void testGetDiscountPriceBelow1000Success() {
        assert shoppingList.getDiscount(1000) == 0.9 : "getDiscount failed. Expected 0.9, got " + shoppingList.getDiscount(1000);
        assert shoppingList.getDiscount(751) == 0.9 : "getDiscount failed. Expected 0.9, got " + shoppingList.getDiscount(751);
        //totalMarketPrice is 1 * 16 + 2 * 15 ... 16 * 1 = 816
        assert shoppingList.getDiscount(getTotalMarketPrice()) == 0.9 : "getDiscount failed. Expected 0.9, got " + shoppingList.getDiscount(816);
    }
    @Test
    public void testGetDiscountPriceAbove1000Success() {
        assert shoppingList.getDiscount(1200) == 0.85 : "getDiscount failed. Expected 0.85, got " + shoppingList.getDiscount(1200);
        assert shoppingList.getDiscount(1001) == 0.85 : "getDiscount failed. Expected 0.85, got " + shoppingList.getDiscount(1001);
        assert shoppingList.getDiscount(141892) == 0.85 : "getDiscount failed. Expected 0.85, got " + shoppingList.getDiscount(141892);
    }
    @Test
    public void testGetDiscountNegativePriceFail() {
        String result = "";
        try {
            shoppingList.getDiscount(-15);
        } catch (Exception e) {
            result = e.getMessage();
        }
        assert result.equals("Price cannot be negative") : "getDiscount failed,  can't receive negative number";
    }

    @Test
    public void testPriceWithDeliveryNegativeMilesFail() {
        String result = "";
        try {
            shoppingList.priceWithDelivery(-3);
        } catch (IllegalArgumentException e) {
            result = e.getMessage();
        }
        assert result.equals("Miles cannot be negative") : "priceWithDelivery failed, can't receive negative number";
    }
    @Test
    public void testPriceWithDeliveryPositiveMilesSuccess() {
        assert shoppingList.priceWithDelivery(5) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(5, listOfProductsToAdd.size()) : "priceWithDelivery failed";
        assert shoppingList.priceWithDelivery(2) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(2, listOfProductsToAdd.size()) : "priceWithDelivery failed";
        assert shoppingList.priceWithDelivery(0) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(0, listOfProductsToAdd.size()) : "priceWithDelivery failed";

    }

    @Test
    public void testChangeQuantityNegativeQuantityFail() {
        String result = "";
        try {
            shoppingList.changeQuantity(-1, "2");
        } catch (IllegalArgumentException e) {
            result = e.getMessage();
        }
        assert result.equals("Quantity cannot be negative") : "changeQuantity failed, can't receive negative number";
    }
    @Test
    public void testChangeQuantityPositiveQuantitySuccess() {
        shoppingList.changeQuantity(5, "2");
        shoppingList.changeQuantity(20, "1");
        assert listOfProductsToAdd.get(2).getQuantity() == 5 : "changeQuantity failed, Expected 5, got " + listOfProductsToAdd.get(2).getQuantity();
        assert listOfProductsToAdd.get(1).getQuantity() == 20 : "changeQuantity failed, Expected 20, got " + listOfProductsToAdd.get(1).getQuantity();
        // change back their quantity
        shoppingList.changeQuantity(3, "2");
        shoppingList.changeQuantity(2, "1");
    }

}
