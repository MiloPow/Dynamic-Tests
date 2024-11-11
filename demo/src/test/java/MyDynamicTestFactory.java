import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DynamicTest;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.Assert.*;
import org.junit.jupiter.api.TestFactory;

public class MyDynamicTestFactory {

    public static void main(String[] args) {

        List<DynamicTest> dynamicTests = Arrays.asList(
            dynamicTest("1st dynamic test", () -> assertTrue(true)),
            dynamicTest("2nd dynamic test", () -> assertEquals(4, 2+2))
        );

        Scanner scan = new Scanner(System.in);

        runTestFactory(scan, dynamicTests);


    }

    @TestFactory
    public static List<DynamicTest> testFactory(List<DynamicTest> dynamicTests) {

        return dynamicTests;

    }

    public static void runTestFactory(Scanner scan, List<DynamicTest> dynamicTests){
        clr();

        for(DynamicTest test : dynamicTests){


            System.out.print("Test Case: ");

            System.out.print(test.getDisplayName() + "Result: ");

            try{

                test.getExecutable().execute();

                System.out.println(" Test Case passed!");

            } catch(Throwable t){

                System.out.println(" Test Case Failed!");

            }

        }

        promptAndClear(scan);
    }

    public static void clr(){

        System.out.print("[H[2J");
        System.out.flush();

    }

    public static void promptAndClear(Scanner scan){

        System.out.print("Press Enter to continue...");
        scan.nextLine();

        System.out.print("[H[2J");
        System.out.flush();

    }

}

