import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sise.sqe.Product;
import sise.sqe.ShoppingList;
import sise.sqe.Supermarket;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ShoppingListTest {
    @Test
    public void testShoppingListConstructor() {
        Supermarket supermarket = Mockito.mock(Supermarket.class);
        assertNotNull(new ShoppingList(supermarket));
    }

    @Test
    public void testAddProduct() {
        ShoppingList shoppingList = new ShoppingList(mock(Supermarket.class));
        Product p = new Product("a","a",2);
        shoppingList = spy(shoppingList);
        shoppingList.addProduct(p);
        verify(shoppingList).addProduct(p);
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
