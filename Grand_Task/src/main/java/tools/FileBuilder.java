package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBuilder {
    private String fileName;
    private Path tmpDir;
    public FileBuilder(String fileName) {
        this.fileName = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        public FileBuilder toXml() {
            try {
                fileName = xmlLib.xmlForm(fileName, tmpDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
}
