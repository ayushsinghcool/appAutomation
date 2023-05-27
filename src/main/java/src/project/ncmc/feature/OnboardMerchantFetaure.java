package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
import src.reportManagement.ExtentManager;
import src.propertyManagement.MessageReader;

public class OnboardMerchantFetaure {
    OnboardMerchantPageObject onboardMerchantPageObject = new OnboardMerchantPageObject();

    public void merchantOnboard(String serialNumber, String eosUrl, String tid) {
        try {
            if (BooleanController.getFirstTimeOnboarding()){
                BooleanController.setFirstTimeOnboarding(false);
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
                    .waitTillLoaderDisplayed();
            //CommonUtils.attachFileAsExtentLog(new FetchLogs().getServerLog(ServerCredentialsProperties.getProperty("server.logPath")), node);
            if (onboardMerchantPageObject.isDoneButtonDisplayed()) {
                Assertion.verifyEqual(onboardMerchantPageObject.getMessage(), MessageReader.getMessage("VALIDATION_MESSAGE_0001"));
                onboardMerchantPageObject.clickOnDoneButton();
            }
        }
        catch (Exception e){
            ExtentTest node = ExtentManager.getTest();
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }


    public void afterMethod() {
        if (onboardMerchantPageObject.isBackButtonDisplayed())
            onboardMerchantPageObject.clickOnBackButton();
    }

}
