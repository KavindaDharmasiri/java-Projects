package bo.custom;

import bo.SuperBO;
import dto.StudentDto;
import entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentBo extends SuperBO {
    boolean save(Student student) throws SQLException, ClassNotFoundException;
    String getId() throws SQLException, ClassNotFoundException;
    List<String> getAllStudentIds() throws SQLException, ClassNotFoundException;
    StudentDto getStudent(String id) throws SQLException, ClassNotFoundException;
    Student getStudentOriginal(String id) throws SQLException, ClassNotFoundException;
    boolean update(Student student) throws SQLException, ClassNotFoundException;
    boolean deleteStudent(Student student) throws SQLException, ClassNotFoundException;
    List<StudentDto> search(String id) throws SQLException, ClassNotFoundException;
}
