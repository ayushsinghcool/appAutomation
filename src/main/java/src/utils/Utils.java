package src.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String createTxtFile(String dir,String fileName) {
        try {
            File file = new File(dir+fileName);
            File directory = new File(dir);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (file.exists()) {
                logger.info("File already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}", fileCreated ? fileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir+fileName;
    }
}
