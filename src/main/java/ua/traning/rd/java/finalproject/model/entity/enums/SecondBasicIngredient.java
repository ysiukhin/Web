package ua.traning.rd.java.finalproject.model.entity.enums;

public enum SecondBasicIngredient {
    AMERICANA(1000),
    CREAM(100);

    private final int quantity;

    SecondBasicIngredient(int quantity) {
        this.quantity = quantity;
    }
}
