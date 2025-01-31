import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CourseTest.class, StudentTest.class})
public class AllTests {
}