package tools;

import java.nio.file.*;

public class ToolsLib {
    public static String getFileName(String fileName) {
        return fileName.substring(0 ,fileName.indexOf("."));
    }
    public static String formPathToTmpDir(Path tmpDir, String fileName) {
        return tmpDir.toString() + "//" + fileName;
    }
}
