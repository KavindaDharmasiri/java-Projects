package dto;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 12:08 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class StudentDto {
    private String studentId;
    private String name;
    private String address;
    private String contact;
    private int marks;

    public StudentDto() {
    }

    public StudentDto(String studentId, String name, String address, String contact, int marks) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", marks=" + marks +
                '}';
    }
}
