# Reworked project scope

- The main folder does not need to contain the runner class. All code can be placed in the test folder

  - This means that we can finally dynamically create and run tests
  
  - As a result, the appropriate changes were made
  

## Changes made from last commit

- Main.java was deleted. MyTestFactory.java (needs to be renamed) now acts as the runner class

- Added folder to com.example called "AssignmentFiles" to store files extracted from assignments

- Edited CPTC, MPTC, MVTC to adjust to Assignment File migration to new folder (com.example. -> com.example.AssignmentFiles)
 
## Next tasks

- Remove "run" functions from test cases and ASpec as "runDynamicTest" is now used

- Rename MyTestFactory appropriately

- Consider renaming getDynamicTest functions for test cases and ASpec
 
- Fix deleting error (deleting a file that isn't empty doesn't work)

- Revise project to determine anymore necessary changes before merging with main repositoy

- **Merge program with main repository**

