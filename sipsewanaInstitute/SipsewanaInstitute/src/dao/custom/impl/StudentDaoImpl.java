package dao.custom.impl;

import dao.custom.StudentDAO;
import entity.Student;
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
 * @Date_: 12/18/2021
 * @Time_: 7:37 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class StudentDaoImpl implements StudentDAO {
    @Override
    public boolean add(Student student) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Serializable save = session.save(student);

        transaction.commit();
        session.close();
        return !save.equals(null);

    }

    @Override
    public boolean delete(Student s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(s);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(Student student) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "UPDATE Student SET name = :name , address = :address , contact = :contact , interviewMark = :interviewMark WHERE sId = :id";
        Query query = session.createQuery(hql);
        query.setParameter("name", student.getName());
        query.setParameter("address", student.getAddress());
        query.setParameter("contact", student.getContact());
        query.setParameter("interviewMark", student.getInterviewMark());
        query.setParameter("id", student.getsId());
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();

        return rowCount > 0;


    }

    @Override
    public Student search(String s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Student WHERE sId = :stu_Id";
        Query query = session.createQuery(hql);
        query.setParameter("stu_Id", s);
        List<Student> result = query.list();
        Student stu = null;
        for (Student student : result) {
            stu = student;
        }

        transaction.commit();
        session.close();

        return stu;

    }

    @Override
    public List<String> getAllIds() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT sId FROM Student";
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

        String hql = "SELECT sId FROM Student ORDER BY sId DESC";
        Query query = session.createQuery(hql);
        List<String> list = query.list();

        transaction.commit();
        session.close();

        if (list.size() > 0) {
            int tempId = Integer.parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            list.clear();
            if (tempId < 9) {

                return "S-00" + tempId;
            } else if (tempId < 99) {
                return "S-0" + tempId;
            } else {
                return "S-" + tempId;
            }
        }
        return "S-001";
    }

    @Override
    public List<Student> getStudent(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Student WHERE sId LIKE :stu_Id";
        Query query = session.createQuery(hql);
        query.setParameter("stu_Id", '%'+id+'%');
        List<Student> result = query.list();


        transaction.commit();
        session.close();

        return result;
    }
}
