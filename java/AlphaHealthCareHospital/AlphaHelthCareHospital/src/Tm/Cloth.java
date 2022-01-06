package Tm;

public class Cloth {
    String id;
    String name;
    String availability;
    double price;

    public Cloth() {
    }

    public Cloth(String id, String name, String availability, double price) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", availability='" + availability + '\'' +
                ", price=" + price +
                '}';
    }
}
