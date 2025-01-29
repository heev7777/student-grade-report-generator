package persistence;

public interface DataStore {
    void saveCourses(List<Course> courses, String filename) throws IOException;
    List<Course> loadCourses(String filename) throws IOException, ClassNotFoundException;
}