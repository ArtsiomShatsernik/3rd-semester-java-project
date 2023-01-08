package org.main;

import enums.FileTypes;
import tools.*;

public class Main {
    public static void main(String[] args) {
    FileBuilder builder = new FileBuilder("input.txt");
    builder.setFileType(FileTypes.json).build();

    System.out.println();
    }
}