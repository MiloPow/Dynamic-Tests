package com.example;

import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javax.tools.JavaCompiler;

public class Main {
    public static void main(String[] args) {

        createFile();

        String filePath = "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\src\\test\\java\\SimpleClass.java";

        boolean compilationResult = compileFile(filePath);

        if (compilationResult) {
            runFile("SimpleClass");
        } else {
            System.out.println("Compilation failed.");
        }
    }

    public static void createFile(){

        String className = "SimpleClass";

        String code = getSimpleCodeString();// getCodeString();

        String folder = "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\src\\test\\java";

        try{

            File file = new File(folder + "\\" + className + ".java");

            try(FileWriter writer = new FileWriter(file)){
                
                writer.write(code);
            
            }




        } catch(Exception e){

            System.out.println("Error created file: " + e);

        }

    }

    public static boolean compileFile(String filePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) return false;

        // Classpath for JUnit and compiled classes
        String classpath = "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\target\\test-classes" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\target\\test-classes" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\junit\\jupiter\\junit-jupiter-api\\5.7.1\\junit-jupiter-api-5.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\junit\\platform\\junit-platform-commons\\1.7.1\\junit-platform-commons-1.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\apiguardian\\apiguardian-api\\1.1.0\\apiguardian-api-1.1.0.jar";

        int compilationResult = compiler.run(null, null, null, "-classpath", classpath, filePath);

        return compilationResult == 0;
    }

    public static void runFile(String className) {
        String classpath = "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\target\\test-classes" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\target\\test-classes" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\junit\\jupiter\\junit-jupiter-api\\5.7.1\\junit-jupiter-api-5.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\junit\\platform\\junit-platform-commons\\1.7.1\\junit-platform-commons-1.7.1.jar" +
                           File.pathSeparator + "C:\\Users\\SHAHZAD\\.m2\\repository\\org\\apiguardian\\apiguardian-api\\1.1.0\\apiguardian-api-1.1.0.jar";

        ProcessBuilder processBuilder = new ProcessBuilder("java", "-classpath", classpath, className);
        processBuilder.directory(new File("C:\\Users\\SHAHZAD\\Documents\\UWI\\COMP3607\\Dynamic-Tests\\demo\\target\\test-classes"));

        try {
            Process p = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            int exitCode = p.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getSimpleCodeString(){

        return """
                import static org.junit.Assert.assertTrue;

                public class SimpleClass{
                
                    public static void main(String[] args){
                    
                        System.out.println("This is a simple class for debugging.");
                    
                    }

                }
                """;


    }

    public static String getCodeString(){
        return """
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

                                """;
    }
}
