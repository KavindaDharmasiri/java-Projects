package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/15/2021
 * @Time_: 10:08 AM
 * @Project_Name : SipsewanaInstitute
 **/

@Entity
public class StudentProgramDetail implements Serializable {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "sId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "programeId")
    private TrainingProgram trainingProgram;

    private double givenMoney;
    private double ongoingPayment;
    private String timeleft;
    private String date;

    public StudentProgramDetail() {
    }

    public StudentProgramDetail(String id, Student student, TrainingProgram trainingProgram, double givenMoney, double ongoingPayment, String timeleft, String date) {
        this.id = id;
        this.student = student;
        this.trainingProgram = trainingProgram;
        this.givenMoney = givenMoney;
        this.ongoingPayment = ongoingPayment;
        this.timeleft = timeleft;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public double getGivenMoney() {
        return givenMoney;
    }

    public void setGivenMoney(double givenMoney) {
        this.givenMoney = givenMoney;
    }

    public double getOngoingPayment() {
        return ongoingPayment;
    }

    public void setOngoingPayment(double ongoingPayment) {
        this.ongoingPayment = ongoingPayment;
    }

    public String getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(String timeleft) {
        this.timeleft = timeleft;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StudentProgramDetail{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", trainingProgram=" + trainingProgram +
                ", givenMoney=" + givenMoney +
                ", ongoingPayment=" + ongoingPayment +
                ", timeleft='" + timeleft + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
