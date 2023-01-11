package interfaces;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;
import org.main.MathExpressions;

import java.util.ArrayList;

public interface IFileActions {
    IFileActions setFileType(FileTypes type);
    IFileActions setArchivingType(ArchivingTypes type);
    IFileActions setEncryptionType(EncryptionTypes type);

    void form();
}
