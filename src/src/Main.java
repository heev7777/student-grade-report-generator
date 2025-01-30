import model.Course;
import model.Student;
import model.exceptions.DuplicateStudentException;
import model.exceptions.InvalidGradeException;
import model.exceptions.StudentNotFoundException;
import persistence.DataStore;
import persistence.JSONDataStore;
import reports.FileReportGenerator;
import reports.GradeReportGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidGradeException {
        DataStore jsonDataStore = new JSONDataStore();
//        GradeReportGenerator textReportGenerator = new TextReportGenerator();
        GradeReportGenerator fileReportGenerator = new FileReportGenerator();

        List<Course> courses = loadCoursesFromJson(jsonDataStore);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student Grade");
            System.out.println("3. View All Students");
            System.out.println("4. Save Courses to JSON");
            System.out.println("5. Load Courses from JSON");
            System.out.println("6. Delete Student");
            System.out.println("7. List Grades for Every Student");
            System.out.println("8. Generate Report to Text File");
            System.out.println("9. Create New Course");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            switch (choice) {
                case "1":
                    addStudent(scanner, jsonDataStore, courses);
                    break;
                case "2":
                    editStudentGrade(scanner, courses);
                    break;
                case "3":
                    viewAllStudents(courses);
                    break;
                case "4":
                    saveCoursesToJson(jsonDataStore, courses);
                    break;
                case "5":
                    courses = loadCoursesFromJson(jsonDataStore);
                    break;
                case "6":
                    deleteStudent(scanner, jsonDataStore, courses);
                    break;
                case "7":
                    listGradesForEveryStudent(courses);
                    break;
                case "8":
                    generateReportToFile(courses, fileReportGenerator);
                    break;
                case "9":
                    createNewCourse(scanner, jsonDataStore, courses);
                    break;
                case "10":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createNewCourse(Scanner scanner, DataStore jsonDataStore, List<Course> courses) {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        if (courseName.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        Course newCourse = new Course(courseName);
        courses.add(newCourse);
        System.out.println("Course created successfully.");

        saveCoursesToJson(jsonDataStore, courses);
    }

    private static void addStudent(Scanner scanner, DataStore jsonDataStore, List<Course> courses) throws InvalidGradeException {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        if (courseName.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        Course course = courses.stream()
                .filter(c -> c.getCourseName().equals(courseName))
                .findFirst()
                .orElse(null);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

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
        student.addGrade("Default Assignment", 0.0);

        try {
            course.addStudent(student);
            System.out.println("Student added successfully.");
            saveCoursesToJson(jsonDataStore, courses);
        } catch (DuplicateStudentException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    private static void editStudentGrade(Scanner scanner, List<Course> courses) {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        if (courseName.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        Course course = courses.stream()
                .filter(c -> c.getCourseName().equals(courseName))
                .findFirst()
                .orElse(null);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

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

            if (!student.getGrades().containsKey(assignmentName)) {
                System.out.print("Assignment not found. Do you want to add it? (yes/no): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    System.out.print("Enter grade: ");
                    double grade = Double.parseDouble(scanner.nextLine());
                    student.addGrade(assignmentName, grade);
                    System.out.println("Assignment added and grade set successfully.");
                } else {
                    System.out.println("Operation cancelled.");
                }
            } else {
                System.out.print("Enter new grade: ");
                double grade = Double.parseDouble(scanner.nextLine());
                student.editGrade(assignmentName, grade);
                System.out.println("Grade updated successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error editing grade: " + e.getMessage());
        }
    }

    private static void viewAllStudents(List<Course> courses) {
        for (Course course : courses) {
            System.out.println("\nStudents in " + course.getCourseName() + ":");
            List<Student> students = course.getStudents();

            if (students.isEmpty()) {
                System.out.println("No students enrolled.");
            } else {
                for (Student student : students) {
                    System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());
                }
            }
        }
    }

    private static void deleteStudent(Scanner scanner, DataStore jsonDataStore, List<Course> courses) {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        if (courseName.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        Course course = courses.stream()
                .filter(c -> c.getCourseName().equals(courseName))
                .findFirst()
                .orElse(null);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter student ID to delete: ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        int studentId = Integer.parseInt(input);

        try {
            course.removeStudent(studentId);
            System.out.println("Student removed successfully.");
            saveCoursesToJson(jsonDataStore, courses);
        } catch (StudentNotFoundException e) {
            System.err.println("Error removing student: " + e.getMessage());
        }
    }

    private static void listGradesForEveryStudent(List<Course> courses) {
        for (Course course : courses) {
            System.out.println("Grades for every student in " + course.getCourseName() + ":");
            for (Student student : course.getStudents()) {
                System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName() + ", Grades: " + student.getGrades());
            }
        }
    }

    private static void generateReportToFile(List<Course> courses, GradeReportGenerator fileReportGenerator) {
        for (Course course : courses) {
            fileReportGenerator.generateReport(course);
            System.out.println("Report generated to file for course: " + course.getCourseName());
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
            List<Course> loadedCourses = jsonDataStore.loadCourses("courses.json");
            System.out.println("Courses loaded from courses.json");
            return loadedCourses;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading courses from JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}