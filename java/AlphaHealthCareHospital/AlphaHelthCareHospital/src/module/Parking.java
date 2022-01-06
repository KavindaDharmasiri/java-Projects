package module;

public class Parking {
    String patId;
    String parkSlot;

    public Parking() {
    }

    public Parking(String patId, String parkSlot) {
        this.patId = patId;
        this.parkSlot = parkSlot;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getParkSlot() {
        return parkSlot;
    }

    public void setParkSlot(String parkSlot) {
        this.parkSlot = parkSlot;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "patId='" + patId + '\'' +
                ", parkSlot='" + parkSlot + '\'' +
                '}';
    }
}
