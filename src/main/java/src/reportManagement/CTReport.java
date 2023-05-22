package src.reportManagement;

import src.globalConstant.FilePaths;
import src.propertyManagement.CTReportProperties;
import src.propertyManagement.MobileProperties;
import src.utils.DateUtil;
import src.utils.ExcelUtil;

import java.io.*;

public class CTReport {
    public static String INPUTHTML_PATH;
    public static String CTDATEFORMAT_INTERNAL = "yyyy-MM-dd HH:mm:ss";
    private static String description = null;
    private static String uniqueID = null;
    private static CTReportProperties properties = CTReportProperties.getInstance();
    private String buildID, leadName, testFrameworkIP, testFrameworkName, testFrameworkGitPath,
            testExecutionDateTime, productInterface, uniqueTestCaseID, testCaseDescription, testStatus,INFO1,INFO2, TestType;
    public CTReport(){
        this.leadName = CTReportProperties.getInstance().getProperty("LeadName");
        this.productInterface = CTReportProperties.getInstance().getProperty("ProjectName");
        this.testFrameworkName = CTReportProperties.getInstance().getProperty("TestFrameworkName");
        this.testFrameworkIP = CTReportProperties.getInstance().getProperty("TestFrameworkIP");
        this.testFrameworkGitPath = CTReportProperties.getInstance().getProperty("TestFrameworkGitPath");
        this.testExecutionDateTime = DateUtil.getCurrentDateTime();
        this.buildID = CTReportProperties.getInstance().getProperty("BUILDID");
        this.INFO1 = MobileProperties.getProperty("project.name");
        this.INFO2 = MobileProperties.getProperty("language.code");
    }

    public CTReport(String inputHTMLPath) {
        this();
        this.INPUTHTML_PATH = inputHTMLPath;

    }

    public static void generateCTReport() throws IOException {

        File directory = new File(FilePaths.CT_REPORT);

        if (!directory.exists()) {
            directory.mkdir();
        }
        ExcelUtil.writeCTReportFileHeader();

        Object[][] reporterObject = HTMLParser.generateReporterObject();
        ExcelUtil excelUtil = new ExcelUtil();
        excelUtil.writeTestCaseSheetNew(FilePaths.CT_REPORT, reporterObject);
    }

    public static void main(String args[]) {

        try {
            String path = "/Users/ayushkumarsingh/Desktop/ncmc_app_automation/reports/htmlReports/NCMC App03-04-2023-02-22-27.html";
            String fileName = "MobileApp19-12-2019-01-04-01.html";
            // CITReport CIT = new CITReport(ExtentManager.getExtentReportFileName());
            CTReport ctReport = new CTReport(path);
            ctReport.generateCTReport();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readBuildID() throws Exception {
        String BuildIDPath = CTReportProperties.getInstance().getProperty("BuildIDPath");
        File fileName = new File(BuildIDPath);

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            String buildID = null;
            while ((line = br.readLine()) != null) {
                buildID = line.trim();
            }
            System.out.println("Build ID is -->" + buildID);
        } finally {
            br.close();
        }
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
        return this.INFO1;
    }

    public String getLanguage() {
        return this.INFO2;
    }

    public String getBuildID() {
        return this.buildID;
    }

    public String getTestFrameworkSVNPath() {
        return this.testFrameworkGitPath;
    }

    public String getTestCaseDescription() {
        return this.testCaseDescription;
    }

    public void setTestCaseDescription(String desc) {
        this.testCaseDescription = desc;
    }


}
