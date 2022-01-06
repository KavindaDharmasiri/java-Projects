package dto;

import entity.Student;
import entity.TrainingProgram;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 12:08 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class DetailDto {
    private String studentId;
    private String trainingProgramId;
    private double givenMoney;
    private double ongoingPayment;
    private String timeleft;

    public DetailDto() {
    }

    public DetailDto(String studentId, String trainingProgramId, double givenMoney, double ongoingPayment, String timeleft) {
        this.studentId = studentId;
        this.trainingProgramId = trainingProgramId;
        this.givenMoney = givenMoney;
        this.ongoingPayment = ongoingPayment;
        this.timeleft = timeleft;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(String trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
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

    @Override
    public String toString() {
        return "DetailDto{" +
                "studentId='" + studentId + '\'' +
                ", trainingProgramId='" + trainingProgramId + '\'' +
                ", givenMoney=" + givenMoney +
                ", ongoingPayment=" + ongoingPayment +
                ", timeleft='" + timeleft + '\'' +
                '}';
    }
}
