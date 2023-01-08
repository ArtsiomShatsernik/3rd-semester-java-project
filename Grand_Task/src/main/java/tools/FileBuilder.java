package tools;

import enums.FileTypes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileBuilder {
    private String fileName;
    private final Path tmpDir;

    public FileBuilder(String fileName) {
        this.fileName = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileBuilder setFileType(FileTypes type) {
        switch (type) {
            case xml -> {
                try {
                    fileName = XmlLib.xmlForm(fileName, tmpDir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case json -> {
                try {
                    fileName = JsonLib.jsonForm(fileName, tmpDir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }

    public void build() {
        Path from = Paths.get(tmpDir.toString() + "//" + fileName);
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
