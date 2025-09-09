package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    public void addNonExistingProduct_throwsException() {
        UUID productId = UUID.randomUUID();

        when(storageService.getProductById(productId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(productId));

        verify(storageService, times(1)).getProductById(productId);
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    public void addExistingProduct_callsAddProduct() {
        UUID productId = UUID.randomUUID();
        Product product = mock(Product.class);

        when(storageService.getProductById(productId)).thenReturn(java.util.Optional.of(product));

        basketService.addProductToBasket(productId);

        verify(storageService, times(1)).getProductById(productId);
        verify(productBasket, times(1)).addProduct(productId);
    }

    @Test
    public void getUserBasket_returnsEmptyIfBasketEmpty() {
        when(productBasket.getProducts()).thenReturn(Map.of());

        UserBasket userBasket = basketService.getUserBasket();

        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty());

        verify(productBasket, times(1)).getProducts();
    }

    @Test
    public void getUserBasket_returnsBasketWithItems() {
        UUID productId = UUID.randomUUID();
        Product product = mock(Product.class);
        int quantity = 2;

        when(productBasket.getProducts()).thenReturn(Map.of(productId, quantity));
        when(storageService.getProductById(productId)).thenReturn(java.util.Optional.of(product));

        UserBasket userBasket = basketService.getUserBasket();

        assertNotNull(userBasket);
        assertFalse(userBasket.getItems().isEmpty());
        assertEquals(1, userBasket.getItems().size());

        BasketItem item = userBasket.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(quantity, item.getQuantity());

        verify(productBasket, times(1)).getProducts();
        verify(storageService, times(1)).getProductById(productId);
    }
}