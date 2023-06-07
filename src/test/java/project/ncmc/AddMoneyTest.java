package project.ncmc;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.AddMoneyFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class AddMoneyTest extends TestInit {
    AddMoneyFeature addMoneyFeature = new AddMoneyFeature();

    @Test
    private void test_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC004"));
        addMoneyFeature.performAddMoney("account","270.01");
    }
}
