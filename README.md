# The Purpose of this branch

- This branch was created to overhaul the structure of the program in an attempt to create a working solution

  - The idea is that Main.java will handle menus and most IO. This would require the moving of all relavent files to the main folder


- MyTestFactory will instead simply run the tests and display the results of the test

  - In order for this to work, Main.java will need to dynamically create the MyDynamicTestFactory.java file using a string

    - The string contains the code for MyDynamicTestFactory

    - The string will be modified based on the tests created by the user so that MyDynamicTestFactory can run the given tests


## Next tasks

- Currently there is an issue with importing org.junit.Assert.x when dynamically creating MyDynamicTestFactory.java

  - This is a critical error that _must_ be fix in order to be able to run DynamicTests

  - This error only occurs when compiling and running the file dynamically after creating it dynamically
 
  - If the file is created dynamically, then runned via pressing the VSCode run button (after the program to create it dynamically has ended), there will be no errors
 
    - Could it be that those imports can't be handled in Main because they are related to tests? (test imports don't work in main) 

 

- Fix deleting error (deleting a file that isn't empty doesn't work)

