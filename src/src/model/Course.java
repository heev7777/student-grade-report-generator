package model;

public class Course {
    private String courseName;
    private List<Student> students;

    public Course(String courseName) {
        this.courseName = courseName;
        this.students = new ArrayList<>();
    }

    public double getAverageGrade() {
        return students.stream()
                .mapToDouble(Student::calculateAverageGrade)
                .average()
                .orElse(0.0);
    }
}
