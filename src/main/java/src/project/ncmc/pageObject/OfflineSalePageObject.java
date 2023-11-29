package src.project.ncmc.pageObject;


import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

import java.util.List;

public class OfflineSalePageObject extends AppPageInit {
    public OfflineSalePageObject(){
        super();
    }

    @AndroidFindBy(id="tvLabel")
    private List<WebElement> paymentText;

    public String getPaymentLinkText(){
        isElementClickable(paymentText.get(1));
        return paymentText.get(1).getText();
    }

    public String getScanQrText(){
        isElementClickable(paymentText.get(0));
        return paymentText.get(0).getText();
    }

}
