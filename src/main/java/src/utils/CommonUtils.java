package src.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.reportManagement.ExtentManager;
import src.reportManagement.Screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class CommonUtils {

    private CommonUtils(){}

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static void pauseExecution(long seconds){
        try{
            logger.info("Current Thread is sleep for {} sec",seconds);
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e){
            logger.info("Error occured");
            Thread.currentThread().interrupt();
        }
    }

    public static void createMethodLabel(String methodName){
        Markup m = MarkupHelper.createLabel("Inside Method: "+methodName, ExtentColor.TEAL);
        ExtentManager.getTest().info(m);
    }
    public static void captureScreenMobile(ExtentTest node, String details){
        try{
            node.info(details,MediaEntityBuilder.createScreenCaptureFromPath(Screenshot.captureScreenMobile()).build());
        }catch (Exception e){
            logger.info("Exception Occurred while capturing Screen");
        }
    }

    public static String takeScreenShotOfCurrentView() {
        logger.info("Taking Screenshot");
        String capturePath = "reports/captures/";
        String fileName = "scr" + DateUtil.getCurrentDateAndTimeForReport() + ".png";
        String filePath = capturePath + fileName;
        try {
                        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            ImageIO.write(image, "png", new File(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "../captures/"+fileName;
    }

    public static void attachFileAsExtentLog(String filename,ExtentTest node){
        node.log(Status.INFO, "<b> <h6><font color='TEAL'>Link to Log File :</b><a href='" + filename + "'><b><h6><font color='blue'> " + filename + "</font></h6></b></a>");
    }

}
