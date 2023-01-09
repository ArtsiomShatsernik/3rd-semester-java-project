package org.main;

import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;
import tools.*;


public class Main {
    public static void main(String[] args) {
    FileBuilder builder = new FileBuilder("input.txt");
    builder.setFileType(FileTypes.json).setEncryptionType(EncryptionTypes.axx).setArchivingType(ArchivingTypes.zip).build();
    ArchivingLib.unpackZip("check.zip");
    System.out.println();
    }
}