package org.main;


import FileActions.FileFormer;
import UI.JavaFXApp;

public class Main {
    public static void main(String[] args) {
        FileFormer former = new FileFormer("input.txt");
        JavaFXApp app = new JavaFXApp();
        JavaFXApp.launchApp();
        System.out.println();
    }
}