import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.example.Calculator;

public class MyTestFactory {

    public static void main(String[] args) {

        //Create ASpec object based on file

        FileManager fileManager = new FileManager();

        AssignmentSpec aSpec = new AssignmentSpec();

        displayMainMenu(fileManager, aSpec);

        //Create DynamicTest objects for each test case

        //Execute DynamicTests and show results

        List<DynamicTest> myTests = exampleTestFactory();

        for(DynamicTest test : myTests){


            System.out.print("Test display name:");

            System.out.println(test.getDisplayName());

            try{

                test.getExecutable().execute();
                
                System.out.println("Test Case passed!");

                Class<?> c = test.getClass();

                Calculator calc = new Calculator();

                System.out.println("Class name: " + c.getName());

            } catch(Throwable t){

                System.out.println("Test Case Failed: " + t);

            }

            

            

        }

        System.out.println("Tests ran! Returning tests...");
    }

    @TestFactory
    public static List<DynamicTest> exampleTestFactory() {

        DynamicTest test = dynamicTest(null, null);
	
        return Arrays.asList(dynamicTest("Dynamic square " + 2, () -> assertEquals(5, 2 * 2)), dynamicTest("Dynamic true " + true, () -> assertTrue(true)));
    }

    public static void displayMainMenu(FileManager fileManager, AssignmentSpec aSpec){

        Scanner scan = new Scanner(System.in);

        //Variable for handling primary choice

        int choice = 0;

        //Primary menu for creating or loading ASpecs

        while(choice != 4){

            clr();

            //Options 1 & 2 takes user to secondary menu where they can modify the ASpec by adding test cases
            //In the secondary menu, only choosing exit will bring the user back to the primary menu
            //where they can run the assignment spec.

            System.out.println("1. Create new assignment specification"); 
            System.out.println("2. Load pre-existing assignment specification");
            System.out.println("3. Specify folder for loading and saving assignment specs");
            System.out.println("4. Exit");

            System.out.print("\nEnter selection: ");

            if(scan.hasNextLine())
                choice = Integer.parseInt(scan.nextLine());
            else
                System.out.println("No input lines left");

            if(choice == 1){

                createAssignmentSpec(fileManager, scan);

            }
            else if(choice == 2){

                loadAssignmentSpec(fileManager, scan, aSpec);

            }
            else if(choice == 3){

                specifyFilePath(scan);

            }

        }

        
        scan.close();
    }

    public static void createAssignmentSpec(FileManager fileManager, Scanner scan){

        System.out.print("Enter the Assignment Spec name: ");

        String aspecName = scan.nextLine();

        clr();

        if(fileManager.createAspecFile(aspecName)){

            System.out.println("File successfully created!\n\n");

        }
        else{

            System.out.println("File name already. If you wish to load an existing file, use the load option from the main menu.\n\n");

        }

        promptAndClear(scan);
    }

    public static void loadAssignmentSpec(FileManager fileManager, Scanner scan, AssignmentSpec aSpec){

        clr();

        System.out.println("Enter the name of the Assigment Spec you wish to load: ");

        aSpec.setName(scan.nextLine());

        if(fileManager.loadASpecFromFile(aSpec))
            System.out.println("File loaded successfully!\n\n");
        else
            System.out.println("Error loading file.\n\n");

        promptAndClear(scan);

    }

    public static void specifyFilePath(Scanner scan){

        System.out.println("This feature is yet to be implemented.");

    }

    public static void clr(){

        System.out.print("\033[H\033[2J");  
        System.out.flush();

    }

    public static void promptAndClear(Scanner scan){

        System.out.print("Press Enter to continue...");
        scan.nextLine();

        System.out.print("\033[H\033[2J");  
        System.out.flush();

    }
    
}
