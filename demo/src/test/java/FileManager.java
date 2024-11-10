// package com.example;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

public class FileManager {

    //Attributes

    private static String folder = "C:\\Users\\Anwar\\Documents\\UWI\\COMP 3607\\Group Project\\Dynamic-Tests\\Dynamic-Tests\\demo\\src\\main\\java\\com\\example\\ASpecs";
    private static String filePath = "C:\\Users\\Anwar\\Documents\\UWI\\COMP 3607\\Group Project\\Dynamic-Tests\\Dynamic-Tests\\demo\\src\\main\\java\\com\\example\\ASpecs";
    
    //Default constructor

    public FileManager(){


    }

    //Constructor for using default file path

    public FileManager(String aspecName){

        FileManager.setFilePath(filePath, aspecName);

        System.out.println("\n\nCURRENT FILE PATH: " + filePath + "\n\n");

    }

    //Constructor for accepting a new filePath

    public FileManager(String folder, String aspecName){

        FileManager.setFilePath(folder, aspecName);

    }

    //Accessors and Modifiers

    public String getFolder(){return folder;}
    public String getFilePath(){return filePath;}

    public static void setFolder(String folder){FileManager.folder = folder;}

    //Sets file path using the current folder and the given aspecName

    public static void setFilePath(String aspecName){FileManager.filePath = FileManager.folder + "\\" + aspecName + ".json";}

    //Sets file path based on given folder and aspecName

    public static void setFilePath(String folder, String aspecName){FileManager.filePath = folder + "\\" + aspecName + ".json";}

    //Methods

    public Boolean createAspecFile(String aspecName){


        FileManager.setFilePath(FileManager.folder, aspecName);

        File aspecFile = new File(FileManager.filePath);
        

        if(aspecFile.exists()){
            return false;
        }

        try{

            if(aspecFile.createNewFile())
                return true;

        } catch(Exception e){

            return false;

        }

        //Why does this need to have an extra return statement even tho save and load don't?

        return false;

    }

    public Boolean saveASpecToFile(AssignmentSpec aSpec){

        //Index for setting keys in json file to count/position

        int index = 1;

        //JSONObject to add to file

        JSONObject j = new JSONObject();

        //For every test case in the given ASpec, the test case is saved based on the type

        for(TestCase testCase : aSpec.getTestCases()){

            if(testCase instanceof ClassPresenceTC){
                
                addClassPTCToJO(index,(ClassPresenceTC)testCase, j);

            }
            else if(testCase instanceof MethodPresenceTC){

                addMethodPTCToJO(index, (MethodPresenceTC)testCase, j);

            }
            else if(testCase instanceof MethodValueTC){

                addMethodVTCToJO(index, (MethodValueTC)testCase, j);
                
            }

            index++;
        }

        //Attempt to save & return save status

        try{

            FileWriter writer = new FileWriter(FileManager.filePath);

            writer.write(j.toJSONString());

            writer.close();

            return true;

        } catch(Exception e){

            System.out.println("\n\nERROR: " + e + "\n\n");

            return false;

        }

    }

    //Must accept Assignment spec to load test cases into

    public Boolean loadASpecFromFile(AssignmentSpec aSpec){

        //Ensure file is not empty

        File f = new File(filePath);

        if(f.length() != 0){

            try{
    
                JSONParser parser = new JSONParser();
    
                JSONObject j = (JSONObject)parser.parse(new FileReader(filePath));
    
                //Only create objects if file isn't empty
    
                for(Object value : j.values()){
    
                    ArrayList<?> tcAttributes = (ArrayList<?>)value;
    
                    //Load test case depending on test case type
    
                    String tcType = tcAttributes.get(1).toString();
    
                    if(tcType.equals("ClassPresenceTC")){
    
                        ClassPresenceTC cpTestCase = loadClassPTCFromArrayList(tcAttributes);
                        aSpec.addTestCase(cpTestCase);
    
                    }
                    else if(tcType.equals("MethodPresenceTC")){
    
                        MethodPresenceTC mpTestCase = loadMethodPTCFromArrayList(tcAttributes);
                        aSpec.addTestCase(mpTestCase);
                        
                    }
                    else if(tcType.equals("MethodValueTC")){
    
                        MethodValueTC mvTestCase = loadMethodVTCFromArrayList(tcAttributes);
                        aSpec.addTestCase(mvTestCase);
                        
                    }
    
                }
    
                return true;
    
            } catch(Exception e){
        
                return false;
    
            }

        }

        return true;        

    }

    public Boolean deleteASpec(){

        File aspecFile = new File(filePath);

        //Debug

        System.out.println("Deleting: " + filePath);

        setFilePath(folder);

        return aspecFile.delete();

    }

    //The following 3 methods adds the given test case to json object

