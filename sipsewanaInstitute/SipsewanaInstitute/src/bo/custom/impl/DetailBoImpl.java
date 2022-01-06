package bo.custom.impl;

import bo.custom.DetailBo;
import dao.DAOFactory;
import dao.custom.DetailDAO;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;

import java.sql.SQLException;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/19/2021
 * @Time_: 11:15 AM
 * @Project_Name : SipsewanaInstitute
 **/

public class DetailBoImpl implements DetailBo {

    DetailDAO detailDAO = (DetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.DETAIL);

    @Override
    public boolean save(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException {
        return detailDAO.add(studentProgramDetail);
    }

    @Override
    public boolean update(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException {
        return detailDAO.update(studentProgramDetail);
    }

    @Override
    public boolean delete(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException {
        return detailDAO.delete(studentProgramDetail);
    }

    @Override
    public List<StudentProgramDetail> getAllProgram() throws SQLException, ClassNotFoundException {
        return detailDAO.getAll();
    }

    @Override
    public StudentProgramDetail getDetail(Student student, TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        return detailDAO.getDetail(student , trainingProgram);
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        return detailDAO.getId();
    }
}
