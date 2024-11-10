package store.domain;

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
            //TODO : 에러 메세지 , 끝나는 에러인지 처리할 에러인지 고민해보기
            throw new IllegalStateException("stock should be a positive number");
        }
    }

    private void updateValidate(int stock) {
        if (stock > this.stock) {
            //TODO : 에러 메세지 , 끝나는 에러인지 처리할 에러인지 고민해보기
            throw new IllegalStateException("stock should be less than the current stock");
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

