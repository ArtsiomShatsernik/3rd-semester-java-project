package FileActions;

import interfaces.IFileActions;
import enums.*;
import tools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileFormer implements IFileActions {
    public FileTypes fileType;
    public ArchivingTypes archivingType = null;
    public EncryptionTypes encryptionType = null;
    public FileOperations firstOperation;
    public FileOperations secondOperation;
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    private String currentKey = defaultKey;
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
        identifyByOperation(firstOperation);
    }
    public FileFormer(String fileName, FileTypes fileType, FileOperations firstOperation, FileOperations secondOperation) {
        this(fileName, fileType, firstOperation);
        this.secondOperation = secondOperation;
        identifyByOperation(secondOperation);
    }
    public void changeEncryptionKey(String currentKey) {
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }

    @Override
    public IFileActions fileType() {
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

    @Override
    public IFileActions archiving() {
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

    @Override
    public IFileActions encryption() {
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
        Path from = Paths.get(ToolsLib.formPathToTmpDir(tmpDir, fileName));
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void doOperation(FileOperations operation) {
        if (operation != null) {
            if (operation.toString().equals(archivingType.toString())) {
                archiving();
            } else if(operation.toString().equals(encryptionType.toString())) {
                encryption();
            } else {
                throw new RuntimeException("Error! Incorrect operation.");
            }
        }
    }
    private void identifyByOperation(FileOperations operation) {
        if (operation.toString().equals(ArchivingTypes.jar.toString())) {
            this.archivingType = ArchivingTypes.jar;
        } else if (operation.toString().equals(ArchivingTypes.zip.toString())) {
            this.archivingType = ArchivingTypes.zip;
        } else if (operation.toString().equals(EncryptionTypes.axx.toString())) {
            this.encryptionType = EncryptionTypes.axx;
        } else {
            throw new RuntimeException("Error! Incorrect operation.");
        }
    }
}
