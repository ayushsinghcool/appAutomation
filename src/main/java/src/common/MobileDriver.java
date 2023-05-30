package src.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;
import src.propertyManagement.MobileProperties;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class MobileDriver {

    private MobileDriver(){

    }
    private static AppiumDriver driver;
    private static AppiumDriverLocalService service;
    private static Logger logger = LoggerFactory.getLogger(MobileDriver.class);

    public static void startAppiumServer() {
        try {
            String nodeJsPath = FilePaths.NODE_JS_PATH;
            String appiumJsPath = FilePaths.APPIUM_JS_PATH;
            Map<String, String> env = new HashMap<>(System.getenv());
            //Start the server with the builder
            service = AppiumDriverLocalService.buildService(
                    new AppiumServiceBuilder().
                            usingDriverExecutable(new File(nodeJsPath)).
                            withAppiumJS(new File(appiumJsPath)).
                            withIPAddress("127.0.0.1").
                            //withLogFile(new File(FilePaths.APPIUM_LOGS)).
                                    usingAnyFreePort().
                            withArgument(GeneralServerFlag.LOG_LEVEL, "error").
                            withArgument(GeneralServerFlag.RELAXED_SECURITY).
                            withArgument(GeneralServerFlag.ALLOW_INSECURE, "get_system_logs").
                            withArgument(GeneralServerFlag.SESSION_OVERRIDE));
            service.start();
            String appiumURL = service.getUrl().toString();
            logger.info("Appium Url {}", appiumURL);
            logger.info("System Environment {}.", env);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void stopAppiumServer() {
        service.stop();
    }


    public static AndroidDriver getAndroidDriver() {
        File dir = new File(FilePaths.APPLICATION_PATH);
        File app = new File(dir, MobileProperties.getProperty("application.name"));
        try {
            startAppiumServer();
            logger.info("Setting Capabilities of Android Driver");
            UiAutomator2Options capabilities = new UiAutomator2Options()
                    //.setUninstallOtherPackages(MobileProperties.getProperty("app.uninstall"))
                    .setDeviceName(MobileProperties.getProperty("device.name"))
                    .setUdid(MobileProperties.getProperty("device.udid"))
                    //.setApp(app.getAbsolutePath())
                    .setAppPackage(MobileProperties.getProperty("app.package"))
                    .setAppActivity(MobileProperties.getProperty("app.activity"))
                    .setAppWaitActivity("com.paytm.pos.ui.activation.ScanActivationActivity")
                    .setPlatformVersion(MobileProperties.getProperty("device.version"))
                    .setAutoGrantPermissions(true)
                    .setNoReset(false)
                    .setNewCommandTimeout(Duration.ofSeconds(30000))
                    .setAutomationName("UiAutomator2")
                    .clearDeviceLogsOnStart();
            return new AndroidDriver(service.getUrl(), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AppiumDriver getMobileAppDriver(){
        try{
            if(driver != null){
                return driver;
            }

            if (MobileProperties.getProperty("device.platform").equalsIgnoreCase("android")) {
                driver = MobileDriver.getAndroidDriver();
                logger.info("Info :  ");

                //mobileDriver.removeApp("com.abp.sub");
            }
            /*else {
                mobileDriver = MobileDriver.getIOSDriver();
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return driver;
    }
}
