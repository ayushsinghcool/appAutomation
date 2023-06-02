package src.project.ncmc.pageObject;


import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class OfflineSalePageObject extends AppPageInit {

    public OfflineSalePageObject(){
        super();
    }


    @AndroidFindBy(id="btnCollect")
    private WebElement collect;

    public OfflineSalePageObject clickOnCollectButton(){
        isElementNotPresent(collect);
        clickOnElement(collect,"Collect Button");
        return this;
    }

    @AndroidFindBy(id="tvTotalAmountTitle")
    private WebElement amtTitle;

    public String getAmountTitle(){
        isElementNotPresent(amtTitle);
        return amtTitle.getText();
    }

    @AndroidFindBy(id="tvTotalAmount")
    private WebElement totalAmount;

    public String getTotalAmount(){
        isElementNotPresent(totalAmount);
        return totalAmount.getText();
    }

    @AndroidFindBy(id="tvBottomLeftView")
    private WebElement scanText;

    public String getScanQrText(){
        return scanText.getText();
    }

    @AndroidFindBy(id="tvBottomRightView")
    private WebElement paymentText;

    public String getPaymentLinkText(){
        return paymentText.getText();
    }

}
