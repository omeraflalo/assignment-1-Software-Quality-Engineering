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
            listOfProductsToAdd.add(new Product(Integer.toString(i), "p" + Integer.toString(i), (int) (Math.random() * 100) + 1));
        }
        for (Product p : listOfProductsToAdd) {
            addProductWithMock(p, (int)(Math.random()*1000)+1);
        }

    }

    public double getTotalMarketPrice(){
        double sum = 0;
        for(Product product: listOfProductsToAdd){
            sum += product.getQuantity()*supermarket.getPrice(product.productId);
        }
        return sum;
    }

//    @Test
//    public void testAddProduct() {
//        Product p = new Product("a", "a", 2);
//        shoppingList.addProduct(p);
//        assert shoppingList.getMarketPrice() == supermarket.getPrice(p.getId()); //a creative way to make sure the product added
//    }


    @Test
    public void testGetMarketPrice() {
        assert shoppingList.getMarketPrice() == getTotalMarketPrice();
    }

    @Test
    public void testGetDiscount() {

    }

    @Test
    public void testPriceWithDelivery() {

    }

    @Test
    public void testChangeQuantity() {

    }

}
