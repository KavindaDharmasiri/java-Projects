package Tm;

public class updateMedicineForm {
    String patId;
    String mediId;
    int qty;
    double price;

    public updateMedicineForm() {
    }

    public updateMedicineForm(String patId, String mediId, int qty, double price) {
        this.patId = patId;
        this.mediId = mediId;
        this.qty = qty;
        this.price = price;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    @Override
    public String toString() {
        return "updateMedicineForm{" +
                "patId='" + patId + '\'' +
                ", mediId='" + mediId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }

    public String getMediId() {
        return mediId;
    }

    public void setMediId(String mediId) {
        this.mediId = mediId;
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
