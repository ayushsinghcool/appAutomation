package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
import src.reportManagement.ExtentManager;
import src.utils.CommonUtils;
import src.utils.MessageReader;

public class OnboardMerchantFetaure {
    OnboardMerchantPageObject onboardMerchantPageObject = new OnboardMerchantPageObject();

    public void merchantOnboard(String serialNumber, String eosUrl, String tid) {
        ExtentTest node = ExtentManager.getTest();
        if (BooleanController.isFirstTimeOnboarding) {
            BooleanController.setFirstTimeOnboardingFalse();
            onboardMerchantPageObject.clickOnSetting()
                    .setSerialNumber(serialNumber)
                    .clickOnUpdateSerialNumber()
                    .clickOnYesBtn()
                    .setEosUrl(eosUrl)
                    .clickOnUpdateEosUrl()
                    .clickOnUpdateEosUrl()
                    .clickOnBackButton();
        }
        onboardMerchantPageObject.clickOnActivationCode()
                .setTid(tid)
                .clickOnConfirmButton()
                .WaitTillLoaderDisplayed();
        //CommonUtils.attachFileAsExtentLog(new FetchLogs().getServerLog(ServerCredentialsProperties.getProperty("server.logPath")), node);
        if (onboardMerchantPageObject.isDoneButtonDisplayed()) {
            Assertion.verifyEqual(onboardMerchantPageObject.getMessage(), MessageReader.getMessage("VALIDATION_MESSAGE_0001"));
            onboardMerchantPageObject.clickOnDoneButton();
        }
    }


    public void afterMethod() {
        if (onboardMerchantPageObject.isBackButtonDisplayed())
            onboardMerchantPageObject.clickOnBackButton();
    }

}
