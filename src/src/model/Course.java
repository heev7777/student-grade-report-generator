package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import model.exceptions.DuplicateStudentException;
import model.exceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private String courseName;
    private List<Student> students;

    public Course() {
        this.students = new ArrayList<>();
    }

    public Course(String courseName) {
        this.courseName = courseName;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) throws DuplicateStudentException {
        if (students.stream().anyMatch(s -> s.getStudentId() == student.getStudentId())) {
            throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists in the course.");
        }
        students.add(student);
    }

    public void removeStudent(int studentId) throws StudentNotFoundException {
        boolean removed = students.removeIf(s -> s.getStudentId() == studentId);
        if (!removed) {
            throw new StudentNotFoundException("Student with ID " + studentId + " not found in the course.");
        }
    }

    public Student getStudent(int studentId) throws StudentNotFoundException {
        return students.stream()
                .filter(s -> s.getStudentId() == studentId)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + studentId + " not found in the course."));
    }

    public double getAverageGrade() {
        return students.stream()
                .mapToDouble(Student::calculateAverageGrade)
                .average()
                .orElse(0.0);
    }

    public double getHighestGrade() {
        return students.stream()
                .mapToDouble(Student::calculateAverageGrade)
                .max()
                .orElse(0.0);
    }

    public double getLowestGrade() {
        return students.stream()
                .mapToDouble(Student::calculateAverageGrade)
                .min()
                .orElse(0.0);
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Student> getStudents() {
        return students;
    }
}