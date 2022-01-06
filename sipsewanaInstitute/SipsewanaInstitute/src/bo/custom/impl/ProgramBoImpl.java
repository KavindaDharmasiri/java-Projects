package bo.custom.impl;

import bo.custom.ProgramBo;
import dao.DAOFactory;
import dao.custom.ProgramDAO;
import entity.Student;
import entity.TrainingProgram;

import java.sql.SQLException;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 7:53 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class ProgramBoImpl implements ProgramBo {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public boolean save(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        return programDAO.add(trainingProgram);
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        return programDAO.getId();
    }

    @Override
    public List<String> getAllProgramId() throws SQLException, ClassNotFoundException {
        return programDAO.getAllIds();
    }

    @Override
    public TrainingProgram getProgram(String id) throws SQLException, ClassNotFoundException {
        return programDAO.search(id);
    }

    @Override
    public boolean updateProgram(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        return programDAO.update(trainingProgram);
    }

    @Override
    public boolean deleteProgram(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        return programDAO.delete(trainingProgram);
    }

    @Override
    public List<TrainingProgram> search(String id) throws SQLException, ClassNotFoundException {
        return programDAO.getProgram(id);
    }
}
