package project.ncmc;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.BalanceUpdateFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class BalanceUpdateTest extends TestInit {

    BalanceUpdateFeature balanceUpdateFeature = new BalanceUpdateFeature();

    @Test
    private void test_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC006"));
        balanceUpdateFeature.performBalnaceUpdate();
    }
}
