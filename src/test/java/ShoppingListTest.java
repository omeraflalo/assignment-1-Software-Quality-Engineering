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

    public void addProductWithMock(Supermarket supermarket,ShoppingList shoppingList, Product product, double price){
        shoppingList.addProduct(product);
        when(supermarket.getPrice(product.productId)).thenReturn(price);
    }

    @Test
    public void testGetMarketPrice() {
        Supermarket supermarket = mock(Supermarket.class);
        ShoppingList shoppingList = new ShoppingList(supermarket);
        Product p = new Product("a","a",2);
        addProductWithMock(supermarket,shoppingList,p,8);

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
