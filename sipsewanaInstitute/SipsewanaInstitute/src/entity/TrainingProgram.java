package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/15/2021
 * @Time_: 10:06 AM
 * @Project_Name : SipsewanaInstitute
 **/

@Entity
public class TrainingProgram {
    @Id
    private String programId;
    private String programe;
    private String duration;
    private double fee;

    public TrainingProgram() {
    }

    public TrainingProgram(String programId, String programe, String duration, double fee) {
        this.programId = programId;
        this.programe = programe;
        this.duration = duration;
        this.fee = fee;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getPrograme() {
        return programe;
    }

    public void setPrograme(String programe) {
        this.programe = programe;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "TrainingProgram{" +
                "programId='" + programId + '\'' +
                ", programe='" + programe + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                '}';
    }
}
