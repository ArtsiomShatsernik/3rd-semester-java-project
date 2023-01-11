package org.main;

import FileActions.FileParser;

import java.util.ArrayList;

public class MathExpressions {
    private ArrayList<Expression> expressions;

    public MathExpressions(ArrayList<String> expressions) {
        listOfStringsToExpressions(expressions);
    }

    public MathExpressions(String fileName) {
        ArrayList<String> temp = new ArrayList<>();
        FileParser parser = new FileParser(fileName);
        temp = parser.parse();
        listOfStringsToExpressions(temp);
    }

    private void listOfStringsToExpressions(ArrayList<String> expressions) {
        for (String t : expressions) {
            this.expressions.add(new Expression(t));
        }
    }
    public ArrayList <String> compute() {
        ArrayList<String> res = new ArrayList<>();

        return  res;

    }
}
