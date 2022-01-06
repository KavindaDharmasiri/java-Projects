package module;

public class RoomDetail {
    String id;
    String type;
    String availability;
    String price;

    public RoomDetail() {
    }

    public RoomDetail(String id, String type, String availability, String price) {
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RoomDetail{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", availability='" + availability + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
