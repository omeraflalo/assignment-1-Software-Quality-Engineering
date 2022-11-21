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
            listOfProductsToAdd.add(new Product(Integer.toString(i), "p" + Integer.toString(i), i+1));
        }
        for (int i = 0; i < 16; i++) {
            addProductWithMock(listOfProductsToAdd.get(i), listOfProductsToAdd.size()-i);
        }

    }

    public double getTotalMarketPrice(){
        double sum = 0;
        for(Product product: listOfProductsToAdd){
            sum += product.getQuantity()*supermarket.getPrice(product.productId);
        }
        return sum;
    }

    @Test
    public void testAddProduct() {
        shoppingList.changeQuantity(20, "0");
        assert listOfProductsToAdd.get(0).getQuantity() == 20; //a creative way to make sure the product added
        shoppingList.changeQuantity(1, "0");
    }


    @Test
    public void testGetMarketPrice() {
        assert shoppingList.getMarketPrice() == getTotalMarketPrice()*0.9;
    }

    @Test
    public void testGetDiscount() {
        //totalMarketPrice is 1 * 16 + 2 * 15 ... 16 * 1 = 816
        System.out.println(shoppingList.getMarketPrice());
        assert shoppingList.getDiscount(shoppingList.getMarketPrice()) == 0.95;
        Product p = new Product("a", "a", 1);
        shoppingList.addProduct(p);
        when(supermarket.getPrice(p.productId)).thenReturn((double)200);
        assert shoppingList.getDiscount(shoppingList.getMarketPrice()) == 0.85; // now totalMarketPrice > 1000
        // check extreme cases
        Supermarket sm = mock(Supermarket.class);
        ShoppingList sl = new ShoppingList(supermarket);
        sl.addProduct(p);
        when(sm.getPrice(p.productId)).thenReturn((double)0);
        assert shoppingList.getDiscount(shoppingList.getMarketPrice()) == 1;
        when(sm.getPrice(p.productId)).thenReturn((double)-15);
        try {
            shoppingList.getDiscount(shoppingList.getMarketPrice());
        }
        catch (Exception e){
            assert e.getMessage().equals("Price cannot be negative");
        }
        when(sm.getPrice(p.productId)).thenReturn((double)500);
        assert shoppingList.getDiscount(shoppingList.getMarketPrice()) == 1;
        when(sm.getPrice(p.productId)).thenReturn((double)501);
        assert shoppingList.getDiscount(shoppingList.getMarketPrice()) == 0.95;
    }

    @Test
    public void testPriceWithDelivery() {

    }

    @Test
    public void testChangeQuantity() {

    }

}
