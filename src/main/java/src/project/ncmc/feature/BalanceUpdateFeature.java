package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.BalanceUpdatePageObject;
import src.project.ncmc.pageObject.CommonPageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.MessageReader;
import src.reportManagement.ExtentManager;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;

public class BalanceUpdateFeature {

    CommonPageObject commonPageObject = new CommonPageObject();
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();

    BalanceUpdatePageObject balanceUpdatePageObject = new BalanceUpdatePageObject();


    public void performBalnaceUpdate() {
        ExtentTest node = ExtentManager.getTest();
        try {
            if (BooleanController.getFirstTimeOnboarding()) {
                onboardMerchantFetaure.merchantOnboard(
                        ExecutionProperties.getProperty("nos.dsn"),
                        ExecutionProperties.getProperty("nos.url"),
                        ExecutionProperties.getProperty("nos.tid"));
            }

            commonPageObject.clickOnMenu().scrollUpToDeviceDetails();
            balanceUpdatePageObject.clickOnBalanceUpdateBtn().clickOnUpdateBalance();

            commonPageObject.waitTillLoaderDisplayed();

            Assertion.verifyEqual(balanceUpdatePageObject.getProcessBalanceUpdateText(), MessageReader.getMessage("VALIDATION_MESSAGE_0014"));
            commonPageObject.waitTillProcessing();

            if (!commonPageObject.waitTillPostTransactionScreenDisplayed()) {
                Assertion.verifyEqual(commonPageObject.getAlertTitle(), MessageReader.getMessage("VALIDATION_MESSAGE_0010"));
                Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0013"));
            } else {
                Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0013"));
            }

            node.info(commonPageObject.getCustomerReceipt());
            node.info(commonPageObject.getMerchantReceipt());
            node.info("Timer Present : " + commonPageObject.isTimerPresent());

            commonPageObject.clickOnNewPayment();

            CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);
            CommonUtils.createMethodLabel("Balance Update API");
            node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("encryptedTrack2", 1)[0]);
            node.info("Response : " + "{\"head\":" + CaptureADBLog.fetchReqRes("responseTimestamp", 1)[1]);

            CommonUtils.createMethodLabel("Echo and Reversal");
            node.info("Request : " + "{\"body\":{\"echo\":{\"body\":" + CaptureADBLog.fetchReqRes("echo", 2)[0]);
            node.info("Response : " + "{\"body\":" + CaptureADBLog.fetchReqRes("isReversalRequired", 1)[0] +
                    "\"body\":" + CaptureADBLog.fetchReqRes("isReversalRequired", 2)[0]);

        } catch (Exception e) {
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }
}
