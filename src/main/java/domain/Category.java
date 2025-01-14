package domain;

public class Category {
    private int id;
    private String name;
    private int amount;
    private int userId;

    public Category(int id, String name, int amount, int userId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getUserID() {
        return this.userId;
    }
}
