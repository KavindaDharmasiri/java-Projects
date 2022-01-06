package dao.custom.impl;

import dao.custom.CreateAccountDAO;
import entity.Login;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 3:48 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class CreateAccountDaoImpl implements CreateAccountDAO {
    @Override
    public boolean add(Login login) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(login);

        transaction.commit();
        session.close();
        return !save.equals(null);

    }

    @Override
    public boolean delete(Login s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Login login) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Login SET password = :pass WHERE name = :name";
        Query query = session.createQuery(hql);
        query.setParameter("pass", login.getPassword());
        query.setParameter("name", login.getName());

        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;

    }

    @Override
    public Login search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Login> searchUsers(String name) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Login WHERE name = :owner_name";
        Query query = session.createQuery(hql);
        query.setParameter("owner_name", name);
        List<Login> result = query.list();

        transaction.commit();
        session.close();

        return result;

    }
}
