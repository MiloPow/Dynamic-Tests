import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.ArrayList;
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

        // List<DynamicTest> myTests = exampleTestFactory();

        // for(DynamicTest test : myTests){


        //     System.out.print("Test display name:");

        //     System.out.println(test.getDisplayName());

        //     try{

        //         test.getExecutable().execute();
                
        //         System.out.println("Test Case passed!");

        //         Class<?> c = test.getClass();

        //         Calculator calc = new Calculator();

        //         System.out.println("Class name: " + c.getName());

        //     } catch(Throwable t){

        //         System.out.println("Test Case Failed: " + t);

        //     }

            

            

        // }

        // System.out.println("Tests ran! Returning tests...");
    }

    @TestFactory
    public static List<DynamicTest> exampleTestFactory() {

        DynamicTest test = dynamicTest("Dynamic Test", () -> assertEquals(4, 2 * 2));
        
	
        return Arrays.asList(dynamicTest("Dynamic square " + 2, () -> assertEquals(5, 2 * 2)), dynamicTest("Dynamic true " + true, () -> assertTrue(true)));
    }

    public static void displayMainMenu(FileManager fileManager, AssignmentSpec aSpec){

        Scanner scan = new Scanner(System.in);

        //Variable for handling primary choice

        int choice = 0;

        //Primary menu for creating or loading ASpecs

        while(choice != 5){

            clr();

            //Options 1 & 2 takes user to secondary menu where they can modify the ASpec by adding test cases
            //In the secondary menu, only choosing exit will bring the user back to the primary menu
            //where they can run the assignment spec.

            System.out.println("1. Create new assignment specification"); 
            System.out.println("2. Load pre-existing assignment specification");
            System.out.println("3. Specify folder for loading and saving assignment specs");
            System.out.println("4. Delete Assignment Spec");
            System.out.println("5. Exit");

            System.out.print("\nEnter selection: ");

            if(scan.hasNextLine())
                choice = Integer.parseInt(scan.nextLine());
            else
                System.out.println("No input lines left");

            if(choice == 1){

                createAssignmentSpec(fileManager, scan, aSpec);

            }
            else if(choice == 2){

                loadAssignmentSpec(fileManager, scan, aSpec);

            }
            else if(choice == 3){

                specifyFolder(scan);

            }
            else if(choice == 4){

                deleteAssignmentSpec(scan, fileManager, aSpec.getName());

            }

        }

        
        scan.close();
    }

    public static void createAssignmentSpec(FileManager fileManager, Scanner scan, AssignmentSpec aSpec){

        System.out.print("Enter the Assignment Spec name: ");

        String aspecName = scan.nextLine();

        clr();

        if(fileManager.createAspecFile(aspecName)){

            System.out.println("File successfully created!\n\n");

            promptAndClear(scan);

            displayTCMenu(scan, aSpec, fileManager);

        }
        else{

            System.out.println("File name already. If you wish to load an existing file, use the load option from the main menu.\n\n");

            promptAndClear(scan);

        }
        
    }

    public static void loadAssignmentSpec(FileManager fileManager, Scanner scan, AssignmentSpec aSpec){

        clr();

        System.out.print("Enter the name of the Assigment Spec you wish to load: ");

        aSpec.setName(scan.nextLine());

        FileManager.setFilePath(aSpec.getName());

        if(fileManager.loadASpecFromFile(aSpec))
            System.out.println("File loaded successfully!\n\n");
        else
            System.out.println("Error loading file.\n\n");

        promptAndClear(scan);

        displayTCMenu(scan, aSpec, fileManager);
    }

    public static void specifyFolder(Scanner scan){

        clr();;

        System.out.println("Enter the file path for the folder you wish to use.\n");

        System.out.println("Note that for every backslash, you must enter 2 instead of 1.\n");

        System.out.print("Specify File path: ");

        String folder = scan.nextLine();

        FileManager.setFolder(folder);

        System.out.println("Folder loaded successfully!");

        promptAndClear(scan);

    }

    public static void displayTCMenu(Scanner scan, AssignmentSpec aSpec, FileManager fileManager){

        int choice = 0;

        while(choice != 6){

            clr();

            System.out.println("1. Create test case for class presence");
            System.out.println("2. Create test case for method presence");
            System.out.println("3. Create test case for method return value");
            System.out.println("4. View created test cases");
            System.out.println("5. Save assignment specification");
            System.out.println("6. Exit");

            System.out.print("\nEnter selection: ");

            choice = Integer.parseInt(scan.nextLine());
            clr();

            if(choice == 1){

                //Create test case for class presence

                ClassPresenceTC cpTestCase = createCPTest(scan);

                aSpec.addTestCase(cpTestCase);

            }
            else if(choice == 2){

                //Create test case for method 
                
                MethodPresenceTC mpTestCase = createMPTest(scan);

                aSpec.addTestCase(mpTestCase);

            }
            else if(choice == 3){

                //Create test case for method return value

                MethodValueTC mvTestCase = createMVTest(scan);

                aSpec.addTestCase(mvTestCase);

            }
            else if(choice == 4){

                //View test cases

                clr();

                printTestCases(aSpec.getTestCases());

            }
            else if(choice == 5){

                //Save assignment spec

                fileManager.saveASpecToFile(aSpec);

            }

        }

    }

    static ClassPresenceTC createCPTest(Scanner scan){

        clr();

        System.out.println("Enter the name of the class you wish to test for: ");

        String className = scan.nextLine();

        ClassPresenceTC cpTestCase = new ClassPresenceTC(className);

        return cpTestCase;

        // DynamicTest test = dynamicTest("Class presence check for: " + className, () -> assertEquals(scan, className));

        //Since we dont know what values to compare at compile time. Dynamic tests must be created at run time

        //Dynamic tests must be created and executed at run time based on test case objects

    }

    static MethodPresenceTC createMPTest(Scanner scan){

        clr();

        System.out.print("Enter the name of the class that the method belongs to: ");

        String className = scan.nextLine();

        System.out.print("Enter the name of the method: ");

        String methodName = scan.nextLine();

        //Display menu and create loop for parameters

        ArrayList<String> parameters = getMPParameters(scan);

        MethodPresenceTC mpTestCase = new MethodPresenceTC(className, methodName, parameters);

        return mpTestCase;

    }

    static MethodValueTC createMVTest(Scanner scan){

        clr();

        System.out.print("Enter the name of the class that the method belongs to: ");

        String className = scan.nextLine();

        System.out.print("Enter the name of the method: ");

        String methodName = scan.nextLine();

        //Display menu and get parameters

        ArrayList<String> parameters = getMVParameters(scan);

        Object expectedValue = getMVReturnValue(scan);

        MethodValueTC mvTestCase = new MethodValueTC(className, methodName, parameters, expectedValue);

        return mvTestCase;

    }

    public static ArrayList<String> getMPParameters(Scanner scan){
        
        int choice = 0;
        
        //Create string array for parameters

        ArrayList<String> parameters = new ArrayList<String>();

        while(choice != 4){

            clr();

            System.out.println("1. Add parameter of type int");
            System.out.println("2. Add parameter of type String");
            System.out.println("3. Add parameter of type Boolean");
            System.out.println("4. Complete parameters");

            System.out.print("\nEnter selection: ");

            choice = Integer.parseInt(scan.nextLine());

            if(choice == 1){

                parameters.add("int");

            }
            else if(choice == 2){

                parameters.add("String");

            }
            else if(choice == 3){

                parameters.add("Boolean");

            }

        }

        return parameters;

    }

    public static ArrayList<String> getMVParameters(Scanner scan){

        int choice = 0;
        
        //Create string array for parameters

        ArrayList<String> parameters = new ArrayList<String>();

        while(choice != 4){

            clr();

            System.out.println("1. Add parameter of type int");
            System.out.println("2. Add parameter of type String");
            System.out.println("3. Add parameter of type Boolean");
            System.out.println("4. Complete parameters");

            System.out.print("\nEnter selection: ");

            choice = Integer.parseInt(scan.nextLine());

            if(choice == 1){

                parameters.add("int");

                System.out.print("\nEnter int value for test parameter: ");

                parameters.add(scan.nextLine());

            }
            else if(choice == 2){

                parameters.add("String");

                System.out.print("\nEnter String value for test parameter: ");

                parameters.add(scan.nextLine());

            }
            else if(choice == 3){

                parameters.add("Boolean");

                System.out.print("\nEnter Boolean value for test parameter: ");

                parameters.add(scan.nextLine());

            }

        }

        return parameters;

    }

    public static Object getMVReturnValue(Scanner scan){

        System.out.println("Select the data type for the expected return value:\n");
        System.out.println("1. int");
        System.out.println("2. String");
        System.out.println("3. Boolean\n");

        System.out.print("Enter selection: ");

        int selection = Integer.parseInt(scan.nextLine());

        clr();

        //Create expected value object and get expected value from user
                        
        Object expectedValue = new Object();

        System.out.print("Enter the expected return value for the method: ");

        String expectedValueString = scan.nextLine();

        //Assign expectedValue based on data type

        if(selection == 1){

            expectedValue = Integer.valueOf(expectedValueString);

        }
        else if(selection == 2){

            expectedValue = expectedValueString;


        }
        else if(selection == 3){

            expectedValue = Boolean.valueOf(expectedValueString);

        }

        return expectedValue;

    }

    public static void deleteAssignmentSpec(Scanner scan, FileManager fileManager, String aspecName){

        clr();

        if(aspecName == null){

            System.out.println("No spec is currently loaded\n\n");

            promptAndClear(scan);

            return;

        }

        //Note user must selected aspec. Should warn user of this

        System.out.println("Are you sure you want to delete " + aspecName + "?\n\n");

        int choice = 0;

        System.out.println("1. Yes");
        System.out.println("2. No\n\n");

        System.out.print("Enter selection: ");

        choice = Integer.parseInt(scan.nextLine());

        if(choice == 1){
            
            Boolean deletionSuccess = fileManager.deleteASpec();

            clr();

            if(deletionSuccess)
                System.out.println("File deleted successfully!\n\n");
            else
                System.out.println("Error deleting file.\n\n");
            
            promptAndClear(scan);
            
        
        }

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

    public static void printTestCases(ArrayList<TestCase> ts){

        int x = 1;

        for(TestCase t : ts){

            System.out.println("\n" + String.valueOf(x) + ". " + t.toString() + "\n");
            x++;

        }

    }
    
}
