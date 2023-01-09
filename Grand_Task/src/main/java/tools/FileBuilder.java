package tools;

import enums.ArchivingTypes;
import enums.FileTypes;

import java.io.*;
import java.nio.file.*;

public class FileBuilder {
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    boolean isArchived = false;
    boolean isEncrypted = false;

    public FileBuilder(String fileName) {
        this.fileName = fileName;
        fileInTmpDir = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileBuilder setFileType(FileTypes type) {
        if (isArchived) {
            System.out.println("File already archived. Incorrect sequencing.");
        } else if (isEncrypted) {
            System.out.println("File already encrypted. Incorrect sequencing.");
        } else {
            switch (type) {
                case xml -> {
                    try {
                        fileName = XmlLib.xmlForm(fileInTmpDir, tmpDir);
                        fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
                    } catch (IOException e) {
                        throw new RuntimeException("XMLForm error!");
                    }
                }
                case json -> {
                    try {
                        fileName = JsonLib.jsonForm(fileInTmpDir, tmpDir);
                        fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
                    } catch (IOException e) {
                        throw new RuntimeException("JSONForm error!");
                    }
                }
            }
        }
        return this;
    }
    public FileBuilder setArchivingType(ArchivingTypes type) {
        switch (type) {
            case jar -> {
                try {
                    fileName += ArchivingLib.packJar(fileInTmpDir, tmpDir);
                    fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Archiving error!");
                }
            }
            case zip -> {
                try {
                    fileName += ArchivingLib.packZip(fileInTmpDir, tmpDir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
            }
        }
        isArchived = true;
        return this;
    }

    public void build() {
        Path from = Paths.get(ToolsLib.formPathToTmpDir(tmpDir, fileName));
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
