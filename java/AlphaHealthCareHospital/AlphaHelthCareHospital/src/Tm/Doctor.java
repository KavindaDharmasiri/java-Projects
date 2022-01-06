package Tm;

public class Doctor {
    String id;
    String name;
    String specialist;
    String address;
    String nic;
    String contact;

    public Doctor() {
    }

    public Doctor(String id, String name, String specialist, String address, String nic, String contact) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.address = address;
        this.nic = nic;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialist='" + specialist + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
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

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
