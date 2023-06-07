package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.CommonPageObject;
import src.project.ncmc.pageObject.OfflineSalePageObject;
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
        if (!BooleanController.getIsTIDActivated()) {
            onboardMerchantFetaure.merchantOnboard("149015901978438",
                    "https://nos-staging.paytm.com/nos/",
                    "14015808");
        }
        commonPageObject.setAmount(amount).clickOnCollectButton();
        node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
        node.info(commonPageObject.getScanQrText());
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

}
