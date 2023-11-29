package project.ncmc;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.BalanceUpdateFeature;
import src.project.ncmc.feature.ReversalFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class ReversalTest extends TestInit {
    ReversalFeature reversalFeature = new ReversalFeature();

    @Test
    private void test_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC008"));
        reversalFeature.performReversal();
    }
}
