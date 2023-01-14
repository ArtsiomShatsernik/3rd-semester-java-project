package FileActions;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileOperations;
import enums.FileTypes;
import interfaces.IFileActions;
import tools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FileParser implements IFileActions {
    public FileTypes fileType = null;
    public ArchivingTypes archivingType = null;
    public EncryptionTypes encryptionType = null;
    public FileOperations firstOperation;
    public FileOperations secondOperation;
    private ArrayList<String> data;
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    private String currentKey = defaultKey;

    public FileParser(String fileName) {
        this.fileName = fileName;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
        Path from = Paths.get(fileName);
        Path to = Paths.get(fileInTmpDir);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        autoSet();
    }
    public FileParser(String fileName, FileTypes fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
        try {
            tmpDir = Files.createTempDirectory("tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.fileInTmpDir = ToolsLib.formPathToTmpDir(tmpDir, fileName);
        Path from = Paths.get(fileName);
        Path to = Paths.get(fileInTmpDir);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileParser(String fileName, FileTypes fileType, FileOperations firstOperation) {
        this(fileName, fileType);
        this.firstOperation = firstOperation;
        identifyByOperation(firstOperation);
    }
    public FileParser(String fileName, FileTypes fileType, FileOperations firstOperation, FileOperations secondOperation) {
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
        try {
            data = new ArrayList<>();
            switch (fileType) {
                case xml -> data = XmlLib.xmlParse(fileInTmpDir);
                case json -> data = JsonLib.jsonParse(fileInTmpDir);
                case txt -> {
                    try {
                        data = TxtLib.txtParse(fileInTmpDir);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error! Check the correctness of file type.");
        }
        return this;
    }
    @Override
    public IFileActions archiving() {
        try {
            switch (archivingType) {
                case jar -> ArchivingLib.unpackJar(fileInTmpDir, tmpDir);
                case zip -> ArchivingLib.unpackZip(fileInTmpDir, tmpDir);
            }
            fileName = ToolsLib.deleteLastExtension(fileName);
            fileInTmpDir = ToolsLib.deleteLastExtension(fileInTmpDir);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error! Check the correctness of archiving type.");
        }
        return this;
    }

    @Override
    public IFileActions encryption() {
        try {
            switch (encryptionType) {
                case axx -> CryptoLib.decrypt(fileInTmpDir, currentKey);
            }
            fileName = ToolsLib.deleteLastExtension(fileName);
            fileInTmpDir = ToolsLib.deleteLastExtension(fileInTmpDir);
        }  catch (RuntimeException e) {
            throw new RuntimeException("Error! Check the correctness of encryption type.");
        }
        return this;
    }
    public ArrayList<String> parse() {
        if (data == null) {
            doOperation(firstOperation);
            doOperation(secondOperation);
            fileType();
            Path from = Paths.get(fileInTmpDir);
            Path to = Paths.get(fileName);
            try {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    private void autoSet() {
        if (data == null) {
            String lastExtension;
            String tempName = fileName;
            Boolean end = false;
            while (!(lastExtension = ToolsLib.getLastExtension(tempName)).equals("No extension")) {
                for (FileTypes type : FileTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.fileType = FileTypes.valueOf(lastExtension);
                        tempName = ToolsLib.deleteLastExtension(tempName);
                        end = true;
                        break;
                    }
                   if(end) {
                       break;
                   }
                }
                for (ArchivingTypes type : ArchivingTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.archivingType = ArchivingTypes.valueOf(lastExtension);
                        setOperations(this.archivingType.toString());
                        tempName = ToolsLib.deleteLastExtension(tempName);
                        break;
                    }
                }
                for (EncryptionTypes type : EncryptionTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.encryptionType = EncryptionTypes.valueOf(lastExtension);
                        setOperations(this.encryptionType.toString());
                        tempName = ToolsLib.deleteLastExtension(tempName);
                        break;
                    }
                }
            }
        }
    }
    private void setOperations(String operation) {
        if (firstOperation == null) {
            firstOperation = FileOperations.valueOf(operation);
        } else if (secondOperation == null) {
            secondOperation = FileOperations.valueOf(operation);
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
