package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.product.article.Article;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public StorageService() {
        initData();
    }

    private void initData() {
        FixPriceProduct bread = new FixPriceProduct(UUID.randomUUID(), "Хлеб");
        SimpleProduct milk = new SimpleProduct(UUID.randomUUID(), "Молоко", 114);
        DiscountedProduct butter = new DiscountedProduct(UUID.randomUUID(), "Масло сливочное", 800, 15);
        SimpleProduct cheese = new SimpleProduct(UUID.randomUUID(), "Сыр", 380);
        DiscountedProduct meat = new DiscountedProduct(UUID.randomUUID(), "Мясо", 450, 10);
        FixPriceProduct pepper = new FixPriceProduct(UUID.randomUUID(), "Перец");
        SimpleProduct oil = new SimpleProduct(UUID.randomUUID(), "Масло растительное", 124);

        products.put(bread.getId(), bread);
        products.put(milk.getId(), milk);
        products.put(butter.getId(), butter);
        products.put(cheese.getId(), cheese);
        products.put(meat.getId(), meat);
        products.put(pepper.getId(), pepper);
        products.put(oil.getId(), oil);

        Article artCheese = new Article(UUID.randomUUID(), "Сыр Liebendorf", "Пищевая ценность на 100 г. : белки -25.0, жиры " +
                "- 25.0, углеводы - 0.0, 330.0 ккал.");
        Article artBread = new Article(UUID.randomUUID(), "Батон нарезной", "Пищевая ценность на 100 г. : белки -7.5, жиры - " +
                "2.9, углеводы - 51.4, 265.0 ккал.");
        Article artMilk = new Article(UUID.randomUUID(), "Молоко ЭкоНива", "Пищевая ценность на 100 г. : белки -7.5, жиры - "+
                "2.9, углеводы - 51.4, 265.0 ккал.");
        Article artButter = new Article(UUID.randomUUID(), "Масло сливочное 82.5%", "Пищевая ценность на 100 г. : белки -0.6," +
                " жиры - 82.5, углеводы - 0.8, 748.0 ккал.");
        Article artMeat = new Article(UUID.randomUUID(), "Окорок свиной", "Пищевая ценность на 100 г. : белки -18.0, жиры - " +
                "34.0, углеводы - 0.0, 330.0 ккал.");
        Article artPepper = new Article(UUID.randomUUID(), "Перец черный молотый", "Пищевая ценность на 100 г. : белки -0.0, " +
                "жиры - 0.0, углеводы - 0.0, 0.0 ккал.");

        articles.put(artCheese.getId(), artCheese);
        articles.put(artBread.getId(), artBread);
        articles.put(artMilk.getId(), artMilk);
        articles.put(artButter.getId(), artButter);
        articles.put(artMeat.getId(), artMeat);
        articles.put(artPepper.getId(), artPepper);
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(articles.values());
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> combined = new ArrayList<>();
        combined.addAll(products.values());
        combined.addAll(articles.values());
        return Collections.unmodifiableCollection(combined);
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}
