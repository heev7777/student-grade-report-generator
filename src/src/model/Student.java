package model;

public class Student {
    private int studentId;
    private String name;
    private Map<String, Double> grades;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.grades = new HashMap<>();
    }

    public Student(int studentId, String name, Map<String, Double> initialGrades) {
        this(studentId, name); // we call the first constuructor
        this.grades.putAll(initialGrades);
    }
}
