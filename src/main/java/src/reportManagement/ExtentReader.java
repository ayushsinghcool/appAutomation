package src.reportManagement;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ExtentReader {
    protected ExtentReader() {
    }

    public String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        String var7;
        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }

            var7 = fileContents.toString();
        } finally {
            scanner.close();
        }

        return var7;
    }
}
