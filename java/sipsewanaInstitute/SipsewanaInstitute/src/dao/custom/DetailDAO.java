package dao.custom;

import dao.CrudDAO;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;

import java.util.List;

public interface DetailDAO extends CrudDAO<StudentProgramDetail , String> {
    List<StudentProgramDetail> getAll();
    StudentProgramDetail getDetail(Student student , TrainingProgram trainingProgram);
}
