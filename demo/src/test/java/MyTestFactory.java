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

        List<DynamicTest> myTests = exampleTestFactory();

        for(DynamicTest test : myTests){


            System.out.print("Test display name:");

            System.out.println(test.getDisplayName());

            try{

                test.getExecutable().execute();
                
                System.out.println("Test Case passed!");

            } catch(Throwable t){

                System.out.println("Test Case Failed: " + t);

            }

            

            // Class<?> c = test.getClass();

        }

        System.out.println("Tests ran!");

        // scan.nextLine();

        scan.close();
    }

    @TestFactory
    public static List<DynamicTest> exampleTestFactory() {
	
        return Arrays.asList(dynamicTest("Dynamic square " + 2, () -> assertEquals(5, 2 * 2)), dynamicTest("Dynamic true " + true, () -> assertTrue(true)));
    }
    
}
