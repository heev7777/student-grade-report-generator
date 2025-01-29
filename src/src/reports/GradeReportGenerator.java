package reports;

import model.Course;
import model.Student;

public interface GradeReportGenerator {
    void generateReport(Student student);
    void generateReport(Course course);
    void generateReport(Course course, String filename);
}