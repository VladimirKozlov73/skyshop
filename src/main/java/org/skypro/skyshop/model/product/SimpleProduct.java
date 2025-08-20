package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product{

    protected int price;


    public SimpleProduct(UUID id, String name, int price) {
        super(id, name);
        if (price <= 0){
            throw new IllegalArgumentException("Цена продукта не может быть равна или менее нуля.");
        }
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }

    @Override
    public boolean isSpecial(){
        return false;
    }
}
