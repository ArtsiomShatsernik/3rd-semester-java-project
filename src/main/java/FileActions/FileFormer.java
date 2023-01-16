package FileActions;

import enums.*;
import tools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileFormer extends FileAction {
    private final Path tmpDir;
    private boolean isArchived = false;
    private boolean isEncrypted = false;

    public FileFormer(String fileName, FileTypes fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileInTmpDir = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileFormer(String fileName, FileTypes fileType, FileOperations firstOperation) {
        this(fileName, fileType);
        this.firstOperation = firstOperation;
    }
    public FileFormer(String fileName, FileTypes fileType, FileOperations firstOperation, FileOperations secondOperation) {
        this(fileName, fileType, firstOperation);
        this.secondOperation = secondOperation;
    }
    public void changeEncryptionKey(String currentKey) {
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }
    public FileFormer fileType() {
        if (isArchived) {
            System.out.println("File already archived. Incorrect sequencing.");
        } else if (isEncrypted) {
            System.out.println("File already encrypted. Incorrect sequencing.");
        } else {
            switch (fileType) {
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
                    Path from = Paths.get(fileName);
                    Path to = Paths.get(ToolsLib.formPathToTmpDir(tmpDir, fileName));
                    try {
                        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return this;
    }

    public FileAction archiving() {
        switch (archivingType) {
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

    public FileAction encryption() {
        switch (encryptionType) {
            case axx -> {
                fileName += CryptoLib.encrypt(fileInTmpDir, tmpDir, currentKey);
                fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
            }
        }
        return this;
    }
    public void form() {
        fileType();
        doOperation(firstOperation);
        doOperation(secondOperation);
        fileName = ToolsLib.deletePathToTmpDir(fileName);
        Path from = Paths.get(ToolsLib.formPathToTmpDir(tmpDir, fileName));
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void doOperation(FileOperations operation) {
        identifyByOperation(operation);
        if (operation != null) {
            if (!(archivingType == null) && operation.toString().equals(archivingType.toString())) {
                archiving();
            } else if (!(encryptionType == null) && operation.toString().equals(encryptionType.toString())) {
                encryption();
            } else {
                throw new RuntimeException("Error! Incorrect operation.");
            }
        }
    }
}
