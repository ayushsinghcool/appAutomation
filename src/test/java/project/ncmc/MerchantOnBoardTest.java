package project.ncmc;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import src.common.Assertion;
import src.initializers.TestInit;
import src.project.ncmc.feature.OnboardMerchantFetaure;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
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

    @Test
    private void test_001()  {
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC001"));
        onboardMerchantFetaure.merchantOnboard(
                "149015901978438",
                "https://nos-staging.paytm.com/nos/",
                "14015809");
        Assertion.verifyEqual(onboardMerchantPageObject.getErrorMessage(), MessageReader.getMessage("VALIDATION_MESSAGE_0002"));

    }
    @Test
    private void test_002()  {
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC002"));
        onboardMerchantFetaure.merchantOnboard(
                "149015901978438",
                "https://nos-staging.paytm.com/nos/",
                "14015808");
    }
}
