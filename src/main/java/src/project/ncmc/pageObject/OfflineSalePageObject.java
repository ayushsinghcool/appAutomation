package src.project.ncmc.pageObject;


import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class OfflineSalePageObject extends AppPageInit {
    public OfflineSalePageObject(){
        super();
    }

    @AndroidFindBy(id="tvBottomRightView")
    private WebElement paymentText;

    public String getPaymentLinkText(){
        return paymentText.getText();
    }

}
