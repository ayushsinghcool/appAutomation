package src.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Utils {

    private Utils() {
    }

    private static int counter = 0;
    private static Logger logger = LoggerFactory.getLogger(Utils.class);
    public static String createTxtFile(String dir, String fileName) {
        try {
            File directory = new File(dir);
            if (directory.exists() && counter == 0) {
                boolean deleted = deleteDirectory(directory);
                logger.info("Directory Deleted : {}", deleted ? dir : deleted);
            }
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdir();
                counter++;
                logger.info("Directory Created : {}", dirCreated ? dir : dirCreated);
            }

            File file = new File(dir + fileName);
            if (file.exists()) {
                logger.info("File already exists.");
            } else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}", fileCreated ? fileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir + fileName;
    }

    private static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        return directory.delete();
    }
}
