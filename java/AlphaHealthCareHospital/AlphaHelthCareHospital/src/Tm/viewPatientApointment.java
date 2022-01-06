package Tm;

public class viewPatientApointment {
    String patId;
    String patName;
    int age;
    String date;
    String time;

    public viewPatientApointment() {
    }
    public viewPatientApointment(String patId, String patName, int age, String date, String time) {
        this.patId = patId;
        this.patName = patName;
        this.age = age;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "viewPatientApointment{" +
                "patId='" + patId + '\'' +
                ", patName='" + patName + '\'' +
                ", age=" + age +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
