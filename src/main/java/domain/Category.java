package domain;

public class Category {
    private int id;
    private OperationType type;
    private String name;
    private int amount;
    private int userId;

    public Category(OperationType type, String name, int amount, int userId) {
        this.type = type;

        if (type == OperationType.OUTCOME) {
            this.amount = amount;
        }

        this.name = name;
        this.userId = userId;
    }

    public Category(int id, OperationType type, String name, int amount, int userId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public int getID() {
        return this.id;
    }

    public OperationType getType() {
        return this.type;
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
