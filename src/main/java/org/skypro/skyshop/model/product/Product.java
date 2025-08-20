package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    protected final String name;
    private final UUID id;

    public Product(UUID id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("UUID продукта не должен быть null.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя продукта не должно быть пустым.");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract int getPrice();

    public abstract boolean isSpecial();

    @Override
    public String toString() {
        return name;
    }
    @Override
    @JsonIgnore
    public String getSearchTerm(){
        return name;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
