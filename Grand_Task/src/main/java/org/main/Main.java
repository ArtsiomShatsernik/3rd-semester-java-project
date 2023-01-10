package org.main;

import builders.FileFormBuilder;
import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;
import tools.TxtLib;

import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
    FileFormBuilder builder = new FileFormBuilder("input.txt");
    builder.setFileType(FileTypes.json).setEncryptionType(EncryptionTypes.axx).setArchivingType(ArchivingTypes.zip).build();
        ArrayList<String> aa = new ArrayList<>();
        try {
            aa = TxtLib.txtParse("input.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }
}