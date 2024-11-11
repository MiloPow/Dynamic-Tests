**Note:**

- Note that in the past few commits I said "X now works using DynamicTest" which was misleading since I wasn't calling a TestFactory function

**Changes made from last commit:**

- MyTestFactory now runs the TestFactory annotated function as opposed to calling runDynamicTest for every test case

- getDynamicTest was added as an abstract method for the TestCase interface as all TestCases need it
 - (MyTestFactory needs to be able to get the dynamic tests from the test cases)

- getDynamicTest has been implemented for CPTC
 
**Next tasks:**

- Implement getDynamicTest function for MPTC and MVTC
 
- Fix deleting error (deleting a file that isn't empty doesn't work)

