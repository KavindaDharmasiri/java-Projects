package dao.custom;

import dao.CrudDAO;
import entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student , String > {
    List<Student> getStudent(String id);
}
