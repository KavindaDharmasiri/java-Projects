package dao.custom;

import dao.CrudDAO;
import entity.TrainingProgram;

import java.sql.SQLException;
import java.util.List;

public interface ProgramDAO extends CrudDAO<TrainingProgram , String> {
    @Override
    List<String> getAllIds() throws SQLException, ClassNotFoundException;

    List<TrainingProgram> getProgram(String id);
}
