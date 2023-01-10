package builders;

import enums.*;
import interfaces.IFileActions;
import tools.JsonLib;
import tools.ToolsLib;
import tools.XmlLib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FileParseBuilder implements IFileActions {
    ArrayList<String> data;
    private String fileName;
    private final Path tmpDir;
    private String fileInTmpDir;
    private final String defaultKey = "QfTjWnZq";
    private String currentKey = defaultKey;

    FileParseBuilder(String fileName) {
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
    FileParseBuilder(String fileName, String currentKey) {
        this(fileName);
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }

    @Override
    public IFileActions setFileType(FileTypes type) {
        data = new ArrayList<>();
        switch (type) {
            case xml -> {
                data = XmlLib.xmlParse(fileInTmpDir);
            }
            case json -> {
                data = JsonLib.jsonParse(fileInTmpDir);
            }
            case txt -> {

            }
        }
        return this;
    }

    @Override
    public IFileActions setArchivingType(ArchivingTypes type) {
        return this;
    }

    @Override
    public IFileActions setEncryptionType(EncryptionTypes type) {
        return this;
    }

    @Override
    public void build() {

    }
}
