package FileActions;

import enums.*;
import interfaces.IFileActions;
import tools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FileParser implements IFileActions {
    private ArrayList<String> data;
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    private final String defaultKey = "QfTjWnZq";
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
    }
    FileParser(String fileName, String currentKey) {
        this(fileName);
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }

    @Override
    public IFileActions setFileType(FileTypes type) {
        try {
            data = new ArrayList<>();
            switch (type) {
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
    public IFileActions setArchivingType(ArchivingTypes type) {
        try {
            switch (type) {
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
    public IFileActions setEncryptionType(EncryptionTypes type) {
        try {
            switch (type) {
                case axx -> CryptoLib.decrypt(fileInTmpDir, currentKey);
            }
            fileName = ToolsLib.deleteLastExtension(fileName);
            fileInTmpDir = ToolsLib.deleteLastExtension(fileInTmpDir);
        }  catch (RuntimeException e) {
            throw new RuntimeException("Error! Check the correctness of encryption type.");
        }
        return this;
    }
    @Override
    public void form() {
        Path from = Paths.get(fileInTmpDir);
        Path to = Paths.get(fileName);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> parse() {
        if (data == null) {
            String lastExtension;
            while (!(lastExtension = ToolsLib.getLastExtension(fileName)).equals("No extension")) {
                for (enums.FileTypes type : enums.FileTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.setFileType(FileTypes.valueOf(lastExtension));
                        return data;
                    }
                }
                for (enums.ArchivingTypes type : enums.ArchivingTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.setArchivingType(ArchivingTypes.valueOf(lastExtension));
                        break;
                    }
                }
                for (enums.EncryptionTypes type : enums.EncryptionTypes.values()) {
                    if (lastExtension.equals(type.name())) {
                        this.setEncryptionType(EncryptionTypes.valueOf(lastExtension));
                        break;
                    }
                }
            }
        }
        return data;
    }
}
