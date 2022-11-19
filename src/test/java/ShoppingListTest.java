import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sise.sqe.ShoppingList;
import sise.sqe.Supermarket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingListTest {
    @Test
    public void testShoppingListConstructor() {
        Supermarket supermarket = Mockito.mock(Supermarket.class);
        assertNotNull(new ShoppingList(supermarket));
    }

    @Test
    public void testAddProduct() {

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
