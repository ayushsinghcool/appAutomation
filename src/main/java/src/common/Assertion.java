package src.common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.reportManagement.ExtentManager;

import static src.utils.CommonUtils.captureScreenMobile;

public class Assertion {

    private Assertion(){
    }

    private static ExtentTest testNode;
    private static Markup m;

    private static final String FAIL = "Test Case Failed";
    private static final String ERROR = "StackTrace Result: ";

    private static void createAssertionLabel(){
        m = MarkupHelper.createLabel("Assertion Block", ExtentColor.GREEN);
        ExtentManager.getTest().info(m);
    }

    private static void createCodeBlock(String actual,String expected){
        String code = "Actual   :" +actual + "\nExpected :"+expected;
        //String code[][] = {{"Verify", "Equality of Assertion"}, {"Actual", actual}, {"Expected", expected}};
        Markup m = MarkupHelper.createCodeBlock(code);
        testNode.info(m);
    }

    public static void assertAPI(String actual,String expected){
        testNode = ExtentManager.getTest();
        try {
            createAssertionLabel();
            createCodeBlock(actual, expected);
            if (actual.equalsIgnoreCase(expected)) {
                testNode.pass(m);
            } else {
                testNode.fail(m);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            testNode.fail(ERROR);
            testNode.log(Status.INFO, ERROR + ExceptionUtils.getStackTrace(e));
        }
    }

    public static void verifyEqual(String actual,String expected){
        testNode = ExtentManager.getTest();
        try {
            createAssertionLabel();
            createCodeBlock(actual, expected);
            if (actual.equalsIgnoreCase(expected)) {
                testNode.pass(m);
            } else {
                testNode.fail(m);
            }
            captureScreenMobile(testNode, "");
        }
        catch (Exception e){
            e.printStackTrace();
            testNode.fail(FAIL);
            testNode.log(Status.INFO, ERROR + ExceptionUtils.getStackTrace(e));
        }
    }

    public static void verifyContains(String actual,String expected){
        testNode = ExtentManager.getTest();
        try{
            createAssertionLabel();
            createCodeBlock(actual,expected);
            if(actual.contains(expected)){
                testNode.pass("Assertion PASS");
            }else {
                testNode.fail("Assertion FAIL");
            }
            captureScreenMobile(testNode,"");
        }
        catch (Exception e){
            e.printStackTrace();
            testNode.fail(FAIL);
            testNode.log(Status.INFO, ERROR + ExceptionUtils.getStackTrace(e));
        }

    }
}
