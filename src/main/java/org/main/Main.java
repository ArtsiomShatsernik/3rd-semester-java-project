package org.main;


import FileActions.FileFormer;
import FileActions.FileParser;
import UI.JavaFXApp;
import builders.FileActionsBuilder;
import enums.ArchivingTypes;
import enums.EncryptionTypes;
import enums.FileTypes;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileActionsBuilder builder = new FileActionsBuilder("input.json.axx.zip");
        FileFormer former = builder.setArchivingType(ArchivingTypes.zip).setEncryptionType(EncryptionTypes.axx).setFileType(FileTypes.json).buildFormerForParser();
        former.form();
        FileParser parser = builder.buildParser();
        ArrayList<String> test = parser.parse();
        MathExpressions mathExpressions = new MathExpressions("input.xml.jar.axx");
        mathExpressions.compute();
        JavaFXApp app = new JavaFXApp();
        JavaFXApp.launchApp();
        System.out.println();
    }
}