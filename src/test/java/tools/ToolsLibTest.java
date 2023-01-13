package tools;

import org.junit.*;

import java.nio.file.Path;

public class ToolsLibTest {

    @Test
    public void getFileNameWithoutPath() {
        String testName = "test.txt";
        Assert.assertEquals("test", ToolsLib.getFileName(testName));
    }
    @Test
    public void getFileNameWithPath() {
        String testName = "C:\\Users\\User\\AppData\\Local\\Temp\\tmp11562600244765466445\\test.txt";
        Assert.assertEquals("test", ToolsLib.getFileName(testName));
    }
    @Test
    public void deletePathToTmpDir() {
        String testName = "C:\\Users\\User\\AppData\\Local\\Temp\\tmp11562600244765466445\\test.txt";
        Assert.assertEquals("test.txt", ToolsLib.deletePathToTmpDir(testName));
    }
    @Test
    public void deleteLastExtension() {
        String testName = "input.txt.axx.jar";
        Assert.assertEquals("input.txt.axx", ToolsLib.deleteLastExtension(testName));
    }
    @Test
    public void getLastExtension() {
        String testName = "input.txt.axx";
        Assert.assertEquals("axx", ToolsLib.getLastExtension(testName));
    }
    @Test
    public void getLastExtensionWithoutExtensions() {
        String testName = "input";
        Assert.assertEquals("No extension", ToolsLib.getLastExtension(testName));
    }

    @Test
    public void formPathToTmpDirWithContainedPath() {
        String tmpDir = "C:\\Users\\User\\AppData\\Local\\Temp\\tmp11562600244765466445";
        String fileName = "C:\\Users\\User\\AppData\\Local\\Temp\\tmp11562600244765466445\\input.xml";
        Assert.assertEquals(fileName, ToolsLib.formPathToTmpDir(Path.of(tmpDir), fileName));
    }
    @Test
    public void formPathToTmpDirWithoutContainedPath() {
        String tmpDir = "C:\\Users\\User\\AppData\\Local\\Temp\\tmp11562600244765466445";
        String fileName = "input.xml";
        Assert.assertEquals(tmpDir + "\\" + fileName, ToolsLib.formPathToTmpDir(Path.of(tmpDir), fileName));
    }
}