    public void addClassPTCToJO(int indexKey, ClassPresenceTC cpTestCase, JSONObject j){//int indexKey, ClassPresenceTC cpTestCase){

        //Create JArray for adding attributes

        JSONArray attributes = new JSONArray();

        attributes.add(cpTestCase.getTestCaseName());
        attributes.add("ClassPresenceTC");
        attributes.add(cpTestCase.getClassName());

        j.put(indexKey, attributes);
        

    }

    public void addMethodPTCToJO(int index, MethodPresenceTC mpTestCase, JSONObject j){

        //Create JArray for adding attributes and add attributes

        JSONArray attributes = new JSONArray();

        attributes.add(mpTestCase.getTestCaseName());
        attributes.add("MethodPresenceTC");
        attributes.add(mpTestCase.getClassName());
        attributes.add(mpTestCase.getMethodName());

        //Create JArray for adding methodParams

        JSONArray methodParams = stringArrayListToJarray(mpTestCase.getMethodParamString());

        //Add methodParams to attributes

        attributes.add(methodParams);

        j.put(index, attributes);

    }

    public void addMethodVTCToJO(int index, MethodValueTC mvTestCase, JSONObject j){

        //Create JArray for adding attributes and add attributes

        JSONArray attributes = new JSONArray();

        attributes.add(mvTestCase.getTestCaseName());
        attributes.add("MethodValueTC");
        attributes.add(mvTestCase.getClassName());
        attributes.add(mvTestCase.getMethodName());

        //Create JArray for adding methodParams

        JSONArray methodParams = stringArrayListToJarray(mvTestCase.getMethodParamString());

        //Add methodParams to attributes

        attributes.add(methodParams);

        //Create JArray for adding return value details

        JSONArray returnValue = new JSONArray();

        returnValue.add(mvTestCase.getExpectedValueType());
        returnValue.add(mvTestCase.getExpectedValue());

        //Add returnValue JArray to attributes JArray

        attributes.add(returnValue);

        //Add attributes to JSONObject

        j.put(index, attributes);


    }

    //For individual test cases, creating the test case from an array list is needed since
    //the loadTestCases function would read each test case as an array list which then
    //needs to be made into an actual test case so that it can be placed into an ASpec

    public ClassPresenceTC loadClassPTCFromArrayList(ArrayList<?> attributes){

        String className = attributes.get(2).toString();

        ClassPresenceTC cpTestCase = new ClassPresenceTC(className);

        return cpTestCase;

    }

    public MethodPresenceTC loadMethodPTCFromArrayList(ArrayList<?> attributes){

        String className = attributes.get(2).toString();
        String methodName = attributes.get(3).toString();
        ArrayList<String> methodParams = (ArrayList<String>)attributes.get(4);

        MethodPresenceTC mpTestCase = new MethodPresenceTC(className, methodName, methodParams);

        return mpTestCase;

    }

    public MethodValueTC loadMethodVTCFromArrayList(ArrayList<?> attributes){

        String className = attributes.get(2).toString();
        String methodName = attributes.get(3).toString();
        ArrayList<String> methodParams = new ArrayList<String>();
        ArrayList<String> returnValue = new ArrayList<String>();

        //Load methodParams into JArray then extract

        //For some reason I need to load methodParams into JSONArray first 
        //but I don't have to do that with return value evern though they are both arrays?

        JSONArray methodParamJarray = (JSONArray)attributes.get(4);

        methodParams = jarrayToStringArrayList(methodParamJarray);

        //Load return value array into JArray then extract

        JSONArray returnValueJarray = (JSONArray)attributes.get(5);

        returnValue = jarrayToStringArrayList(returnValueJarray); //return value is null. jarrayToString fun not returning correctly. Needed to add functionality

        //Get string for expected value type and create expectedValue object

        String expectedValueType = returnValue.get(0).toString();
        Object expectedValue = new Object();

        //Cast expectedValue object to appropriate type depending on expectedValueType String

        if(expectedValueType.equals("int")){

            expectedValue = Integer.valueOf(returnValue.get(1).toString());

        }
        else if(expectedValueType.equals("String")){

            expectedValue = returnValue.get(1).toString();
            
        }
        else if(expectedValueType.equals("Boolean")){

            expectedValue = Boolean.valueOf(returnValue.get(1).toString());
            
        }

        //Create MethodVTC using collected attributes

        MethodValueTC mvTestCase = new MethodValueTC(className, methodName, methodParams, expectedValue);

        return mvTestCase;

    }

    public ArrayList<String> jarrayToStringArrayList(JSONArray jArray){

        ArrayList<String> stringArrayList = new ArrayList<String>();

        for(int i = 0;i < jArray.size();i++){

            stringArrayList.add(jArray.get(i).toString());

        }

        return stringArrayList;
    
    }

    public JSONArray stringArrayListToJarray(ArrayList<String> stringArrayList){

        JSONArray j = new JSONArray();

        for(int i = 0;i < stringArrayList.size();i++){

            j.add(stringArrayList.get(i));

        }

        return j;

    }
    
}
