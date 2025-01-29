package reports;

import model.Course;
import model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileReportGenerator implements GradeReportGenerator {
    @Override
    public void generateReport(Student student) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("student_report_" + student.getStudentId() + ".txt"))) {
            writer.println("Student Report:");
            writer.println("ID: " + student.getStudentId());
            writer.println("Name: " + student.getName());
            writer.println("Grades: " + student.getGrades());
            writer.println("Average Grade: " + student.calculateAverageGrade());
            writer.println("Letter Grade: " + student.getLetterGrade());
        } catch (IOException e) {
            System.err.println("Error writing student report to file: " + e.getMessage());
        }
    }

    @Override
    public void generateReport(Course course) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("course_report_" + course.getCourseName() + ".txt"))) {
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

    public void generateReport(Student student, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Student Report:");
            writer.println("ID: " + student.getStudentId());
            writer.println("Name: " + student.getName());
            writer.println("Grades: " + student.getGrades());
            writer.println("Average Grade: " + student.calculateAverageGrade());
            writer.println("Letter Grade: " + student.getLetterGrade());
        } catch (IOException e) {
            System.err.println("Error writing student report to file: " + e.getMessage());
        }
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