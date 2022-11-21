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
    private Supermarket sm;
    private ShoppingList shoppingList;
    private ArrayList<Product> listOfProductsToAdd;

    @BeforeAll
    public void setUpForTesting(){
        sm = mock(Supermarket.class);
        shoppingList = new ShoppingList(sm);
        for (int i =0;i<16;i++){
            listOfProductsToAdd.add(new Product(Integer.toString(i), "p" + Integer.toString(i), i));
        }

    }

    @Test
    public void testAddProduct() {
        Product p = new Product("a","a",2);
        shoppingList = spy(shoppingList);
        shoppingList.addProduct(p);
        verify(shoppingList).addProduct(p);
        assert shoppingList.getMarketPrice() == sm.getPrice(p.getId()); //a creative way to make sure the product added
    }

    @Test
    public void testGetMarketPrice() {

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
