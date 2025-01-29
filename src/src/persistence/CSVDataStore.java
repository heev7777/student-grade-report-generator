package persistence;

import model.Course;
import model.Student;
import model.exceptions.InvalidGradeException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVDataStore implements DataStore {
    @Override
    public void saveCourses(List<Course> courses, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Course course : courses) {
                writer.println("Course:" + course.getCourseName());
                for (Student student : course.getStudents()) {
                    writer.print("Student:" + student.getStudentId() + "," + student.getName());

                    for (Map.Entry<String, Double> entry : student.getGrades().entrySet()) {
                        writer.print("," + entry.getKey() + ":" + entry.getValue());
                    }
                    writer.println();
                }
            }
        }
    }

    @Override
    public List<Course> loadCourses(String filename) throws IOException {
        List<Course> courses = new ArrayList<>();
        Course currentCourse = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Course:")) {
                    String courseName = line.substring("Course:".length());
                    currentCourse = new Course(courseName);
                    courses.add(currentCourse);
                } else if (line.startsWith("Student:")) {
                    if (currentCourse != null) {
                        Student student = getStudent(line);
                        currentCourse.addStudent(student);
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("Error loading courses from CSV: " + e.getMessage());
        }
        return courses;
    }

    private static Student getStudent(String line) throws InvalidGradeException {
        String[] parts = line.substring("Student:".length()).split(",");
        int studentId = Integer.parseInt(parts[0]);
        String studentName = parts[1];

        Map<String, Double> grades = new HashMap<>();
        for (int i = 2; i < parts.length; i++) {
            String[] gradeParts = parts[i].split(":");
            if (gradeParts.length == 2) {
                try {
                    grades.put(gradeParts[0], Double.parseDouble(gradeParts[1]));
                } catch (NumberFormatException e) {
                    throw new InvalidGradeException("Invalid grade format for student ID " + studentId);
                }
            }
        }

        Student student = new Student(studentId, studentName, grades);
        return student;
    }

}
