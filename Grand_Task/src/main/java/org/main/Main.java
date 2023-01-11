package org.main;

import FileActions.FileFormer;
import FileActions.FileParser;
import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        FileFormer builder = new FileFormer("input.txt");
        builder.setFileType(FileTypes.json).setArchivingType(ArchivingTypes.zip).setEncryptionType(EncryptionTypes.axx).form();
        FileParser parser = new FileParser("input.json.axx.zip");
        parser.setArchivingType(ArchivingTypes.zip).setEncryptionType(EncryptionTypes.axx).setFileType(FileTypes.json).form();
        ArrayList<String> aa = new ArrayList<>();
        aa = parser.parse();
        System.out.println();
    }
}