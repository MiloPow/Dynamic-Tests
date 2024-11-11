// package com.example;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodValueTC implements TestCase {

    //Attributes

    private String testCaseName;
    private String className;
    private String methodName;
    private ArrayList<String> methodParams; //for every 2 values, the first value would indicate the data type of the param and the second would indicate the actual value
    private ArrayList<Object> trueParams;
    private Object expectedValue;

    //Constructor

    public MethodValueTC(String className, String methodName, ArrayList<String> methodParams, Object expectedValue){

        this.className = className;
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.expectedValue = expectedValue;

        getTrueMethodParams();

        //Creates its own name based on class name

        testCaseName = "Method Value check for: " + className + "." + methodName + "(";

        //Add parameters to testCaseName

        for(int x = 1;x < methodParams.size();x = x + 2){

            if(x != methodParams.size() - 1)
                testCaseName = testCaseName + methodParams.get(x) + ", ";
            else
                testCaseName = testCaseName + methodParams.get(x);

        }

        testCaseName = testCaseName + ") Expected: " + expectedValue.toString();

        //Determine true params based on string array of params

        getTrueMethodParams();

    }

    //Accessors and Modifiers

    public String getTestCaseName(){return testCaseName;}
    public String getClassName(){return className;}
    public String getMethodName(){return methodName;}
    public ArrayList<String> getMethodParamString(){return methodParams;}
    public Object getExpectedValue(){return expectedValue;}

    public String getExpectedValueType(){

        if(expectedValue instanceof Integer){

            return "int";

        }
        else if(expectedValue instanceof String){

            return "String";

        }
        else if(expectedValue instanceof Boolean){

            return "Boolean";

        }

        return null;

    }

    public ArrayList<Object> getTrueMethodParams(){

        //List to be returned once data types are determined and added

        trueParams = new ArrayList<Object>();

        for(int x = 0; x < methodParams.size();x = x + 2){

            //Not for loop increments in 2s because data is stored in pairs
            //First value in each pair indicates data type
            //Second indicates actual value

            if(methodParams.get(x).equals("int")){

                //Initialise int var

                int var = Integer.valueOf(methodParams.get(x + 1));

                //Add var to trueParams list

                trueParams.add(var);

            }
            else if(methodParams.get(x).equals("String")){

                //Initialise String var

                String var = methodParams.get(x + 1);

                //Add var to trueParams list

                trueParams.add(var);
                
            }
            else if(methodParams.get(x).equals("Boolean")){

                //Initialise Boolean var

                Boolean var = Boolean.valueOf(methodParams.get(x + 1));

                //Add var to trueParams list

                trueParams.add(var);
                
            }

        }


        return trueParams;

    }

    public void setTestCaseName(String testCaseName){this.testCaseName = testCaseName;}
    public void setClassName(String className){this.className = className;}
    public void setMethodName(String methodName){this.methodName = methodName;}
    public void setMethodParamString(ArrayList<String> methodParams){this.methodParams = methodParams;}
    public void setTrueMethodParams(ArrayList<Object> trueParams){this.trueParams = trueParams;}
    public void setExpectedValue(Object expectedValue){this.expectedValue = expectedValue;}
    
    //Methods

    @Override
    public Boolean run() {

        try{

            //Create Boolean for if the method is found so success or error output can be displayed appropriately
        
            Boolean found = false;

            //Get a reference to the class
        
            Class<?> c = Class.forName("com.example." + className);

            //Get object so method can be invoked

            Object instance = c.getDeclaredConstructor().newInstance();
            
            //Get the class's methods

            Method[] methods = c.getMethods();

            //Search for the method and when found run test based on given parameters

            for(Method m : methods){
                
                if(m.getName().equals(methodName)){

                    //If method found run test

                    try{

                        //Invoke method and check if the return value matches the expected value
                        

                        if(m.invoke(instance, trueParams.toArray()).equals(expectedValue)){

                            //print success

                            found = true;
                            // System.out.println("\nTest Case successful! Return value matches expected value.\n");

                            // return true;

                        }

                    } catch(Exception e){

                        System.out.println("Error invoking method: " + e);

                        //Error invoking method: java.lang.IllegalArgumentException: object is not an instance of declaring class

                        return false;

                    }

                }

            }

            //Print result

            if(found){
                System.out.println("\nTest Case Passed: " + testCaseName + " returns the expected value!\n");
                return true;
            }
            else
                System.out.println("\nTest Case Failed: " + testCaseName + " does not return the expected value.\n");

        } catch(Exception e){

            System.out.println("\nTest Case Failed: " + testCaseName + " does not return the expected value.\n");
        
            return false;

        }

        return false;
    }
    @Override
    public Boolean runDynamicTest() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String toString() {
        
        return testCaseName;

    }
}
