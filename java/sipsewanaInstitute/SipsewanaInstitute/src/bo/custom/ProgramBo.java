package bo.custom;

import bo.SuperBO;
import dto.StudentDto;
import entity.Student;
import entity.TrainingProgram;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBo extends SuperBO {
    boolean save(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException;
    String getId() throws SQLException, ClassNotFoundException;
    List<String> getAllProgramId() throws SQLException, ClassNotFoundException;
    TrainingProgram getProgram(String id) throws SQLException, ClassNotFoundException;
    boolean updateProgram(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException;
    boolean deleteProgram(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException;
    List<TrainingProgram> search(String id) throws SQLException, ClassNotFoundException;

}
