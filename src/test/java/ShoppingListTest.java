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

    public static void addProductWithMock(Product product, double price) {
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

    public double getTotalMarketPrice() {
        double sum = 0;
        for (Product product : listOfProductsToAdd) {
            sum += product.getQuantity() * supermarket.getPrice(product.productId);
        }
        return sum;
    }

    @Test
    public void testAddProduct() {
        shoppingList.changeQuantity(20, "0");
        assert listOfProductsToAdd.get(0).getQuantity() == 20; //a creative way to make sure the products added
        shoppingList.changeQuantity(1, "0");
    }


    @Test
    public void testGetMarketPrice() {
        String result = "ok";
        assert shoppingList.getMarketPrice() == getTotalMarketPrice() * 0.9;
        Supermarket sm = mock(Supermarket.class);
        ShoppingList sl = new ShoppingList(sm);
        Product p = new Product("a", "a", 1);
        sl.addProduct(p);
        when(sm.getPrice(p.productId)).thenReturn((double)-5);
        try {
            sl.getMarketPrice();
        }
        catch (Exception e){
            result = e.getMessage();
        }
        assert result.equals("Price cannot be negative"); //suppose to be exception because of the function getDiscount(price)

    }

    @Test
    public void testGetDiscount() {
        String result = "";
        //totalMarketPrice is 1 * 16 + 2 * 15 ... 16 * 1 = 816
        assert shoppingList.getDiscount(getTotalMarketPrice()) == 0.9;
        assert shoppingList.getDiscount(1200) == 0.85;
        assert shoppingList.getDiscount(0) == 1;
        try {
            shoppingList.getDiscount(-15);
        } catch (Exception e) {
            result = e.getMessage();
        }
        assert result.equals("Price cannot be negative");
        assert shoppingList.getDiscount(500) == 1;
        assert shoppingList.getDiscount(501) == 0.95;
    }

    @Test
    public void testPriceWithDelivery() {
        String result = "";
        try {
            shoppingList.priceWithDelivery(-3);
        } catch (IllegalArgumentException e) {
            result = e.getMessage();
        }
        assert result.equals("Miles cannot be negative");
        assert shoppingList.priceWithDelivery(5) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(5, listOfProductsToAdd.size());
        assert shoppingList.priceWithDelivery(2) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(2, listOfProductsToAdd.size());
        assert shoppingList.priceWithDelivery(0) == shoppingList.getMarketPrice() + supermarket.calcDeliveryFee(0, listOfProductsToAdd.size());

    }

    @Test
    public void testChangeQuantity() {
        String result = "";
        try{
            shoppingList.changeQuantity(-1, "2");
        }
        catch (IllegalArgumentException e){
            result = e.getMessage();
        }
        assert result.equals("Quantity cannot be negative");
        shoppingList.changeQuantity(5, "2");
        assert listOfProductsToAdd.get(2).getQuantity() == 5;
        try {
            shoppingList.changeQuantity(12, "-1"); // id is not exists, should throw exception
        }
        catch (Exception e){
            return;
        }
        assert false: "The product id=-1 doesn't exist in the shopping list"; // didn't throw exception - no handle in the problem that Product doesn't exist
    }

}
