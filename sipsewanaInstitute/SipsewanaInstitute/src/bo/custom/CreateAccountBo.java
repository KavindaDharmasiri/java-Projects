package bo.custom;

import bo.SuperBO;
import entity.Login;

import java.sql.SQLException;
import java.util.List;

public interface CreateAccountBo extends SuperBO {
    boolean save(Login log) throws SQLException, ClassNotFoundException;
    List<Login> search(String name) throws SQLException, ClassNotFoundException;
    boolean update(Login login) throws SQLException, ClassNotFoundException;
}
