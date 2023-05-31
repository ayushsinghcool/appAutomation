package src.initializers;

import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.Setting;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.common.MobileDriver;
import src.reportManagement.ExtentManager;

import java.time.Duration;

public class AppPageInit {
    protected static AppiumDriver driver = MobileDriver.getMobileAppDriver();
    protected  ExtentTest pageInfo;
    private static Logger logger = LoggerFactory.getLogger(AppPageInit.class);

    protected AppPageInit() {
        logger.info("Driver {}", driver);
        driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 100);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isElementNotPresent(WebElement element) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean waitUntilElementDisappear(String locatorId) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(locatorId)));
            return false;
        } catch (java.util.NoSuchElementException e) {
            return true;
        }
    }

    public boolean isElementNotPresent(WebElement element,long timeOutInSeconds ) {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementClickable(WebElement element){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setText(WebElement element, String text, String elementName) {
        pageInfo = ExtentManager.getTest();
        element.sendKeys(text);
        pageInfo.info("Entered text: '" + text + "' into field " + elementName);
    }

   public void clickOnElement(WebElement element,String elementName) {
       pageInfo = ExtentManager.getTest();
       element.click();
       pageInfo.info("Clicked on : '" + elementName + "'");
    }

    public void logInfo(String info){
        pageInfo = ExtentManager.getTest();
        pageInfo.info(info);
    }
}
