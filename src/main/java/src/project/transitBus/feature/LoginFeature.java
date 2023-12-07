package src.project.transitBus.feature;

import src.project.transitBus.pageObject.LoginPageObject;

public class LoginFeature {

    LoginPageObject loginPageObject = new LoginPageObject();

    public void performLogin() {
        loginPageObject.clickOnLoginBtn();
    }
}
