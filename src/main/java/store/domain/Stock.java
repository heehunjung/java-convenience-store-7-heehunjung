package store.domain;

public class Stock {

    private int stock;

    public Stock(int stock) {
        validate(stock);
        this.stock = stock;
    }

    private void validate(int stock) {
        if (stock < 0) {
            //TODO : 예외 메세지, 종료되는 에러인지 체크
            throw new IllegalStateException( "stock should be a positive number");
        }
    }

    public void update(int stock) {
        if (stock > this.stock) {
            throw new IllegalStateException( "stock should be a positive number");
        }
        this.stock -= stock;
    }

    public int getStock() {
        return stock;
    }
}
