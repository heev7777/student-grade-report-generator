import model.Course;
import model.Student;
import model.exceptions.DuplicateStudentException;
import model.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private Course course;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        course = new Course("Test Course");
        student1 = new Student(1, "John Doe");
        student2 = new Student(2, "Jane Doe");
    }

    @Test
    public void testAddStudent() throws DuplicateStudentException {
        course.addStudent(student1);
        assertEquals(1, course.getStudents().size());
        assertEquals(student1, course.getStudents().get(0));
    }

    @Test
    public void testAddDuplicateStudent() {
        assertThrows(DuplicateStudentException.class, () -> {
            course.addStudent(student1);
            course.addStudent(student1);
        });
    }

    @Test
    public void testRemoveStudent() throws DuplicateStudentException, StudentNotFoundException {
        course.addStudent(student1);
        course.removeStudent(student1.getStudentId());
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    public void testRemoveNonExistentStudent() {
        assertThrows(StudentNotFoundException.class, () -> {
            course.removeStudent(999);
        });
    }

    @Test
    public void testGetStudent() throws DuplicateStudentException, StudentNotFoundException {
        course.addStudent(student1);
        Student retrievedStudent = course.getStudent(student1.getStudentId());
        assertEquals(student1, retrievedStudent);
    }

    @Test
    public void testGetNonExistentStudent() {
        assertThrows(StudentNotFoundException.class, () -> {
            course.getStudent(999);
        });
    }
}