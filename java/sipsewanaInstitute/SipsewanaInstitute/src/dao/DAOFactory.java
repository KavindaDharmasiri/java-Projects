package dao;

import dao.custom.impl.CreateAccountDaoImpl;
import dao.custom.impl.DetailDaoImpl;
import dao.custom.impl.ProgramDaoImpl;
import dao.custom.impl.StudentDaoImpl;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 4:23 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {

            case DETAIL:
                return new DetailDaoImpl();
            case PROGRAM:
                return new ProgramDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            case NEW_USER:
                return new CreateAccountDaoImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        DETAIL, PROGRAM, STUDENT, NEW_USER
    }

}
