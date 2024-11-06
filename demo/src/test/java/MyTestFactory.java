import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.engine.discovery.DiscoverySelectors;

public class MyTestFactory {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Press enter to run tests");
        // scan.nextLine();

        // Set up JUnit launcher and discovery request
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(MyTestFactory.class))
                .build();

        // Set up a listener to capture the test results
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.execute(request, listener);

        // Print out a summary of test results
        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));

        System.out.println("Tests ran!");
        scan.close();
    }

    @TestFactory
    public static List<DynamicTest> exampleTestFactory() {
        return Arrays.asList(
            dynamicTest("Dynamic square " + 2, () -> assertEquals(4, 2 * 2)),
            dynamicTest("Dynamic true " + true, () -> assertTrue(true))
        );
    }
}
