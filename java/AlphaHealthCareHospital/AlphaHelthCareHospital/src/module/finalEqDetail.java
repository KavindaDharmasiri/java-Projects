package module;

public class finalEqDetail {
    String patId;
    String eqId;
    int Qty;
    double price;

    public finalEqDetail() {
    }

    public finalEqDetail(String patId, String eqId, int qty, double price) {
        this.patId = patId;
        this.eqId = eqId;
        Qty = qty;
        this.price = price;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
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
        return "finalEqDetail{" +
                "patId='" + patId + '\'' +
                ", eqId='" + eqId + '\'' +
                ", Qty=" + Qty +
                ", price=" + price +
                '}';
    }
}
