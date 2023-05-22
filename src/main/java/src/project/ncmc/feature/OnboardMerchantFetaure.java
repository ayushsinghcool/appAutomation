package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
import src.propertyManagement.ServerCredentialsProperties;
import src.reportManagement.ExtentManager;
import src.utils.CommonUtils;
import src.utils.FetchLogs;

public class OnboardMerchantFetaure {
    OnboardMerchantPageObject onboardMerchantPageObject = new OnboardMerchantPageObject();

    public void merchantOnboard(String serialNumber, String eosUrl, String tid) {
        ExtentTest node = ExtentManager.getTest();
        onboardMerchantPageObject.clickOnSetting()
                .setSerialNumber(serialNumber)
                .clickOnUpdateSerialNumber()
                .clickOnYesBtn()
                .setEosUrl(eosUrl)
                .clickOnUpdateEosUrl()
                .clickOnUpdateEosUrl()
                .clickOnBackButton()
                .clickOnActivationCode()
                .setTid(tid)
                .clickOnConfirmButton();
        //CommonUtils.attachFileAsExtentLog(new FetchLogs().getServerLog(ServerCredentialsProperties.getProperty("server.logPath")), node);
        node.pass("Merchant Onboarded Successfully");
        CommonUtils.pauseExecution(3);
        CommonUtils.captureScreenMobile(node, "Card Machine Activated");

    }

}
