package Tm;

public class Room {
    String id;
    String type;
    String availability;
    double price;

    public Room() {
    }

    public Room(String id, String type, String availability, double price) {
        this.id = id;
        this.type = type;
        this.availability = availability;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", availability='" + availability + '\'' +
                ", price=" + price +
                '}';
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
}
