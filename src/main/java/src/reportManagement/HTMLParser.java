package src.reportManagement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Iterator;

public class HTMLParser {

    private static Logger logger = LoggerFactory.getLogger(HTMLParser.class);
    static Object[][] reporterData;

    private HTMLParser() {
    }

    public static Object[][] generateReporterObject(String htmlReportPath) throws IOException {
        ExtentReader htmlReader = new ExtentReader();
        CTReport ctReport = new CTReport();
        String fileContent = htmlReader.readFile(htmlReportPath);
        Document doc = Jsoup.parse(fileContent, "", Parser.xmlParser());
        int objSize;
        int counter;

        Element e;
        Iterator<?> var6;
        String moduleName;
        String testCaseDescription = null;
        String testCaseTime = null;
        /*String testCaseType = null;
        String infos = null;*/


        objSize = doc.select("div[class='node']").size();
        reporterData = new Object[objSize][16];
        counter = 0;
        var6 = doc.getElementsByClass("test-item").iterator();

        while (var6.hasNext()) {
            e = (Element) var6.next();
            //test-name fetches the module name
            moduleName = e.select("p[class='name']").text();
            //collapsible-header fetches the block of test case description
            for (Iterator<?> var9 = e.select("div[class='card-header']").iterator(); var9.hasNext(); ++counter) {
                Element f = (Element) var9.next();
                String testCaseID = null;
                String testStatus = null;

                try {
                    Element mainNode = f.children().select("div[class='node']").first();
                    Element mainNodeForTime = f.children().select("div[class='node-time']").first();
                    String allDes = mainNode.text();
                    testCaseID = allDes.split(":")[0];

                    if (testCaseID.toLowerCase().contains("setup") || testCaseID.toLowerCase().contains("teardown") || testCaseID.contains("Load Environment Variables and Base Set") || testCaseID.contains("Base User Creation")) {
                        counter--;
                        continue;
                    }

                    testCaseDescription = allDes.split(":")[1];


                    Element executionTimePROG = mainNodeForTime;
                       testCaseTime = executionTimePROG.text();


                    Element testStatusPROG = mainNode.nextElementSibling();
                    testStatus = testStatusPROG.text();

                    testStatus = Character.toString(testStatus.charAt(0)).toUpperCase() + testStatus.substring(1);
                } catch (Exception ex) {
                    logger.info("Error while reading HTML file");
                    ex.printStackTrace();
                }

                reporterData[counter][0] = ctReport.getBuildID();
                reporterData[counter][1] = ctReport.getLeadName();
                reporterData[counter][2] = ctReport.getTestFrameworkIP();
                reporterData[counter][3] = ctReport.getTestFrameworkName();
                reporterData[counter][4] = ctReport.getTestFrameworkSVNPath();
                reporterData[counter][5] = testCaseTime;
                reporterData[counter][6] = ctReport.getProductInterface();
                reporterData[counter][7] = testCaseID;
                reporterData[counter][8] = testCaseDescription;
                reporterData[counter][9] = testStatus;
                reporterData[counter][10] = ctReport.getCountry();
                reporterData[counter][11] = ctReport.getLanguage();
                reporterData[counter][12] = moduleName;
                reporterData[counter][13] = ctReport.getTestType();
            }
        }

        return reporterData;
    }
}