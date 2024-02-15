package src.reportManagement;

import src.globalConstant.FilePaths;
import src.propertyManagement.CTReportProperties;
import src.propertyManagement.MobileProperties;
import src.utils.ExcelUtil;

import java.io.*;

public class CTReport {
    private String buildID;
    private String leadName;
    private String testFrameworkIP;
    private String testFrameworkName;
    private String testFrameworkGitPath;
    private String productInterface;
    private String inf01;
    private String inf02;
    private String testType;
    public CTReport(){
        this.leadName = CTReportProperties.getInstance().getProperty("LeadName");
        this.productInterface = CTReportProperties.getInstance().getProperty("ProjectName");
        this.testFrameworkName = CTReportProperties.getInstance().getProperty("TestFrameworkName");
        this.testFrameworkIP = CTReportProperties.getInstance().getProperty("TestFrameworkIP");
        this.testFrameworkGitPath = CTReportProperties.getInstance().getProperty("TestFrameworkGitPath");
        this.buildID = CTReportProperties.getInstance().getProperty("BUILDID");
        this.inf01 = MobileProperties.getProperty("project.name");
        this.inf02 = MobileProperties.getProperty("language.code");
        this.testType = CTReportProperties.getInstance().getProperty("TestType");
    }
    public static void generateCTReport(String htmlReportPath) {
        try{
        File directory = new File(FilePaths.CT_REPORT);

        if (!directory.exists()) {
            directory.mkdir();
        }
        ExcelUtil.writeCTReportFileHeader();

        Object[][] reporterObject = HTMLParser.generateReporterObject(htmlReportPath);
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.writeTestCaseSheetNew(FilePaths.CT_REPORT, reporterObject);
    }
    catch(IOException io){
        io.printStackTrace();
    }}

    public static void main(String[] args) {

        String path = "/Users/ayushkumarsingh/Downloads/p3/paytm_ncmc_app_automation/reports/htmlReports/NCMC App06-02-2024-05-22-00.html";
        CTReport ctReport = new CTReport();
        ctReport.generateCTReport(path);
    }
    public String getLeadName() {
        return this.leadName;
    }
    public String getTestFrameworkName() {
        return this.testFrameworkName;
    }
    public String getProductInterface() {
        return this.productInterface;
    }
    public String getTestFrameworkIP() {
        return this.testFrameworkIP;
    }
    public String getCountry() {
        return this.inf01;
    }
    public String getLanguage() {
        return this.inf02;
    }
    public String getBuildID() {
        return this.buildID;
    }
    public String getTestFrameworkSVNPath() {
        return this.testFrameworkGitPath;
    }
    public String getTestType(){return this.testType;}


}
