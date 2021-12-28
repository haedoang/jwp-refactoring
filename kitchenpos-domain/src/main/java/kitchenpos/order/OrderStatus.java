package kitchenpos.order;

public enum OrderStatus {
    COOKING, MEAL, COMPLETION;

    public boolean isCompletion() {
        return this == COMPLETION;
    }
}
