package reports;

import model.Course;
import model.Student;

public class TextReportGenerator implements GradeReportGenerator {
    @Override
    public void generateReport(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("model.Student Report:\n");
        sb.append("ID: ").append(student.getStudentId()).append("\n");
        sb.append("Name: ").append(student.getName()).append("\n");
        // Maybe something else more?
        System.out.println(sb.toString());
    }

    @Override
    public void generateReport(Course course) {

    }
}