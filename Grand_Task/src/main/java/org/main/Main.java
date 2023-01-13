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
        builder.setFileType(FileTypes.json).setEncryptionType(EncryptionTypes.axx).setArchivingType(ArchivingTypes.zip).form();
        MathExpressions expressions = new MathExpressions("input.json.axx.zip");
        expressions.compute();
        System.out.println();
    }
}