package org.main;

import tools.*;

public class Main {
    public static void main(String[] args) {
    FileBuilder builder = new FileBuilder("input.txt");
    builder.toXml().getResult();
    System.out.println();
    }
}