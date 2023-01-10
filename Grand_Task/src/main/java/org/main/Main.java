package org.main;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;
import tools.*;


public class Main {
    public static void main(String[] args) {
    FileBuilder builder = new FileBuilder("input.txt");
    builder.fileType(FileTypes.json).encryptionType(EncryptionTypes.axx).archivingType(ArchivingTypes.zip).make();
    }
}