package Tm;

public class Medicine {
    String id;
    String name;
    String availability;
    String Qty;
    String price;

    public Medicine() {
    }

    public Medicine(String id, String name, String availability, String qty, String price) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        Qty = qty;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                ", Qty='" + Qty + '\'' +
                ", price='" + price + '\'' +
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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
