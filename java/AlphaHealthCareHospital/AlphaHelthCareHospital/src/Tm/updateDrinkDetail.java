package Tm;

public class updateDrinkDetail {
    String patId;
    String drinkId;
    int qty;
    double price;

    public updateDrinkDetail() {
    }

    public updateDrinkDetail(String patId, String drinkId, int qty, double price) {
        this.patId = patId;
        this.drinkId = drinkId;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        return "updateDrinkDetail{" +
                "patId='" + patId + '\'' +
                ", drinkId='" + drinkId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
