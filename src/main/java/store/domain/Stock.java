package store.domain;

import static store.global.ErrorMessages.INVALID_STATE_ERROR;

import java.util.Objects;

public class Stock {

    private int stock;

    public Stock(int stock) {
        validate(stock);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public boolean compare(int stock) {
        return this.stock >= stock;
    }

    public void minus(int stock) {
        updateValidate(stock);
        this.stock -= stock;
    }

    public void plus(int stock) {
        this.stock += stock;
    }

    private void validate(int stock) {
        if (stock < 0) {
            throw new IllegalStateException(INVALID_STATE_ERROR.getMessage());
        }
    }

    private void updateValidate(int stock) {
        if (stock > this.stock) {
            throw new IllegalStateException(INVALID_STATE_ERROR.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock1 = (Stock) o;
        return stock == stock1.stock;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stock);
    }
}

