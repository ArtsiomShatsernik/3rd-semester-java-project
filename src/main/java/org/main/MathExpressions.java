package org.main;

import FileActions.FileParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MathExpressions {
    private String fileName = "input";
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

    public void computeToFile() {
        this.results = compute();
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
    public ArrayList<String> compute() {
        ArrayList<String> output = new ArrayList<>();
        for (Expression next : expressions) {
            try {
                output.add(next.compute());
            } catch (RuntimeException e) {
                output.add(e.getMessage());
            }
        }
        return output;
    }
}
