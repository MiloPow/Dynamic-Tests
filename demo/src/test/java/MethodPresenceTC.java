// package com.example;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.DynamicTest;

public class MethodPresenceTC implements TestCase {

    //Attributes

    private String testCaseName;
    private String className;
    private String methodName;
    private ArrayList<String> methodParams; //for every 2 values, the first value would indicate the data type of the param and the second would indicate the actual value
    //Note MPTC only keeps track of methodParam types, not values since it doesn't need to test for a return value

    //Constructor

    public MethodPresenceTC(String className, String methodName, ArrayList<String> methodParams){

        this.className = /*"com.example." +*/ className;
        this.methodName = methodName;
        this.methodParams = methodParams;

        //Creates its own name based on class name

        testCaseName = "Method Presence check for: " + className + "." + methodName + "(";

        //Add parameters to testCaseName

        for(int x = 0;x < methodParams.size();x++){

            if(x != methodParams.size() - 1)
                testCaseName = testCaseName + methodParams.get(x) + ", ";
            else
                testCaseName = testCaseName + methodParams.get(x);

        }

        testCaseName = testCaseName + ")";

    }

    //Accessors and Modifiers

    public String getTestCaseName(){return testCaseName;}
    public String getClassName(){return className;}
    public String getMethodName(){return methodName;}
    public ArrayList<String> getMethodParamString(){return methodParams;}

    public void setTestCaseName(String testCaseName){this.testCaseName = testCaseName;}
    public void setClassName(String className){this.className = className;}
    public void setMethodName(String methodName){this.methodName = methodName;}
    public void setMethodParamString(ArrayList<String> methodParams){this.methodParams = methodParams;}

    //Methods

    @Override
    public Boolean run(){

        try{

            //Create Boolean for if the method is found so success or error output can be displayed appropriately
        
            Boolean found = false;

            Class<?> c = Class.forName("com.example." + className);

            Method[] methods = c.getMethods();

            for(Method m : methods){

                if(m.getName().equals(methodName)){


                    //Check if parameter types of equal by extracting parameter types into ArrayList
                    //then calling equals() to compay ArrayList with methodParams ArrayList

                    Class<?>[] paramTypes = m.getParameterTypes();

                    ArrayList<String> paramTypesArray = new ArrayList<String>();

                    for(int x = 0;x < paramTypes.length;x++){

                        paramTypesArray.add(paramTypes[x].getName());

                    }

                    if(paramTypesArray.equals(methodParams)){

                        found = true;

                    }

                }

            }

            if(found){
                System.out.println("\nTest Case Passed: " + className + "." + methodName + "() Method Found!\n");
                return true;
            }
            else
                System.out.println("\nTest Case Failed: " + className + "." + methodName + "() Method Not found\n (found = false)");

        } catch(ClassNotFoundException e){

            System.out.println("\nTest Case Failed: " + className + "." + methodName + "() Method Not found\n");
        
            return false;

        }

        return false;

    }

    @Override
    public Boolean runDynamicTest(){

        try{

            //Create Boolean for if the method is found so success or error output can be displayed appropriately
        
            Boolean found = false;

            Class<?> c = Class.forName("com.example." + className);

            Method[] methods = c.getMethods();

            //Create dt

            DynamicTest dt = dynamicTest(testCaseName, () -> assertTrue(true));

            for(Method m : methods){
                
                if(m.getName().equals(methodName)){


                    //Check if parameter types of equal by extracting parameter types into ArrayList
                    //then calling equals() to compay ArrayList with methodParams ArrayList

                    Class<?>[] paramTypes = m.getParameterTypes();

                    ArrayList<String> paramTypesArray = new ArrayList<String>();

                    for(int x = 0;x < paramTypes.length;x++){

                        paramTypesArray.add(paramTypes[x].getName());

                    }

                    if(paramTypesArray.equals(methodParams)){

                        found = true;

                    }

                }

            }

            if(found){

                try{

                    dt.getExecutable().execute();

                    System.out.println("\nTest Case Passed: " + className + "." + methodName + "() Method Found!\n");
                    
                    return true;

                }catch(Throwable throwable){

                    System.out.println("\nTest Case Failed: " + className + "." + methodName + "() Method Not found (dt failed)\n");

                    return false;

                }
                
            }
            else{

                System.out.println("\nTest Case Failed: " + className + "." + methodName + "() Method Not found (found = false)\n");

                return false;

            }
                

        } catch(ClassNotFoundException e){

            System.out.println("\nTest Case Failed: " + className + "." + methodName + "() Method Not found(ClassNotFoundException thrown)\n");
        
            return false;

        }

    }

    @Override
    public String toString() {
        return testCaseName;
    }

    
    
}
