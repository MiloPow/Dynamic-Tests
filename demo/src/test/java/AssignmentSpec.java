// package com.example;

import java.util.ArrayList;

public class AssignmentSpec {

    //Attributes
    
    private String name;

    private ArrayList<TestCase> testCases;

    //Constructors

    public AssignmentSpec(){

        testCases = new ArrayList<TestCase>();

    }

    public AssignmentSpec(String name){

        this.name = name;
        testCases = new ArrayList<TestCase>();

    }

    public AssignmentSpec(String name, ArrayList<TestCase> testCases){

        this.name = name;
        this.testCases = testCases;

    }

    //Accessors and Modifiers

    public String getName(){return name;}
    public ArrayList<TestCase> getTestCases(){return testCases;}

    public void setName(String name){this.name = name;}
    public void setTestCases(ArrayList<TestCase> testCases){this.testCases = testCases;}

    //Methods

    public void addTestCase(TestCase t){

        testCases.add(t);

    }

    public void addTestCases(ArrayList<TestCase> t){

        testCases.addAll(t);

    }

    public void runTestCases(){

        for(TestCase t : testCases){
            t.run();
        }

    }

}
