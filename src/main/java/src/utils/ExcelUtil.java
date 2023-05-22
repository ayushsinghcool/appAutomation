package src.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelUtil {

    public ExcelUtil() {
        logger.info("Initializing Excel Util");
    }

    String excelSheetPath;
    XSSFSheet excelWSheet;
    XSSFWorkbook excelWBook;
    XSSFRow row;
    XSSFCell cell;

    private static final String[] ctReportExcelHeader = {
            "BUILD_ID",
            "Lead-Name",
            "Test-Framework-IP",
            "Test-Framework-Name",
            "Test-Framework-GIT-Path",
            "Test-Execution-Date-Time",
            "Product-Interface",
            "Unique-TestCase-ID",
            "Test-Case-Description",
            "Test-Status",
            "INFO1",
            "INFO2",
            "Module Name",
            "Test Type"
    };

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /*private static InputStream createExcel(InputStream inputStream, String filename) throws IOException {
        try {
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            workbook.createSheet("new sheet");

            try (FileOutputStream out = new FileOutputStream(new File(filename))) {
                workbook.write(out);
            }

            inputStream = new FileInputStream(filename);
        }

        return inputStream;
    }*/

    private static InputStream createExcel(InputStream inputStream, String filename) throws IOException {
        try{
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            // Create Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // Create file system using specific name
            FileOutputStream out = new FileOutputStream(filename);
            workbook.createSheet("new sheet");
            // write operation workbook using file out object
            workbook.write(out);
            out.close();

            inputStream = new FileInputStream(filename);
            logger.info("File Opened " + filename);
        }
        return inputStream;
    }

    public static void writeDataToExcel(String filename, int rowt, int cellt, String textt, int... index) throws IOException {
        int sheetId = (index.length > 0) ? index[0] : 0;
        InputStream inpx = null;
        FileOutputStream fileOut = null;
        try {
            inpx = createExcel(inpx, filename);
            XSSFWorkbook wbx = new XSSFWorkbook(inpx);
            XSSFSheet sheet = wbx.getSheetAt(sheetId);
            XSSFCell cell;
            XSSFRow row = sheet.getRow(rowt);
            if (row == null) {
                //logger.info("Row is null");
                sheet.createRow(rowt);
                row = sheet.getRow(rowt);
                row.createCell(cellt);
                cell = row.getCell(cellt);
            } else {
                row.createCell(cellt);
                cell = row.getCell(cellt);
            }
            cell.setCellValue("" + textt);
            fileOut = new FileOutputStream(filename);
            wbx.write(fileOut);
        } catch (IOException ex) {
            logger.error("Error occurred in Excel Util....");
            ex.printStackTrace();
        } finally {
            if (inpx != null)
                inpx.close();

            if (fileOut != null)
                fileOut.close();
        }
    }


    private static void writeHeader(String filePaths, String[] headerList) throws IOException {
        logger.info("Writing file headers {}", filePaths);
        Path path = Paths.get(filePaths);

        if (!Files.exists(path)) {
            logger.info("Creating New File: {}", path);
            for (int i = 0; i < headerList.length; i++) {
                logger.info(headerList[i]);
                writeDataToExcel(filePaths, 0, i, headerList[i]);
            }
        } else {
            logger.info("File already exist: {}", path);
        }

    }

    public static void writeCTReportFileHeader() throws IOException {
        writeHeader(FilePaths.CIT_REPORT_OUTPUT_FILE, ctReportExcelHeader);
    }

    public void setExcelFile(String path, String sheetName) {
        try (FileInputStream excelFile = new FileInputStream(path)) {
            excelSheetPath = path;
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
            if (excelWSheet == null) {
                excelWSheet = excelWBook.createSheet(sheetName);
            }
        } catch (Exception var3) {
            logger.info("Error performing setExcelFile():");
            var3.printStackTrace();
        }

    }

    public void createHeader(String... headers) {
        try {
            if (excelWSheet.getRow(0) == null) {
                excelWSheet.createRow(0);
                row = excelWSheet.getRow(0);
            } else {
                row = excelWSheet.getRow(0);
            }

            CellStyle style = excelWBook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            style.setFillPattern(FillPatternType.forInt((short) 1));
            Font font = excelWBook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setBorderBottom(BorderStyle.valueOf((short) 1));
            style.setBorderTop(BorderStyle.valueOf((short) 1));
            style.setBorderRight(BorderStyle.valueOf((short) 1));
            style.setBorderLeft(BorderStyle.valueOf((short) 1));
            style.setFont(font);

            for (int i = 0; i < headers.length; ++i) {
                cell = row.getCell(i, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style);
                excelWSheet.autoSizeColumn(i);
            }

            excelWSheet.createFreezePane(0, 1);
            FileOutputStream fileOut = new FileOutputStream(excelSheetPath);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void writeTestCaseSheetNew(String sheetName, Object[][] writetofile) throws IOException {

        String path = FilePaths.CIT_REPORT_OUTPUT_FILE;
        File file = new File(path);
        File directory = new File(FilePaths.CT_REPORT);
        FileOutputStream outputStream = null;

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!file.exists()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
                workbook.createSheet(sheetName);
                setExcelFile(path, sheetName);
                createHeader(ctReportExcelHeader);
            }
        }

        try (FileInputStream inputStream = new FileInputStream(file);
             Workbook workBookName = new XSSFWorkbook(inputStream);
        ) {
            Sheet sheet = workBookName.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();

            for (int i = 0; i < writetofile.length; ++i) {
                ++rowCount;
                Row newRow = sheet.createRow(rowCount);

                for (int j = 0; j < writetofile[0].length; ++j) {
                    Cell cel = newRow.createCell(j);
                    cel.setCellValue((String) writetofile[i][j]);
                }
            }


            outputStream = new FileOutputStream(file);
            workBookName.write(outputStream);
            logger.info("CT Report Generated Successfully");
            outputStream.close();
        } catch (Exception e) {
            logger.error("error {}", e.getMessage());
        }

    }
}
