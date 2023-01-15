package builders;

import FileActions.FileFormer;
import FileActions.FileParser;
import interfaces.IFIleBuilder;
import enums.*;
import tools.ToolsLib;

public class FileActionsBuilder implements IFIleBuilder {
    public String fileName;
    public FileTypes fileType = FileTypes.txt;
    public ArchivingTypes archivingType = null;
    public EncryptionTypes encryptionType = null;
    public FileOperations firstOperation = null;
    public FileOperations secondOperation = null;
    public String encryptionKey = defaultKey;

    public FileActionsBuilder(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public FileActionsBuilder setFileType(FileTypes fileType) {
        this.fileType = fileType;
        return this;
    }

    @Override
    public FileActionsBuilder setArchivingType(ArchivingTypes archivingType) {
        setOperations(FileOperations.valueOf(archivingType.toString()));
        this.archivingType = archivingType;
        return this;
    }

    @Override
    public FileActionsBuilder setEncryptionType(EncryptionTypes encryptionType) {
        setOperations(FileOperations.valueOf(encryptionType.toString()));
        this.encryptionType = encryptionType;
        return this;
    }

    @Override
    public FileActionsBuilder setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
        return this;
    }
    public FileFormer buildFormer() {
        FileFormer former = new FileFormer(fileName, fileType, firstOperation, secondOperation);
        former.currentKey = encryptionKey;
        return former;
    }
    public FileParser buildParser() {
        FileParser parser = new FileParser(fileName, fileType, firstOperation, secondOperation);
        parser.currentKey = encryptionKey;
        return parser;
    }
    public FileParser buildParserForBuilder() {
        fileName = ToolsLib.deleteLastExtension(fileName) + "." + fileType + "." + firstOperation + "." + secondOperation;
        FileParser parser = new FileParser(fileName, fileType, secondOperation, firstOperation);
        parser.currentKey = encryptionKey;
        return parser;
    }
    public FileFormer buildFormerForParser() {
        String temp, tempName = fileName;
        while(!(temp = ToolsLib.deleteLastExtension(tempName)).equals("No extension")) {
            tempName = temp;
        }
        FileFormer former = new FileFormer(tempName + ".txt", fileType, secondOperation, firstOperation);
        former.currentKey = encryptionKey;
        return former;
    }
    private void setOperations(FileOperations type) {
        if (firstOperation == null) {
            firstOperation = type;
        } else if (secondOperation == null) {
            secondOperation = type;
        } else {
            throw new RuntimeException("Can't set type to  + " + type + " because it already defined");
        }
    }
}
