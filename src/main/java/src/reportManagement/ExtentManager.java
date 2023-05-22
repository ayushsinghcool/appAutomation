package src.reportManagement;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import src.globalConstant.FilePaths;
import src.propertyManagement.ExecutionProperties;
import src.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private ExtentManager(){
    }

    private static ExtentReports extent;
    private static String extentReportFileName = null;
    protected static Map<Integer,ExtentTest> extentMap = new HashMap<>();

    public static ExtentReports getInstance(){
        if(extent != null){
            return extent;
        }
        return createInstance();
    }

    public static String getExtentReportFileName(){
        return extentReportFileName;
    }
    private static ExtentReports createInstance(){
        ExtentSparkReporter htmlReporter;
        String fileName = "NCMC App"+ DateUtil.getCurrentDateAndTimeForReport()+".html";
        extentReportFileName = FilePaths.EXTENT_REPORT_PATH + fileName;
        htmlReporter = new ExtentSparkReporter(extentReportFileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("NCMC App Report");
        //htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("NCMC Automation Report");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setAnalysisStrategy(AnalysisStrategy.SUITE);

        extent.setSystemInfo("Environment", ExecutionProperties.getProperty("environment"));
        extent.setSystemInfo("Product",ExecutionProperties.getProperty("product"));
        extent.setSystemInfo("Jira Ticket",ExecutionProperties.getProperty("JiraTicket"));
        extent.setSystemInfo("Version",ExecutionProperties.getProperty("version"));

        extent.flush();

        return extent;
    }

    public static synchronized ExtentTest getTest(){
        return extentMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName, String desc){
        ExtentTest test = extent.createTest(testName,desc);
        extentMap.put((int) Thread.currentThread().getId(),test);
        return test;
    }

    public static synchronized ExtentTest startTest(ExtentTest test,String testName, String desc){
        ExtentTest t1 = test.createNode(testName,desc);
        extentMap.put((int) Thread.currentThread().getId(),t1);
        return t1;
    }

    public static synchronized ExtentTest startTestFromProperty(ExtentTest test,String keyValue){
        ExtentTest t1 = test.createNode(keyValue);
        extentMap.put((int) Thread.currentThread().getId(),t1);
        return t1;
    }

    public static synchronized ExtentTest startTestFromProperty(String keyValue){
        ExtentTest t1 = extent.createTest(keyValue);
        extentMap.put((int) Thread.currentThread().getId(),t1);
        return t1;
    }
}
