package src.project.transitBus.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class LoginPageObject extends AppPageInit {

    public LoginPageObject() {
        super();
    }

    @AndroidFindBy(id = "loginBtn")
    private WebElement login;


    public LoginPageObject clickOnLoginBtn() {
        clickOnElement(login, "Login");
        return this;
    }
}
