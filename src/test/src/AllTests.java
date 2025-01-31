package src;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import src.StudentTest;

@Suite
@SelectClasses({CourseTest.class, StudentTest.class})
public class AllTests {
}