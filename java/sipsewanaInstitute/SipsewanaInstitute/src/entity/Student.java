package entity;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/15/2021
 * @Time_: 10:06 AM
 * @Project_Name : SipsewanaInstitute
 **/

@Entity
public class Student {
    @Id
    private String sId;
    private String name;
    private String address;
    private String contact;
    private int interviewMark;

    public Student() {
    }

    public Student(String sId, String name, String address, String contact, int interviewMark) {
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.interviewMark = interviewMark;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
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

    public int getInterviewMark() {
        return interviewMark;
    }

    public void setInterviewMark(int interviewMark) {
        this.interviewMark = interviewMark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sId='" + sId + '\'' +
                ", name=" + name +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", interviewMark=" + interviewMark +
                '}';
    }
}
