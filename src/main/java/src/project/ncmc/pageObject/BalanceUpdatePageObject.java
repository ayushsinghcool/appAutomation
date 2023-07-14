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

    public BalanceUpdatePageObject clickOnBalanceUpdateBtn(){
        isElementNotPresent(balanceUpdate);
        clickOnElement(balanceUpdate,"Balance Update Button Menu");
        return this;
    }

    @AndroidFindBy(id = "btnAction")
    private WebElement updateBalanceBtn;
    public BalanceUpdatePageObject clickOnUpdateBalance(){
        isElementNotPresent(updateBalanceBtn);
        clickOnElement(updateBalanceBtn,"Balance Update Button Button");
        return this;
    }

    @AndroidFindBy(id = "processBalanceUpdate")
    private WebElement processBalanceUpdate;

    public String getProcessBalanceUpdateText(){
        logInfo("Fetching Text...");
        return processBalanceUpdate.getText();
    }
}
