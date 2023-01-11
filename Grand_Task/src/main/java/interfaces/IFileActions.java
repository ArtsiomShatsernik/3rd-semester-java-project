package interfaces;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;

public interface IFileActions {
    IFileActions setFileType(FileTypes type);
    IFileActions setArchivingType(ArchivingTypes type);
    IFileActions setEncryptionType(EncryptionTypes type);

    void form();
}
