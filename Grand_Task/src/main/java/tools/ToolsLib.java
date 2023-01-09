package tools;

import javax.xml.transform.stream.StreamResult;
import java.nio.file.*;

public class ToolsLib {
    public static String getFileName(String fileName) {
        if (fileName.contains("\\")) {
            return fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.indexOf("."));
        } else {
            return  fileName.substring(0, fileName.indexOf("."));
        }
    }
    public static String deletePath(String fileName) {
        if (fileName.contains("\\")) {
            return fileName.substring(fileName.lastIndexOf("\\") + 1);
        } else {
            return fileName;
        }
    }
    public static String formPathToTmpDir(Path tmpDir, String fileName) {
        if (fileName.contains(tmpDir.toString())) {
            return fileName;
        } else {
            return tmpDir.toString() + "\\" + fileName;
        }
    }
}
