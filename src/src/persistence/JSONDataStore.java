package persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONDataStore implements DataStore {
    private final ObjectMapper objectMapper;

    public JSONDataStore() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void saveCourses(List<Course> courses, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, courses);
        }
    }

    @Override
    public List<Course> loadCourses(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return objectMapper.readValue(reader, new TypeReference<List<Course>>() {});
        } catch (Exception e) {
            throw new IOException("Error loading courses from JSON: " + e.getMessage());
        }
    }
}