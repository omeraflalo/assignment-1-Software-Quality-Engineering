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
            listOfProductsToAdd.add(new Product(Integer.toString(i), "p" + Integer.toString(i), (int)(Math.random()*100)+1));
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
