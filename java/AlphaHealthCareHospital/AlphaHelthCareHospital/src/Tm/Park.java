package Tm;

public class Park {
    String slot;
    String availability;

    public Park() {
    }

    public Park(String slot, String availability) {
        this.slot = slot;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Park{" +
                "slot='" + slot + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
