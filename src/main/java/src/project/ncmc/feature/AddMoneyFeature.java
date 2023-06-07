package src.project.ncmc.feature;

import com.aventstack.extentreports.ExtentTest;
import src.common.Assertion;
import src.globalConstant.BooleanController;
import src.project.ncmc.pageObject.AddMoneyPageObject;
import src.project.ncmc.pageObject.CommonPageObject;
import src.propertyManagement.MessageReader;
import src.reportManagement.ExtentManager;
import src.utils.CaptureADBLog;
import src.utils.CommonUtils;

public class AddMoneyFeature {
    CommonPageObject commonPageObject = new CommonPageObject();
    OnboardMerchantFetaure onboardMerchantFetaure = new OnboardMerchantFetaure();
    AddMoneyPageObject addMoneyPageObject = new AddMoneyPageObject();

    public void performAddMoney(String transactionType, String amount){
        ExtentTest node = ExtentManager.getTest();
        if (!BooleanController.getIsTIDActivated()) {
            onboardMerchantFetaure.merchantOnboard("149015901978438",
                    "https://nos-staging.paytm.com/nos/",
                    "14015808");
        }
        commonPageObject.clickOnMenu().scrollUpToDeviceDetails();
        addMoneyPageObject.clickOnAddMoneyMenu(transactionType);
        commonPageObject.setAmount("270.01").clickOnCollectButton();
        //First Tap
        node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
        node.info(commonPageObject.getScanQrText());
        Assertion.verifyEqual(commonPageObject.getTotalAmount(), "₹" + amount);
        //Second Tap
        commonPageObject.waitTillLoaderDisplayed();
        node.info(commonPageObject.getAmountTitle() + ": " + commonPageObject.getTotalAmount());
        Assertion.verifyEqual(commonPageObject.getTotalAmount(), "₹" + amount);
        Assertion.verifyEqual(commonPageObject.getAlertTitle(), MessageReader.getMessage("VALIDATION_MESSAGE_0010"));
        Assertion.verifyEqual(commonPageObject.getTxnStatus(), MessageReader.getMessage("VALIDATION_MESSAGE_0012"));
        node.info(commonPageObject.getCustomerReceipt());
        node.info(commonPageObject.getMerchantReceipt());
        node.info("Timer Present : " + commonPageObject.isTimerPresent());
        commonPageObject.clickOnNewPayment();
        CommonUtils.attachFileAsExtentLog(CaptureADBLog.captureLogcatLog(),node);
        CommonUtils.createMethodLabel("Add money API");
        node.info("Request : " + "{\"body\":" + CaptureADBLog.fetchReqRes("encryptedTrack2",1)[0]);
        node.info("Response : " + "{\"head\":"+ CaptureADBLog.fetchReqRes("responseTimestamp",1)[1]);
        CommonUtils.pauseExecution(5);
        CommonUtils.createMethodLabel("Echo and Reversal");
        node.info("Request : " + "{\"body\":{\"echo\":{\"body\":" + CaptureADBLog.fetchReqRes("echo",2)[0]);
        node.info("Response : " + "{\"head\":"+ CaptureADBLog.fetchReqRes("isReversalRequired",1)[1]);


    }
}
