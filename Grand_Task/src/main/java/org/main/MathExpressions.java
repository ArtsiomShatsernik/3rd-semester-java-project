package org.main;

import FileActions.FileParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MathExpressions {
    private String fileName = "answers";
    private ArrayList<Expression> expressions;
    private  ArrayList<String> results;

    public MathExpressions(ArrayList<String> expressions) {
        listOfStringsToExpressions(expressions);
    }

    public MathExpressions(String fileName) {
        this.fileName = fileName;
        ArrayList<String> temp;
        FileParser parser = new FileParser(fileName);
        temp = parser.parse();
        listOfStringsToExpressions(temp);
    }

    private void listOfStringsToExpressions(ArrayList<String> expressions) {
        this.expressions = new ArrayList<>();
        for (String t : expressions) {
            this.expressions.add(new Expression(t));
        }
    }

    public void compute() {
        this.results = new ArrayList<>();
        for (Expression next : expressions) {
            try {
                results.add(next.compute());
            } catch (RuntimeException e) {
                results.add(e.getMessage());
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName + "Answers"));
            for (String next : results) {
                writer.write(next + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
