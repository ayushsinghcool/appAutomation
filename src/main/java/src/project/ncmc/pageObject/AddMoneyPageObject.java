package src.project.ncmc.pageObject;

//scan below code and let me know any improvement

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class AddMoneyPageObject extends AppPageInit {

    public AddMoneyPageObject(){
        super();
    }

    WebElement element;
    @AndroidFindBy(id = "tvMoneyAddCash")
    private WebElement cash;

    @AndroidFindBy(id = "tvMoneyAddAccount")
    private WebElement account;

    public AddMoneyPageObject clickOnAddMoneyMenu(String type){
        element = type.equalsIgnoreCase("cash") ? cash : account;
        clickOnElement(element ,"Add Money "+ type);
        return this;
    }

}
