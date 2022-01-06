package dao.custom;

import dao.CrudDAO;
import entity.Login;

import java.util.List;

public interface CreateAccountDAO extends CrudDAO<Login ,String> {
    List<Login> searchUsers(String name);
}
