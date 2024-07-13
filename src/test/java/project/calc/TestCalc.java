package project.calc;

import org.testng.annotations.Test;
import src.common.Assertion;
import src.initializers.TestInit;
import src.project.calc.feature.Calcfeature;
import src.project.calc.pageObject.CommonPageObject;
import src.propertyManagement.TestCasesProperties;
import src.reportManagement.ExtentManager;

public class TestCalc extends TestInit {

    Calcfeature calcfeature = new Calcfeature();
    CommonPageObject commonPageObject = new CommonPageObject();

    @Test
    private void test_001(){
        ExtentManager.startTestFromProperty(pNode, TestCasesProperties.getTestCase("TC010"));
        calcfeature.performAction("123");
        Assertion.verifyEqual(commonPageObject.getExpression().replaceAll("= ",""),"1");
    }
}
