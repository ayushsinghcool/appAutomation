package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.CommonPageObject;
import src.project.ncmc.pageObject.OfflineSalePageObject;
import src.propertyManagement.ExecutionProperties;
import src.propertyManagement.MessageReader;
import src.reportManagement.ExtentManager;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;

public class OfflineSaleFeature {

    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();
    CommonPageObject commonPageObject = new CommonPageObject();
    OfflineSalePageObject offlineSalePageObject = new OfflineSalePageObject();

    public void performOfflineSale(String amount) {
        ExtentTest node = ExtentManager.getTest();
        try{
        if (BooleanController.getFirstTimeOnboarding()) {
            onboardMerchantFetaure.merchantOnboard(
                    ExecutionProperties.getProperty("nos.dsn"),
                    ExecutionProperties.getProperty("nos.url"),
                    ExecutionProperties.getProperty("nos.tid"));
        }

        commonPageObject.setAmount(amount).clickOnCollectButton();

        node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
        node.info(offlineSalePageObject.getScanQrText());
        node.info(offlineSalePageObject.getPaymentLinkText());

        Assertion.verifyEqual(commonPageObject.getTotalAmount(), "₹" + amount);
        Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0006"));

        node.info(commonPageObject.getCustomerReceipt());
        node.info(commonPageObject.getMerchantReceipt());
        node.info("Timer Present : " + commonPageObject.isTimerPresent());

        commonPageObject.clickOnNewPayment().clickOnMenu().scrollUpToDeviceDetails().clickOnDeviceDeatils().clickOnReassign().clickOnYesBtn().waitTillLoaderPresent();

        CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);
        CommonUtils.createMethodLabel("Offline Sale API");
        node.info("Request : " + "{\"offlineSaleRequestBody\":" + CaptureADBLog.fetchReqRes("{\"" + "offlineSaleRequestBody" + "\"",1)[0]);
        node.info("Response : " + "{\"head\":" + CaptureADBLog.fetchReqRes("responseTimestamp",1)[1]);
    }
        catch (Exception e){
            e.printStackTrace();
            node.fail("Test Case Failed");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }
    }

}
