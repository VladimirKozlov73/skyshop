package org.skypro.skyshop.model.product;

public class DiscountedProduct extends Product{

    protected int basePrice;
    protected int discount;

    public DiscountedProduct(String name, int basePrice, int discount) {
        super(name);
        if (basePrice <= 0){
            throw new IllegalArgumentException("Цена на продукт со скидкой не может быть равна или менее нуля.");
        }
        this.basePrice = basePrice;
        if (discount < 0 || discount > 100){
            throw new IllegalArgumentException("Скидка на продукт не может быть меньше нуля или больше 100%.");
        }
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        int price;
        price = (int) Math.round (basePrice * (1 - discount / 100.0));
        return price;
    }

    @Override
    public String toString() {
        return name + ": " + getPrice() + " (" + discount + "%)";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
