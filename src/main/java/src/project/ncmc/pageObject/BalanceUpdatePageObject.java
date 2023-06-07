package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class BalanceUpdatePageObject extends AppPageInit {

    public BalanceUpdatePageObject(){
        super();
    }

    @AndroidFindBy(id = "tvBalanceUpdate")
    private WebElement balanceUpdate;
}
