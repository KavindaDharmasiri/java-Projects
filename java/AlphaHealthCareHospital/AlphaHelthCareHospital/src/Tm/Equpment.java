package Tm;

public class Equpment {
    String id;
    String name;
    String availability;
    int qty;
    double price;

    public Equpment() {
    }

    public Equpment(String id, String name, String availability, int qty, double price) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Equpment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                ", qty=" + qty +
                ", price=" + price +
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
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
