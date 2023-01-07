package org.main;

import tools.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
    FileBuilder builder = new FileBuilder("input.txt");
    builder.toXml();
    System.out.println();
    }
}