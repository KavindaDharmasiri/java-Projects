package Tm;

public class updateEqDetail {
    String patId;
    String eqId;
    int qty;
    double price;

    public updateEqDetail() {
    }

    public updateEqDetail(String patId, String eqId, int qty, double price) {
        this.patId = patId;
        this.eqId = eqId;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        return "updateEqDetail{" +
                "patId='" + patId + '\'' +
                ", eqId='" + eqId + '\'' +
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

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
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
