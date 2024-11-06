// package com.example;

public class ClassPresenceTC implements TestCase {

    //Attributes

    private String testCaseName;
    private String className;

    //Constructor

    public ClassPresenceTC(String className){

        this.className = /* "com.example." +*/ className;

        //Creates its own name based on class name

        testCaseName = "Class Presence check for: " + className;

    }

    //Accessors and Modifiers

    public String getTestCaseName(){return testCaseName;}
    public String getClassName(){return className;}

    public void setTestCaseName(String testCaseName){this.testCaseName = testCaseName;}
    public void setClassName(String className){this.className = className;}

    //Methods

    @Override
    public Boolean run(){

        // className = "com.example." + className;

        try{
        
            Class<?> c = Class.forName("com.example." + className);

            System.out.println("\nTest Case Passed: " + className + " Class Found!\n");

            return true;

        } catch(ClassNotFoundException e){

            System.out.println("\nTest Case Failed: " + className + " Class not found.\n");
        
            return false;

        }

    }

    @Override
    public String toString() {
        return testCaseName;
    }
    
    
}
