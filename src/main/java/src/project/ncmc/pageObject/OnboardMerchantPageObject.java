package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;


public class OnboardMerchantPageObject extends AppPageInit {
    public OnboardMerchantPageObject() {
        super();
    }

    @AndroidFindBy(id = "btnEnterCode")
    private WebElement enterActivationCode;

    public OnboardMerchantPageObject clickOnActivationCode() {
        clickOnElement(enterActivationCode,"TID");
        return this;
    }

    @AndroidFindBy(id = "tvDebugSettings")
    private WebElement setting;

    public OnboardMerchantPageObject clickOnSetting() {
        isElementNotPresent(setting);
        clickOnElement(setting,"Gear Icon");
        driver.navigate().back();
        return this;
    }

    @AndroidFindBy(id = "changedURL")
    private WebElement eosUrl;

    public OnboardMerchantPageObject setEosUrl(String url) {
        eosUrl.clear();
        setText(eosUrl, url,"EOS URL");
        return this;
    }

    @AndroidFindBy(id = "changedSN")
    private WebElement updateSerialNumber;

    public OnboardMerchantPageObject setSerialNumber(String serialNumber) {
        setText(updateSerialNumber, serialNumber,"Serial Number");
        return this;
    }

    @AndroidFindBy(id = "btnSubmitSN")
    private WebElement serialNumberBtn;

    public OnboardMerchantPageObject clickOnUpdateSerialNumber() {
        clickOnElement(serialNumberBtn,"Serial Number Button");
        return this;
    }

    @AndroidFindBy(id = "android:id/button1")
    private WebElement yesBtn;

    public OnboardMerchantPageObject clickOnYesBtn() {
        isElementNotPresent(yesBtn);
        clickOnElement(yesBtn,"Yes button");
        clickOnSetting();
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



}