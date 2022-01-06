package dao.custom.impl;

import dao.custom.DetailDAO;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/19/2021
 * @Time_: 11:17 AM
 * @Project_Name : SipsewanaInstitute
 **/

public class DetailDaoImpl implements DetailDAO {
    @Override
    public boolean add(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(studentProgramDetail);

        transaction.commit();
        session.close();
        return !save.equals(null);
    }

    @Override
    public boolean delete(StudentProgramDetail id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(id);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(StudentProgramDetail studentProgramDetail) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE StudentProgramDetail SET givenMoney = :given , ongoingPayment = :onGoing , timeleft = :time WHERE student = :stu AND trainingProgram = :program ";
        Query query = session.createQuery(hql);
        query.setParameter("given", studentProgramDetail.getGivenMoney());
        query.setParameter("onGoing", studentProgramDetail.getOngoingPayment());
        query.setParameter("time", studentProgramDetail.getTimeleft());
        query.setParameter("stu", studentProgramDetail.getStudent());
        query.setParameter("program", studentProgramDetail.getTrainingProgram());
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;

    }

    @Override
    public StudentProgramDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT id FROM StudentProgramDetail ORDER BY id DESC";
        Query query = session.createQuery(hql);
        List<String> list = query.list();

        transaction.commit();
        session.close();

        if (list.size() > 0) {
            int tempId = Integer.parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            list.clear();
            if (tempId < 9) {

                return "Tp-00" + tempId;
            } else if (tempId < 99) {
                return "Tp-0" + tempId;
            } else {
                return "Tp-" + tempId;
            }
        }
        return "Tp-001";
    }

    @Override
    public List<StudentProgramDetail> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM StudentProgramDetail ";
        Query query = session.createQuery(hql);
        List<StudentProgramDetail> result = query.list();

        transaction.commit();
        session.close();

        return result;
    }

    @Override
    public StudentProgramDetail getDetail(Student student, TrainingProgram trainingProgram) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM StudentProgramDetail WHERE student = :student AND trainingProgram = :tP ";
        Query query = session.createQuery(hql);
        query.setParameter("student", student);
        query.setParameter("tP", trainingProgram);
        List<StudentProgramDetail> result = query.list();
        StudentProgramDetail st = null;
        for (StudentProgramDetail studentProgramDetail : result) {
            st = studentProgramDetail;
        }

        transaction.commit();
        session.close();
        return st;

    }
}
