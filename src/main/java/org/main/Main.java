package org.main;


import FileActions.FileFormer;
import UI.JavaFXApp;
import enums.FileOperations;
import enums.FileTypes;

public class Main {
    public static void main(String[] args) {
        FileFormer former = new FileFormer("input.txt", FileTypes.xml, FileOperations.jar, FileOperations.axx);
        former.form();
        MathExpressions mathExpressions = new MathExpressions("input.xml.jar.axx");
        mathExpressions.compute();
        JavaFXApp app = new JavaFXApp();
        JavaFXApp.launchApp();
        System.out.println();
    }
}