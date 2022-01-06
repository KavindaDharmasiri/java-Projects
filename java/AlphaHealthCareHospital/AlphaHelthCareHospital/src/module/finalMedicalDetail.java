package module;


public class finalMedicalDetail {
    String patId;
    String mediId;
    int Qty;
    double price;

    public finalMedicalDetail() {
    }

    public finalMedicalDetail(String patId, String mediId, int qty, double price) {
        this.patId = patId;
        this.mediId = mediId;
        Qty = qty;
        this.price = price;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getMediId() {
        return mediId;
    }

    public void setMediId(String mediId) {
        this.mediId = mediId;
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
        return "finalMedicalDetail{" +
                "patId='" + patId + '\'' +
                ", mediId='" + mediId + '\'' +
                ", Qty=" + Qty +
                ", price=" + price +
                '}';
    }
}
