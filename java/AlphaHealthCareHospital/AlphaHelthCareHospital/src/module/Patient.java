package module;

public class Patient {
    String id;
    String name;
    String address;
    String nic;
    String contact;

    public Patient() {
    }

    public Patient(String id, String name, String address, String nic, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.contact = contact;
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

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
