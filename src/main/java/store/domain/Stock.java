package store.domain;

public class Stock {

    private int stock;

    public Stock(int stock) {
        validate(stock);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void update(int stock) {
        updateValidate(stock);
        this.stock -= stock;
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


}

