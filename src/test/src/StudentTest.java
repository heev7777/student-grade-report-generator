package src;

import model.Student;
import model.exceptions.InvalidGradeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student(1, "random person");
    }

    @Test
    public void testAddGrade() throws InvalidGradeException {
        student.addGrade("Assignment 1", 85.0);
        assertEquals(85.0, student.getGrade("Assignment 1"));
    }

    @Test
    public void testAddInvalidGrade() {
        assertThrows(InvalidGradeException.class, () -> {
            student.addGrade("Assignment 1", 105.0);
        });
    }

    @Test
    public void testEditGrade() throws InvalidGradeException {
        student.addGrade("Assignment 1", 85.0);
        student.editGrade("Assignment 1", 90.0);
        assertEquals(90.0, student.getGrade("Assignment 1"));
    }

    @Test
    public void testEditNonExistentGrade() {
        assertThrows(InvalidGradeException.class, () -> {
            student.editGrade("Assignment 1", 90.0);
        });
    }

    @Test
    public void testCalculateAverageGrade() throws InvalidGradeException {
        student.addGrade("Assignment 1", 85.0);
        student.addGrade("Assignment 2", 95.0);
        assertEquals(90.0, student.calculateAverageGrade());
    }

    @Test
    public void testGetLetterGrade() throws InvalidGradeException {
        student.addGrade("Assignment 1", 85.0);
        assertEquals("B", student.getLetterGrade());
    }
}