package project.ncmc;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.ncmc.feature.ServiceCreationFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class ServiceCreationTest extends TestInit {

    ServiceCreationFeature serviceCreationFeature = new ServiceCreationFeature();

    @Test
    private void tc_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC007"));
        serviceCreationFeature.performServiceCreation();
    }
}
