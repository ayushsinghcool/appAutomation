package src.project.calc.pageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import src.initializers.AppPageInit;
import src.utils.ApplicationInteractionActions;

public class CommonPageObject extends AppPageInit {

    public CommonPageObject(){
        super();
    }

    @FindBy(id = "result")
    private WebElement expression;
    public String  getExpression(){
        return expression.getText();
    }

    @FindBy(xpath = "//android.widget.TextView[@text=\"Tap here to minimise calculator\"]")
    private WebElement view;
    @FindBy(id = "digit_1")
    private WebElement one;
    public void clickOne(){
        ApplicationInteractionActions.tap(533,533);
       // System.out.println(driver.getPageSource());
       isElementClickable(one);
        clickOnElement(one,"Button one");
    }


}
