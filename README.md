**Changes made from last commit:**

- CPTC now works using DynamicTest

- TestCase interface now has the abstract method "runDynamicTest" so that it can be called on any type of TestCase
 
**Next tasks:**

- Implement runDynamicTest functions for MPTC and MVTC

- Fix deleting error found from last commit (deleting a file that isn't empty doesn't work)

