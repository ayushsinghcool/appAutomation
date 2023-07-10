package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;
import src.utils.ApplicationInteractionActions;
import src.utils.CommonUtils;


public class OnboardMerchantPageObject extends AppPageInit {
    public OnboardMerchantPageObject() {
        super();
    }

    @AndroidFindBy(id = "btnEnterCode")
    private WebElement enterActivationCode;

    public OnboardMerchantPageObject clickOnActivationCode() {
        isElementNotPresent(enterActivationCode);
        clickOnElement(enterActivationCode,"TID");
        return this;
    }

    @AndroidFindBy(id = "tvDebugSettings")
    private WebElement setting;

    public OnboardMerchantPageObject clickOnSetting() {
        isElementNotPresent(setting);
        clickOnElement(setting,"Gear Icon");
        return this;
    }

    @AndroidFindBy(id = "changedURL")
    private WebElement eosUrl;

    public OnboardMerchantPageObject setEosUrl(String url) {
        isElementNotPresent(eosUrl);
        eosUrl.clear();
        setText(eosUrl, url,"EOS URL");
        return this;
    }

    @AndroidFindBy(id = "tvNcmcUrl")
    private WebElement ncmcUrl;

    public OnboardMerchantPageObject setNcmcUrl(String url) {
        ApplicationInteractionActions.scrollDown();
        isElementNotPresent(ncmcUrl);
        ncmcUrl.clear();
        setText(ncmcUrl, url,"NCMC URL");
        return this;
    }

    @AndroidFindBy(id = "btnSubmitNcmcURL")
    private WebElement updateNcmcUrlBtn;

    public OnboardMerchantPageObject clickOnUpdateNcmcUrl() {
        clickOnElement(updateNcmcUrlBtn,"Update NCMC Url");
        //driver.navigate().back();
        ApplicationInteractionActions.scrollUp();
        return this;
    }

    @AndroidFindBy(id = "changedSN")
    private WebElement updateSerialNumber;

    public OnboardMerchantPageObject setSerialNumber(String serialNumber) {
        isElementNotPresent(updateSerialNumber);
        setText(updateSerialNumber, serialNumber,"Serial Number");
        return this;
    }

    @AndroidFindBy(id = "btnSubmitSN")
    private WebElement serialNumberBtn;

    public OnboardMerchantPageObject clickOnUpdateSerialNumber() {
        clickOnElement(serialNumberBtn,"Serial Number Button");
        return this;
    }

    @AndroidFindBy(id = "android:id/button2")
    private WebElement noBtn;

    public OnboardMerchantPageObject clickOnNoBtn() {
        clickOnElement(noBtn,"No Button");
        return this;
    }

    @AndroidFindBy(id = "btnSubmitURL")
    private WebElement updateEosBtn;

    public OnboardMerchantPageObject clickOnUpdateEosUrl() {
        clickOnElement(updateEosBtn,"Update EOS Url");
        return this;
    }

    @AndroidFindBy(id = "ivBack")
    private WebElement backButton;
    public OnboardMerchantPageObject clickOnBackButton() {
        clickOnElement(backButton,"Back Button");
        return this;
    }
    public boolean isBackButtonDisplayed(){
         return driver.findElements(By.id("ivBack")).size()>0 ;
    }

    @AndroidFindBy(id = "etActivationCode")
    private WebElement activationCode;

    public OnboardMerchantPageObject setTid(String tid) {
        isElementNotPresent(activationCode);
        setText(activationCode,tid,"TID");
        return this;
    }

    @AndroidFindBy(id = "btn_widget_processing")
    private WebElement confirmButton;

    public OnboardMerchantPageObject clickOnConfirmButton() {
        clickOnElement(confirmButton,"Confirm Button");
        return this;
    }

    @AndroidFindBy(id = "btnAction")
    private WebElement doneBtn;
    @AndroidFindBy(id ="ivProfile" )
    private WebElement menu  ;
    public OnboardMerchantPageObject clickOnDoneButton(){
        isElementNotPresent(doneBtn);
        clickOnElement(doneBtn,"Done Button");
        isElementNotPresent(menu);
        return this;
    }
    public boolean isDoneButtonDisplayed(){
        return driver.findElements(By.id("btnAction")).size()>0;
    }
    public void waitTillLoaderDisplayed(){
        waitUntilElementDisappear("btn_widget_processing");
        CommonUtils.pauseExecution(5);
    }

    @AndroidFindBy(id="tvMessage")
    private WebElement message;
    public String getMessage(){
        isElementNotPresent(message);
        logInfo("Fetching Message from Device...");
        return message.getText();
    }

    @AndroidFindBy(id="ivClose")
    private WebElement close;
    public OnboardMerchantPageObject clickOnCloseButton(){
        isElementNotPresent(close);
        clickOnElement(close,"Close Button");
        return this;
    }

    @AndroidFindBy(id = "tvActivationError")
    private WebElement errormsg;
    public String getErrorMessage(){
        isElementNotPresent(errormsg);
        logInfo("Fetching Message from Device...");
        return errormsg.getText();
    }



}