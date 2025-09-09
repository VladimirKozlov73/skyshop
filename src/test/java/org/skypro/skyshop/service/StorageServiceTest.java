package org.skypro.skyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.article.Article;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Test
    void testGetAllProducts() {
        Collection<Product> products = storageService.getAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void testGetAllArticles() {
        Collection<Article> articles = storageService.getAllArticles();
        assertNotNull(articles);
        assertFalse(articles.isEmpty());
    }

    @Test
    void testGetAllSearchables_shouldNotReturnNull() {
        StorageService storageService = new StorageService();
        Collection<Searchable> searchables = storageService.getAllSearchables();
        Assertions.assertNotNull(searchables, "Коллекция не должна быть null");
    }

    @Test
    void testGetAllSearchables_shouldNotReturnEmptyCollection() {
        StorageService storageService = new StorageService();
        Collection<Searchable> searchables = storageService.getAllSearchables();
        Assertions.assertFalse(searchables.isEmpty(), "Коллекция не должна быть пустой");
    }

    @Test
    void testGetAllSearchables_shouldContainProducts() {
        StorageService storageService = new StorageService();
        Collection<Searchable> searchables = storageService.getAllSearchables();
        boolean hasProduct = searchables.stream()
                .anyMatch(s -> "PRODUCT".equals(s.getContentType()));
        Assertions.assertTrue(hasProduct, "В коллекции должны быть продукты");
    }

    @Test
    void testGetAllSearchables_shouldContainArticles() {
        StorageService storageService = new StorageService();
        Collection<Searchable> searchables = storageService.getAllSearchables();
        boolean hasArticle = searchables.stream()
                .anyMatch(s -> "ARTICLE".equals(s.getContentType()));
        Assertions.assertTrue(hasArticle, "В коллекции должны быть статьи");
    }

    @Test
    void testGetProductByIdExists() {
        Product product = storageService.getAllProducts().iterator().next();
        UUID id = product.getId();
        Optional<Product> optProduct = storageService.getProductById(id);
        assertTrue(optProduct.isPresent());
        Assertions.assertEquals(id, optProduct.get().getId());
    }

    @Test
    void testGetProductByIdNotExists() {
        UUID id = UUID.randomUUID();
        Optional<Product> optProduct = storageService.getProductById(id);
        assertFalse(optProduct.isPresent());
    }
}
