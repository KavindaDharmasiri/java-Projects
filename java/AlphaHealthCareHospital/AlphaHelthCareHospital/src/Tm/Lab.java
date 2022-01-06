package Tm;

public class Lab {
    String id;
    String type;
    String availability;

    public Lab() {
    }

    public Lab(String id, String type, String availability) {
        this.id = id;
        this.type = type;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", availability='" + availability + '\'' +
                '}';
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
}
