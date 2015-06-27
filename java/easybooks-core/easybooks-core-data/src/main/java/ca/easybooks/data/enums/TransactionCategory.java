package ca.easybooks.data.enums;

public enum TransactionCategory {
    EXPENSE(0),
    REVENUE(1);

    private final int type;

    private TransactionCategory(final int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
