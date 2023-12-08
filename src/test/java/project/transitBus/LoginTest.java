package project.transitBus;

import org.testng.annotations.Test;
import src.initializers.TestInit;
import src.project.transitBus.feature.LoginFeature;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class LoginTest extends TestInit {

    LoginFeature loginFeature = new LoginFeature();

    @Test
    private void tc_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC0101"));
        loginFeature.performLogin();
    }
}
