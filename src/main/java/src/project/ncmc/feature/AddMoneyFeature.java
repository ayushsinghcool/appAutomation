package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.AddMoneyPageObject;
import src.project.ncmc.pageObject.CommonPageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.MessageReader;
import src.reportManagement.ExtentManager;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;
import src.utils.ServerConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMoneyFeature {

    CommonPageObject commonPageObject = new CommonPageObject();
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();
    AddMoneyPageObject addMoneyPageObject = new AddMoneyPageObject();

    public void performAddMoney(String transactionType, String amount) {
        ExtentTest node = ExtentManager.getTest();
        try {
            if (BooleanController.getFirstTimeOnboarding()) {
                onboardMerchantFetaure.merchantOnboard(
                        ExecutionProperties.getProperty("nos.dsn"),
                        ExecutionProperties.getProperty("nos.url"),
                        ExecutionProperties.getProperty("nos.tid"));
            }

            commonPageObject.clickOnMenu().scrollUpToDeviceDetails();
            addMoneyPageObject.clickOnAddMoneyMenu(transactionType);
            commonPageObject.setAmount("270.01").clickOnCollectButton();

            //First Tap
            node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
            //node.info(commonPageObject.getScanQrText());
            Assertion.verifyEqual(commonPageObject.getTotalAmount(), "₹" + amount);

            //Second Tap
            commonPageObject.waitTillLoaderDisplayed();
            node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
            Assertion.verifyEqual(commonPageObject.getTotalAmount(), "₹" + amount);

            commonPageObject.waitTillProcessing();


            if(commonPageObject.isSecondTapOptionAvailable()){
                Assertion.verifyEqual(commonPageObject.getAlertTitle(), MessageReader.getMessage("VALIDATION_MESSAGE_0010"));
            }

            commonPageObject.waitTillPostTransactionScreenDisplayed();

            Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0012"));

            node.info(commonPageObject.getCustomerReceipt());
            node.info(commonPageObject.getMerchantReceipt());
            node.info("Timer Present : " + commonPageObject.isTimerPresent());

            commonPageObject.clickOnNewPayment();

            CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);

            String response = CaptureADBLog.fetchReqRes("responseTimestamp", 1)[1];

            CommonUtils.createMethodLabel("Add money API");
            node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("encryptedTrack2", 1)[0]);
            node.info("Response : " + "{\"head\":" + response);

            CommonUtils.createMethodLabel("Echo and Reversal");
            node.info("Request : "+ CaptureADBLog.fetchLog(".*I okhttp.OkHttpClient: (.+\"reversalErrorMsg\"[^}]+\\}).*"));
            node.info("Response : "+ CaptureADBLog.fetchLog(".*I okhttp.OkHttpClient: (.+\"retrievalReferenceNumber\"[^}]+\\}).*"));

            Pattern pattern = Pattern.compile("\"orderId\":\"(\\d+)\"");
            Matcher matcher = pattern.matcher(response);

            if (matcher.find()) {
                // Extract the orderId from the matched group
                String orderId = matcher.group(1);
                CommonUtils.createMethodLabel("Instaproxy Log");
                CommonUtils.attachFileAsExtentLog(ServerConnection.fetchInstaLog(
                        ExecutionProperties.getProperty("environment.pod"),
                        ExecutionProperties.getProperty("environment.insta"),
                        orderId
                        ),node);
            } else {
               node.info("Unable to find orderId");
            }

        } catch (Exception e) {
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }
}
