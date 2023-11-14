package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.CommonPageObject;
import src.project.ncmc.pageObject.ServiceCreationPageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.MessageReader;
import src.reportManagement.ExtentManager;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;

public class ServiceCreationFeature {
    CommonPageObject commonPageObject = new CommonPageObject();
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();

    ServiceCreationPageObject serviceCreationPageObject = new ServiceCreationPageObject();

    public void performServiceCreation() {
        ExtentTest node = ExtentManager.getTest();
        try {
            if (BooleanController.getFirstTimeOnboarding()) {
                onboardMerchantFetaure.merchantOnboard(
                        ExecutionProperties.getProperty("nos.dsn"),
                        ExecutionProperties.getProperty("nos.url"),
                        ExecutionProperties.getProperty("nos.tid"));
            }

            commonPageObject.clickOnMenu().scrollUpToDeviceDetails();
            serviceCreationPageObject
                    .clickOnServiceCreationBtn()
                    .clickOnCSA()
                    .clickOnCreateService()
                    .waitTillSVLoaderDisplayed();

            Assertion.verifyEqual(serviceCreationPageObject.getSVCreationText(), MessageReader.getMessage("VALIDATION_MESSAGE_0015"));

            serviceCreationPageObject.waitTillSVProcessing();
            serviceCreationPageObject.waitTillSVPostTransactionScreenDisplayed();

            if(!serviceCreationPageObject.isErrorCodeDisplayed()) {
                Assertion.verifyEqual(serviceCreationPageObject.getSVCreationText(), MessageReader.getMessage("VALIDATION_MESSAGE_0016"));
            }
            else{
                Assertion.verifyEqual(serviceCreationPageObject.getSVPostTxnText(), MessageReader.getMessage("VALIDATION_MESSAGE_0016"));
            }

            node.info("Timer Present : " + serviceCreationPageObject.isSVTimerPresent());

            serviceCreationPageObject.clickOnSVNewPayment();

            CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);
            CommonUtils.createMethodLabel("Create Service API");
            node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("encryptedTrack2", 1)[0]);
            node.info("Response : " + "{\"head\":" + CaptureADBLog.fetchReqRes("responseTimestamp", 1)[1]);

        } catch (Exception e) {
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }
}
