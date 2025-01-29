package reports;

import model.Course;
import model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextReportGenerator implements GradeReportGenerator {
    @Override
    public void generateReport(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Student Report:\n");
        sb.append("ID: ").append(student.getStudentId()).append("\n");
        sb.append("Name: ").append(student.getName()).append("\n");
        sb.append("Grades: ").append(student.getGrades()).append("\n");
        sb.append("Average Grade: ").append(student.calculateAverageGrade()).append("\n");
        sb.append("Letter Grade: ").append(student.getLetterGrade()).append("\n");
        System.out.println(sb.toString());
    }

    @Override
    public void generateReport(Course course) {
        StringBuilder sb = new StringBuilder();
        sb.append("Course Report:\n");
        sb.append("Course Name: ").append(course.getCourseName()).append("\n");
        sb.append("Students:\n");
        for (Student student : course.getStudents()) {
            sb.append("\tID: ").append(student.getStudentId()).append(", Name: ").append(student.getName()).append("\n");
        }
        sb.append("Average Grade: ").append(course.getAverageGrade()).append("\n");
        sb.append("Highest Grade: ").append(course.getHighestGrade()).append("\n");
        sb.append("Lowest Grade: ").append(course.getLowestGrade()).append("\n");
        System.out.println(sb.toString());
    }

    @Override
    public void generateReport(Course course, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Course Report:");
            writer.println("Course Name: " + course.getCourseName());
            writer.println("Students:");
            for (Student student : course.getStudents()) {
                writer.println("\tID: " + student.getStudentId() + ", Name: " + student.getName());
            }
            writer.println("Average Grade: " + course.getAverageGrade());
            writer.println("Highest Grade: " + course.getHighestGrade());
            writer.println("Lowest Grade: " + course.getLowestGrade());
        } catch (IOException e) {
            System.err.println("Error writing course report to file: " + e.getMessage());
        }
    }
}