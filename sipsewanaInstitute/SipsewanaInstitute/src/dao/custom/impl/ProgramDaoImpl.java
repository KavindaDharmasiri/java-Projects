package dao.custom.impl;

import dao.custom.ProgramDAO;
import entity.Student;
import entity.TrainingProgram;
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
 * @Time_: 7:55 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class ProgramDaoImpl implements ProgramDAO {
    @Override
    public boolean add(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(trainingProgram);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public boolean delete(TrainingProgram s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(s);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(TrainingProgram trainingProgram) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE TrainingProgram SET programe = :programe , duration = :duration , fee = :fee WHERE programId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("programe", trainingProgram.getPrograme());
        query.setParameter("duration", trainingProgram.getDuration());
        query.setParameter("fee", trainingProgram.getFee());
        query.setParameter("id", trainingProgram.getProgramId());
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;

    }

    @Override
    public TrainingProgram search(String s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM TrainingProgram WHERE programId = :pro_Id";
        Query query = session.createQuery(hql);
        query.setParameter("pro_Id", s);
        List<TrainingProgram> result = query.list();
        TrainingProgram t = null;
        for (TrainingProgram trainingProgram : result) {
            t = trainingProgram;
        }

        transaction.commit();
        session.close();

        return t;

    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT programId FROM TrainingProgram";
        Query query = session.createQuery(hql);
        List<String> result = query.list();

        transaction.commit();
        session.close();
        return result;

    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT programId FROM TrainingProgram ORDER BY programId DESC";
        Query query = session.createQuery(hql);
        List<String> list = query.list();

        transaction.commit();
        session.close();

        if (list.size() > 0) {
            int tempId = Integer.parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            list.clear();
            if (tempId < 9) {

                return "P-00" + tempId;
            } else if (tempId < 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }
        }
        return "P-001";
    }

    @Override
    public List<TrainingProgram> getProgram(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM TrainingProgram WHERE programId LIKE :pro_Id";
        Query query = session.createQuery(hql);
        query.setParameter("pro_Id", '%'+id+'%');
        List<TrainingProgram> result = query.list();


        transaction.commit();
        session.close();

        return result;
    }
}
