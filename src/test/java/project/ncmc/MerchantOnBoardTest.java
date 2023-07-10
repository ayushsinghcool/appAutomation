package project.ncmc;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import src.common.Assertion;
import src.initializers.TestInit;
import src.project.ncmc.feature.OnboardMerchantFetaure;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;
import src.propertyManagement.MessageReader;


public class MerchantOnBoardTest extends TestInit {
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();
    OnboardMerchantPageObject onboardMerchantPageObject = new OnboardMerchantPageObject();

    @AfterMethod(alwaysRun = true)
    public void afterTest(){
       onboardMerchantFetaure.afterMethod();
    }

  //  @Test
    private void test_001()  {
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC001"));
        onboardMerchantFetaure.merchantOnboard(
                "1990999031",
                "https://acq-qa2-mw.ncmc-staging.paytmdgt.io/nos/",
                "15036764");
        Assertion.verifyEqual(onboardMerchantPageObject.getErrorMessage(), MessageReader.getMessage("VALIDATION_MESSAGE_0002"));

    }
    @Test
    private void test_002()  {
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC002"));
        onboardMerchantFetaure.merchantOnboard(
                ExecutionProperties.getProperty("nos.dsn"),
                ExecutionProperties.getProperty("nos.url"),
                ExecutionProperties.getProperty("nos.tid"));
    }
}
