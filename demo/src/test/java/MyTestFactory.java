import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class MyTestFactory {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        // System.out.println("Press enter");

        // scan.nextLine();

        List<DynamicTest> myTests = exampleTestFactory();

        for(DynamicTest test : myTests){

            System.out.println(test.getDisplayName());

            // Class<?> c = test.getClass();

        }

        System.out.println("Tests ran!");

        // scan.nextLine();

        scan.close();
    }

    @TestFactory
    public static List<DynamicTest> exampleTestFactory() {
	
        return Arrays.asList(dynamicTest("Dynamic square " + 2, () -> assertEquals(4, 2 * 2)), dynamicTest("Dynamic true " + true, () -> assertTrue(true)));
    }
    
}
