package tools;

import org.junit.*;

public class toolsLibTest {

    @Test
    public void getFileName() {
        String testName = "test.txt";
        Assert.assertEquals("test", toolsLib.getFileName(testName));
    }
}