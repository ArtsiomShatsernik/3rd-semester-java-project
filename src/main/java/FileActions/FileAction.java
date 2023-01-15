package FileActions;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileOperations;
import enums.FileTypes;

public class FileAction {
    public FileTypes fileType;
    public ArchivingTypes archivingType = null;
    public EncryptionTypes encryptionType = null;
    public FileOperations firstOperation;
    public FileOperations secondOperation;
    private final String defaultKey = "QfTjWnZq";
    public String currentKey = defaultKey;
    public String fileName;
    protected String fileInTmpDir;
    public void changeEncryptionKey(String currentKey) {
        if (currentKey.length() < 8) {
            throw new RuntimeException("Incorrect key. Key must be 8 characters or more.");
        }
        this.currentKey = currentKey;
    }
    protected void identifyByOperation(FileOperations operation) {
        if (operation == null) {
            return;
        }
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
