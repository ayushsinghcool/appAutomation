package src.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;

import java.io.File;
import java.io.IOException;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String createTxtFile(String pathFileName) {
        try {
            File file = new File(pathFileName);
            File directory = new File(FilePaths.LOGS);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (file.exists()) {
                logger.info("File already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}", fileCreated ? pathFileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathFileName;
    }
}
