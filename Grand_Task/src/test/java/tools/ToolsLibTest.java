package tools;

import org.junit.*;

public class ToolsLibTest {

    @Test
    public void getFileName() {
        String testName = "test.txt";
        Assert.assertEquals("test", ToolsLib.getFileName(testName));
    }
}