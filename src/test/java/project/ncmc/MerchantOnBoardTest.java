package project.ncmc;


import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.OnboardMerchantFetaure;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;


public class MerchantOnBoardTest extends TestInit {
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();

    @Test
    private void test_001()  {
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC001"));
        onboardMerchantFetaure.merchantOnboard(
                "149015901978438",
                "https://nos-staging.paytm.com/nos/",
                "14015808");
    }
}
