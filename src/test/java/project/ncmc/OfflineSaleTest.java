package project.ncmc;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.OfflineSaleFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class OfflineSaleTest extends TestInit {

    OfflineSaleFeature offlineSaleFeature = new OfflineSaleFeature();

    @Test
    private void test001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC003"));
        offlineSaleFeature.performOfflineSale("1");
    }

}
