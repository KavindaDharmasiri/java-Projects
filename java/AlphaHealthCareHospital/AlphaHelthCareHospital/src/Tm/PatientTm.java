package Tm;

public class PatientTm {
    String id;
    String name;
    String address;
    int age;
    String nic;
    String contact;

    public PatientTm() {
    }

    public PatientTm(String id, String name, String address, int age, String nic, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.nic = nic;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "PatientTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
