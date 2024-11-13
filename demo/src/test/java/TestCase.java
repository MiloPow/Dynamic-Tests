import org.junit.jupiter.api.DynamicTest;

public interface TestCase {

    public Boolean runDynamicTest();

    public DynamicTest getDynamicTest();

}
