package bo.custom;

import bo.SuperBO;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;

import java.sql.SQLException;
import java.util.List;

public interface DetailBo extends SuperBO {
    boolean save(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException;
    boolean update(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException;
    boolean delete(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException;
    List<StudentProgramDetail> getAllProgram() throws SQLException, ClassNotFoundException;
    StudentProgramDetail getDetail(Student student , TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException;
    String getId() throws SQLException, ClassNotFoundException;
}
