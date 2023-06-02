package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;
import src.utils.ApplicationInteractionActions;

public class CommonPageObject extends AppPageInit {
    public CommonPageObject(){
        super();
    }

    @AndroidFindBy(id ="ivProfile" )
    private WebElement menu  ;

    public CommonPageObject clickOnMenu(){
        isElementNotPresent(menu);
        clickOnElement(menu,"Hamburger Menu");
        return  this;
    }

    @AndroidFindBy(id ="tvTotalAmount" )
    private WebElement totalAmount  ;

    WebElement element;

    public CommonPageObject setAmount(String amount) {
        isElementNotPresent(totalAmount);
        for (char amt : amount.toCharArray()) {
            element = amt != '.' ? driver.findElement(By.id("btn" +amt)) :
            driver.findElement(By.id("btnDot"));
            clickOnElement(element,""+amt);
        }
        return this;
    }

    @AndroidFindBy(id ="tvTxnStatus" )
    private WebElement txnStatus  ;

    public String getTxnStatus(){
        isElementNotPresent(txnStatus);
        return txnStatus.getText();
    }
    @AndroidFindBy(id ="tvMerchantPrint" )
    private WebElement merchantReceipt  ;

    public String getMerchantReceipt(){
        return merchantReceipt.getText();
    }

    @AndroidFindBy(id ="tvPrint" )
    private WebElement customerReceipt  ;

    public String getCustomerReceipt(){
        return customerReceipt.getText();
    }

    @AndroidFindBy(id ="btnNewPayment" )
    private WebElement newPaymentBtn  ;

    public CommonPageObject clickOnNewPayment(){
        clickOnElement(newPaymentBtn,"New Payment Button");
        return this;
    }

    @AndroidFindBy(id = "tvTimerTop")
    private WebElement timer;

    public boolean isTimerPresent(){
        return timer.isDisplayed();
    }

    @AndroidFindBy(id = "tvRecentPayments")
    private WebElement recent;

    @AndroidFindBy(id = "tvDeviceDetails")
    private WebElement deviceDetails;

    public CommonPageObject scrollUpToDeviceDetails(){
        isElementNotPresent(recent);
        ApplicationInteractionActions.scrollDown();
        clickOnElement(deviceDetails,"Device Details");
        return this;
    }

    @AndroidFindBy(id = "btnRessign")
    private WebElement reAssign;

    public CommonPageObject clickOnReassign(){
        isElementNotPresent(reAssign);
        clickOnElement(reAssign,"Re-Assign");
        return this;
    }

    @AndroidFindBy(id = "android:id/button1")
    private WebElement yesBtn;
    @AndroidFindBy(id = "action_bar_root")
    private WebElement actionBar;
    @AndroidFindBy(id = "tvDebugSettings")
    private WebElement setting;

    public CommonPageObject clickOnYesBtn() {
        isElementClickable(actionBar);
        clickOnElement(yesBtn,"Yes button");
        isElementNotPresent(setting);
        return this;
    }

    public CommonPageObject waitTillLoaderPresent(){
        waitUntilElementDisappear("progressLayout");
        return this;
    }

}
