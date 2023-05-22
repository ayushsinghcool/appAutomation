package src.reportManagement;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.common.MobileDriver;
import src.utils.DateUtil;

import java.io.File;
import java.io.IOException;

public class Screenshot {

    private Screenshot(){

    }
    private static AppiumDriver driver;

    private static Logger logger = LoggerFactory.getLogger(Screenshot.class);

    static {
        driver = MobileDriver.getMobileAppDriver();
    }

    public static String captureScreenMobile() {
        logger.info("\n Taking Screenshot");
        String capturePath = "reports/captures/";
        String fileName = "scr" + DateUtil.getCurrentDateAndTimeForReport() + ".png";
        String filePath = capturePath + fileName;
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "../captures/"+fileName;
    }
}
