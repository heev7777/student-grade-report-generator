package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import model.exceptions.InvalidGradeException;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
    private int studentId;
    private String name;
    private Map<String, Double> grades;

    // Default constructor
    public Student() {
        this.grades = new HashMap<>();
    }

    // Overloaded constructor
    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.grades = new HashMap<>();
    }

    // Overloaded constructor
    public Student(int studentId, String name, Map<String, Double> initialGrades) {
        this(studentId, name);
        this.grades.putAll(initialGrades);
    }

    public void addGrade(String assignmentName, double grade) throws InvalidGradeException {
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException("Grade must be between 0 and 100.");
        }
        grades.put(assignmentName, grade);
    }

    public void editGrade(String assignmentName, double newGrade) throws InvalidGradeException {
        if (!grades.containsKey(assignmentName)) {
            throw new InvalidGradeException("Assignment not found.");
        }
        if (newGrade < 0 || newGrade > 100) {
            throw new InvalidGradeException("Grade must be between 0 and 100.");
        }
        grades.put(assignmentName, newGrade);
    }

    public double getGrade(String assignmentName) {
        return grades.getOrDefault(assignmentName, -1.0); // Return -1 if not found
    }

    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public String getLetterGrade() {
        double average = calculateAverageGrade();
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public String getName() {
        return name;
    }

    public int getStudentId() {
        return studentId;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }
}