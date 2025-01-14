package domain;

public class Operation {
    public enum OperationType {
        UNKNOWN,
        INCOME,
        OUTCOME
    }

    private int id;
    private OperationType type;
    private int categoryId;
    private int amount;
    private int userId;

    public Operation(int id, OperationType type, int categoryId, int amount, int userId) {
        this.id = id;
        this.type = type;
        this.categoryId = categoryId;
        this.amount = amount;
        this.userId = userId;
    }

    public int getID() {
        return this.id;
    }

    public OperationType getType() {
        return this.type;
    }

    public int getCategoryID() {
        return this.categoryId;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getUserID() {
        return this.userId;
    }
}
