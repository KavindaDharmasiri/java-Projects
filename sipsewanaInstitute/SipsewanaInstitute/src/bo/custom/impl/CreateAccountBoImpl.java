package bo.custom.impl;

import bo.custom.CreateAccountBo;
import dao.custom.impl.CreateAccountDaoImpl;
import entity.Login;

import java.sql.SQLException;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 3:45 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class CreateAccountBoImpl implements CreateAccountBo {
    CreateAccountDaoImpl createAccountDao=new CreateAccountDaoImpl();
    @Override
    public boolean save(Login log) throws SQLException, ClassNotFoundException {
        return createAccountDao.add(log);
    }

    @Override
    public List<Login> search(String name) {
        return createAccountDao.searchUsers(name);
    }

    @Override
    public boolean update(Login login) throws SQLException, ClassNotFoundException {
        return createAccountDao.update(login);
    }
}
