package org.skypro.skyshop.model.search;

public interface Searchable {

    String getSearchTerm();

    String getContentType();

    default String getStringRepresentation() {
        return getName() + " тип " + getContentType();
    }

    String getName();
}
