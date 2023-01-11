package tools;

import java.nio.file.*;

public class ToolsLib {
    public static String getFileName(String fileName) {
        if (fileName.contains("\\")) {
            return fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.indexOf("."));
        } else {
            return  fileName.substring(0, fileName.indexOf("."));
        }
    }
    public static  String deleteLastExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    public static String getLastExtension(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring((fileName.lastIndexOf(".") + 1));
        } else {
            return "No extension";
        }
    }
    public static String deletePathToTmpDir(String fileName) {
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
            return tmpDir + "\\" + fileName;
        }
    }
}
