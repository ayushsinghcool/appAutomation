package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class BalanceEnquiryPageObject extends AppPageInit {

    public BalanceEnquiryPageObject(){
        super();
    }

    @AndroidFindBy(id = "tvBalanceEnquiry")
    private WebElement balanceEnquiry;
}
