package Tm;

public class medicalDetail {
    String id;
    String name;
    int qty;
    double unitPrice;
    double total;

    public medicalDetail() {
    }

    public medicalDetail(String id, String name, int qty, double unitPrice, double total) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    @Override
    public String toString() {
        return "medicalDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
