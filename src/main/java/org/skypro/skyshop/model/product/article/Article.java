package org.skypro.skyshop.model.product.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements Searchable {

    private final UUID id;
    private final String name;
    private final String text;

    public Article(UUID id, String name, String text) {
        if (id == null) {
            throw new IllegalArgumentException("ID статьи не должен быть null.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название статьи не должно быть пустым.");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Текст статьи не может быть пустым.");
        }
        this.id = id;
        this.name = name;
        this.text = text;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + "\n" + text;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return name + " " + text;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
