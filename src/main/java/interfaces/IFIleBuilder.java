package interfaces;

import enums.*;
public interface IFIleBuilder {
    String defaultKey = "QfTjWnZq";
    IFIleBuilder setFileType(FileTypes type);
    IFIleBuilder setArchivingType(ArchivingTypes type);
    IFIleBuilder setEncryptionType(EncryptionTypes type);
    IFIleBuilder setEncryptionKey(String key);
}
