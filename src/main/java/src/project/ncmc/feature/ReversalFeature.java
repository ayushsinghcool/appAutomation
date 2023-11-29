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

public class ReversalFeature {

    CommonPageObject commonPageObject = new CommonPageObject();
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();
    BalanceUpdatePageObject balanceUpdatePageObject = new BalanceUpdatePageObject();


    public void performReversal() {
        ExtentTest node = ExtentManager.getTest();
        try {
            if (!BooleanController.getFirstTimeOnboarding()) {
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

            if(commonPageObject.isSecondTapOptionAvailable()){
                Assertion.verifyEqual(commonPageObject.getAlertTitle(), MessageReader.getMessage("VALIDATION_MESSAGE_0010"));
            }

            commonPageObject.clickOnCancelButton();

            commonPageObject.waitTillPostTransactionScreenDisplayed();
            Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0017"));

            node.info(commonPageObject.getCustomerReceipt());
            node.info(commonPageObject.getMerchantReceipt());
            node.info("Timer Present : " + commonPageObject.isTimerPresent());

            commonPageObject.clickOnNewPayment();

            CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);
            CommonUtils.createMethodLabel("Echo and Reversal");

            String requestRegex = "";
            String responseRegex = "";

            String[] reqRes = CaptureADBLog.fetchReqRes(requestRegex, responseRegex);

            node.info("Request : " + reqRes[0]);
            node.info("Response : " + reqRes[1]);

        } catch (Exception e) {
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }
}