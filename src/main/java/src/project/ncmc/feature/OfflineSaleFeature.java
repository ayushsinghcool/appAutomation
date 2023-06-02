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
        commonPageObject.setAmount(amount);
        offlineSalePageObject.clickOnCollectButton();
        node.info(offlineSalePageObject.getAmountTitle() + ": " + offlineSalePageObject.getTotalAmount());
        node.info(offlineSalePageObject.getScanQrText());
        node.info(offlineSalePageObject.getPaymentLinkText());
        Assertion.verifyEqual(offlineSalePageObject.getTotalAmount(), "₹" + amount);
        Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0006"));
        node.info(commonPageObject.getCustomerReceipt());
        node.info(commonPageObject.getMerchantReceipt());
        node.info("Timer Present : " + commonPageObject.isTimerPresent());
        commonPageObject.clickOnNewPayment();
        commonPageObject.clickOnMenu().scrollUpToDeviceDetails().clickOnReassign().clickOnYesBtn();
        commonPageObject.waitTillLoaderPresent();
        CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(), node);
        CommonUtils.createMethodLabel("Offline Sale API");
        node.info("Request : " + "{\"offlineSaleRequestBody\":" + CaptureADBLog.fetchReqRes("{\"" + "offlineSaleRequestBody" + "\"")[0]);
        node.info("Response : " + "{\"head\":" + CaptureADBLog.fetchReqRes("responseTimestamp")[1]);
    }

}
