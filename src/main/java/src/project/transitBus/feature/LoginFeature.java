package src.project.transitBus.feature;

import com.aventstack.extentreports.ExtentTest;
import src.project.transitBus.pageObject.LoginPageObject;
import src.reportManagement.ExtentManager;
import src.utils.CommonUtils;

public class LoginFeature {

    LoginPageObject loginPageObject = new LoginPageObject();

    public void performLogin() {
        ExtentTest node = ExtentManager.getTest();
        loginPageObject.clickOnLoginBtn();
        CommonUtils.captureScreenMobile(node,"Login Screen");

    }
}
