package persistence;

import model.Course;

import java.io.IOException;
import java.util.List;

public interface DataStore {
    void saveCourses(List<Course> courses, String filename) throws IOException;
    List<Course> loadCourses(String filename) throws IOException, ClassNotFoundException;
}