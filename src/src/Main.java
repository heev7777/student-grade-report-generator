import model.Course;
import model.Student;
import model.exceptions.DuplicateStudentException;
import persistence.DataStore;
import persistence.JSONDataStore;
import reports.FileReportGenerator;
import reports.GradeReportGenerator;
import reports.TextReportGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataStore jsonDataStore = new JSONDataStore();
        GradeReportGenerator textReportGenerator = new TextReportGenerator();
        GradeReportGenerator fileReportGenerator = new FileReportGenerator();

        List<Course> courses = new ArrayList<>();
        Course course1 = new Course("Introduction to Programming");
        courses.add(course1);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student Grade");
            System.out.println("3. View All Students");
            System.out.println("4. Save Courses to JSON");
            System.out.println("5. Load Courses from JSON");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            switch (choice) {
                case "1":
                    addStudent(course1, scanner, jsonDataStore, courses);
                    break;
                case "2":
                    editStudentGrade(course1, scanner);
                    break;
                case "3":
                    viewAllStudents(jsonDataStore, courses);
                    break;
                case "4":
                    saveCoursesToJson(jsonDataStore, courses);
                    break;
                case "5":
                    courses = loadCoursesFromJson(jsonDataStore);
                    break;
                case "6":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(Course course, Scanner scanner, DataStore jsonDataStore, List<Course> courses) {
        System.out.print("Enter student ID: ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        int studentId = Integer.parseInt(input);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (name.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        Student student = new Student(studentId, name);
        try {
            course.addStudent(student);
            System.out.println("Student added successfully.");
            saveCoursesToJson(jsonDataStore, courses); // Save courses to JSON after adding a student
        } catch (DuplicateStudentException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    private static void editStudentGrade(Course course, Scanner scanner) {
        System.out.print("Enter student ID: ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        int studentId = Integer.parseInt(input);

        try {
            Student student = course.getStudent(studentId);
            System.out.print("Enter assignment name: ");
            String assignmentName = scanner.nextLine();
            if (assignmentName.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            System.out.print("Enter new grade: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
            double grade = Double.parseDouble(input);

            student.editGrade(assignmentName, grade);
            System.out.println("Grade updated successfully.");
        } catch (Exception e) {
            System.err.println("Error editing grade: " + e.getMessage());
        }
    }

    private static void viewAllStudents(DataStore jsonDataStore, List<Course> courses) {
        courses = loadCoursesFromJson(jsonDataStore);
        for (Course course : courses) {
            System.out.println("Students in " + course.getCourseName() + ":");
            for (Student student : course.getStudents()) {
                System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());
            }
        }
    }
    private static void saveCoursesToJson(DataStore jsonDataStore, List<Course> courses) {
        try {
            jsonDataStore.saveCourses(courses, "courses.json");
            System.out.println("Courses saved to courses.json");
        } catch (IOException e) {
            System.err.println("Error saving courses to JSON: " + e.getMessage());
        }
    }

    private static List<Course> loadCoursesFromJson(DataStore jsonDataStore) {
        try {
            List<Course> courses = jsonDataStore.loadCourses("courses.json");
            System.out.println("Courses loaded from courses.json");
            return courses;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading courses from JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}