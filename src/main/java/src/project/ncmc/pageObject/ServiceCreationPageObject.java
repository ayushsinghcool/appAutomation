package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class ServiceCreationPageObject extends AppPageInit {

    public ServiceCreationPageObject(){
        super();
    }

    @AndroidFindBy(id = "tvServiceCreation")
    private WebElement serviceCreation;

    public ServiceCreationPageObject clickOnServiceCreationBtn(){
        isElementNotPresent(serviceCreation);
        clickOnElement(serviceCreation,"Service Creation Button");
        return this;
    }

    @AndroidFindBy(id = "tvOsa")
    private WebElement tvOsa;

    public ServiceCreationPageObject clickOnOSA(){
        isElementNotPresent(tvOsa);
        clickOnElement(tvOsa,"Operator Service Area");
        return this;
    }

    @AndroidFindBy(id = "tvCsa")
    private WebElement tvCsa;

    public ServiceCreationPageObject clickOnCSA(){
        isElementNotPresent(tvCsa);
        clickOnElement(tvCsa,"Common Service Area");
        return this;
    }

    @AndroidFindBy(id = "btnAction")
    private WebElement btnAction;

    public ServiceCreationPageObject clickOnCreateService(){
        isElementNotPresent(btnAction);
        clickOnElement(btnAction,"Create Service");
        return this;
    }

    @AndroidFindBy(id = "msgSvCreation")
    private WebElement msgSvCreation;

    public String getSVCreationText(){
        logInfo("Fetching Text...");
        return msgSvCreation.getText();
    }

    @AndroidFindBy(id = "pbSvCreation")
    private WebElement pbSvCreation;

    public ServiceCreationPageObject waitTillSVLoaderDisplayed(){
        isElementNotPresent(pbSvCreation);
        return this;
    }

    public boolean waitTillSVProcessing(){
        waitUntilElementDisappear("pbSvCreation");
        return waitUntilElementDisappear("pbSvCreation");
    }
    @AndroidFindBy(id ="btnNewPaymentSvCreation" )
    private WebElement btnNewPaymentSvCreation  ;
    public boolean waitTillSVPostTransactionScreenDisplayed(){
       return isElementNotPresent(btnNewPaymentSvCreation);
    }

    public boolean isErrorCodeDisplayed(){
        return driver.findElements(By.id(" tvErrorCode")).size()>0;
    }

    public ServiceCreationPageObject clickOnSVNewPayment(){
        clickOnElement(btnNewPaymentSvCreation,"New Payment Button");
        return this;
    }

    @AndroidFindBy(id = "tvTimerSvCreation")
    private WebElement timer;

    public boolean isSVTimerPresent(){
        return timer.isDisplayed();
    }


    @AndroidFindBy(id = "tvErrorMsg")
    private WebElement tvErrorMsg;

    public String getSVPostTxnText(){
        logInfo("Fetching Text...");
        return tvErrorMsg.getText();
    }

}
