package interfaces;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;

public interface IFileActions {
    IFileActions fileType(FileTypes type);
    IFileActions archivingType(ArchivingTypes type);
    IFileActions encryptionType(EncryptionTypes type);
    void make();
}
