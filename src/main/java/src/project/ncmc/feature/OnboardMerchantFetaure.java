package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.xpath.operations.Bool;
import src.common.Assertion;
import src.common.MobileDriver;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.CommonPageObject;
import src.project.ncmc.pageObject.OnboardMerchantPageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.MobileProperties;
import src.reportManagement.ExtentManager;
import src.propertyManagement.MessageReader;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;
public class OnboardMerchantFetaure {
    OnboardMerchantPageObject onboardMerchantPageObject = new OnboardMerchantPageObject();
    CommonPageObject commonPageObject = new CommonPageObject();
    public void merchantOnboard(String serialNumber, String url, String tid) {
        ExtentTest node = ExtentManager.getTest();
        try {
            if (BooleanController.getFirstTimeOnboarding()){
                BooleanController.setFirstTimeOnboarding(false);
                onboardMerchantPageObject.clickOnSetting()
                        .setSerialNumber(serialNumber)
                        .clickOnUpdateSerialNumber();
                commonPageObject.clickOnYesBtn();
                onboardMerchantPageObject.clickOnSetting().setEosUrl(
                                serialNumber.equals("149015901978438") ?
                                         url :
                                        ExecutionProperties.getProperty("eos.url"))
                        .clickOnUpdateEosUrl()
                        .clickOnUpdateEosUrl()
                        .setNcmcUrl(url)
                        .clickOnUpdateNcmcUrl()
                        .clickOnBackButton();
            }
            if(!BooleanController.getIsTIDActivated()) {
                onboardMerchantPageObject.clickOnActivationCode()
                        .setTid(tid)
                        .clickOnConfirmButton()
                        .waitTillLoaderDisplayed();
            }

            CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(),node);
            CommonUtils.createMethodLabel("Verify Terminal API");
            node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("{\"body\"",1)[0]);
            node.info("Response : " + "{\"head\":"+ CaptureADBLog.fetchReqRes("responseTimestamp",1)[1]);

            if (onboardMerchantPageObject.isDoneButtonDisplayed()) {
                Assertion.verifyEqual(onboardMerchantPageObject.getMessage(), MessageReader.getMessage("VALIDATION_MESSAGE_0001"));
                onboardMerchantPageObject.clickOnDoneButton();
                BooleanController.setIsTIDActivated(true);
                CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(),node);
                CommonUtils.createMethodLabel("Merchant Config API");
                node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("{\"body\"",1)[0]);
                node.info("Response : " + "{\"head\":"+ CaptureADBLog.fetchReqRes("responseTimestamp",1)[1]);
            }
            else{
                node.fail("Verify Termional Failed");
                CommonUtils.captureScreenMobile(node,"");
            }
        }
        catch (Exception e){
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
