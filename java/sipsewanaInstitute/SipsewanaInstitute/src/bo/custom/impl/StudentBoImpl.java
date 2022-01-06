package bo.custom.impl;

import bo.custom.StudentBo;
import dao.DAOFactory;
import dao.custom.StudentDAO;
import dto.StudentDto;
import entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/18/2021
 * @Time_: 7:33 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class StudentBoImpl implements StudentBo {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
        return studentDAO.add(student);
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        return studentDAO.getId();
    }

    @Override
    public List<String> getAllStudentIds() throws SQLException, ClassNotFoundException {
        return studentDAO.getAllIds();
    }

    @Override
    public StudentDto getStudent(String id) throws SQLException, ClassNotFoundException {
        Student search = studentDAO.search(id);
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(search.getsId());
        studentDto.setName(search.getName());
        studentDto.setAddress(search.getAddress());
        studentDto.setContact(search.getContact());
        studentDto.setMarks(search.getInterviewMark());

        return studentDto;
    }

    @Override
    public Student getStudentOriginal(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.search(id);
    }

    @Override
    public boolean update(Student student) throws SQLException, ClassNotFoundException {
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(Student student) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(student);
    }

    @Override
    public List<StudentDto> search(String id) throws SQLException, ClassNotFoundException {
        List<Student> student = studentDAO.getStudent(id);
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student st:student
             ) {
            studentDtos.add(new StudentDto(st.getsId(),st.getName(),st.getAddress(),st.getContact(),st.getInterviewMark()));

        }
        return studentDtos;
    }

}
