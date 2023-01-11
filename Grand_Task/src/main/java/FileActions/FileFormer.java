package FileActions;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;
import interfaces.IFileActions;
import tools.*;

import java.io.*;
import java.nio.file.*;

public class FileFormer implements IFileActions {
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    private final String defaultKey = "QfTjWnZq";
    private String currentKey = defaultKey;
    boolean isArchived = false;
    boolean isEncrypted = false;

    public FileFormer(String fileName) {
        this.fileName = fileName;
        this.fileInTmpDir = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileFormer(String fileName, String currentKey) {
        this(fileName);
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }

    @Override
    public IFileActions setFileType(FileTypes type) {
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
                case txt -> {

                }
            }
        }
        return this;
    }

    @Override
    public IFileActions setArchivingType(ArchivingTypes type) {
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
                    fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Archiving error!");
                }
            }
        }
        isArchived = true;
        return this;
    }

    @Override
    public IFileActions setEncryptionType(EncryptionTypes type) {
        switch (type) {
            case axx -> {
                fileName += CryptoLib.encrypt(fileInTmpDir, tmpDir, currentKey);
                fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
            }
        }
        return this;
    }
    @Override
    public void form() {
        Path from = Paths.get(ToolsLib.formPathToTmpDir(tmpDir, fileName));
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
