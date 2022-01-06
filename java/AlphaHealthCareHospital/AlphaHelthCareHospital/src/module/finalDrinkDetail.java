package module;

public class finalDrinkDetail {
    String patId;
    String drinkId;
    int Qty;
    double price;

    public finalDrinkDetail() {
    }

    public finalDrinkDetail(String patId, String drinkId, int qty, double price) {
        this.patId = patId;
        this.drinkId = drinkId;
        Qty = qty;
        this.price = price;
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
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "finalDrinkDetail{" +
                "patId='" + patId + '\'' +
                ", drinkId='" + drinkId + '\'' +
                ", Qty=" + Qty +
                ", price=" + price +
                '}';
    }
}
