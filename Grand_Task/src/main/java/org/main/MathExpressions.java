package org.main;

import tools.ToolsLib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class MathExpressions {
    public static ArrayList<String> exp;
    private String fileName = null;

    public MathExpressions(String fileName) {
       this.fileName = fileName;
    }
    public MathExpressions(ArrayList <String> exp) {
        this.exp = exp;
    }
    public void formFile() {
        if (fileName != null) {

        } else {
            System.out.println("File not built");
        }
    }
}
