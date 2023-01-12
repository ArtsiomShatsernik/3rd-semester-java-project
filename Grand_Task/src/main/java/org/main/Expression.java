package org.main;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final String exp;
    private final String simplifiedExp;
    private String result;

    public Expression(String exp) {
        this.exp = exp;
        this.simplifiedExp = simplify(this.exp);
        if (!isCorrect()) {
            throw new RuntimeException("Incorrect expression. Check parentheses");
        }
    }
    public String compute() {
        StringBuilder curString = new StringBuilder(simplifiedExp);
        while(curString.toString().contains("(")) {
            int firstID = getFirstBracketID(curString);
            int secondID = getSecondBracketID(curString) + firstID;
            curString.replace(firstID, secondID, computeRPN(curString.substring(firstID + 1, secondID - 1)));
        }
        this.result = computeRPN(String.valueOf(curString));
        return this.result;
    }
    public String simplify(String exp) {
        Pattern negativeNumber = Pattern.compile("\\(-\\d+\\)");
        Pattern numberInParentheses  = Pattern.compile("\\(\\d+\\)");
        StringBuilder curString = new StringBuilder(exp);
        while (true) {
            Matcher negativeMatcher = negativeNumber.matcher(curString);
            Matcher numberInParenthesesMatcher = numberInParentheses.matcher(curString);
            if (negativeMatcher.find()) {
                String into = curString.substring(negativeMatcher.start() + 1, negativeMatcher.end() - 1);
                curString.replace(negativeMatcher.start(), negativeMatcher.end(), into);
            } else if (numberInParenthesesMatcher.find() || negativeMatcher.find()) {
                String into = curString.substring(numberInParenthesesMatcher.start() + 1, numberInParenthesesMatcher.end() - 1);
                curString.replace(numberInParenthesesMatcher.start(), numberInParenthesesMatcher.end(), into);
            } else {
                break;
            }
        }
        return curString.toString();
    }
    public boolean isCorrect() {
        StringBuilder curString = new StringBuilder(this.simplifiedExp);
        try {
            while(curString.toString().contains("(")) {
                int firstID = getFirstBracketID(curString);
                int secondID = getSecondBracketID(curString) + firstID;
                curString.replace(firstID, secondID , "a");
            }
        } catch (RuntimeException e) {
            return false;
        }
        return !curString.toString().contains(")");
    }

    private int getFirstBracketID(StringBuilder string) throws RuntimeException {
        return string.lastIndexOf("(");
    }
    private int getSecondBracketID(StringBuilder string) throws RuntimeException {
        string = new StringBuilder(string.substring(getFirstBracketID(string)));
        if (string.toString().contains(")")) {
            return string.indexOf(")") + 1;
        } else {
            throw new RuntimeException("No \")\" in expression");
        }
    }

    public StringBuilder toRPN(StringBuilder part) {
        Pattern digitOrOperation = Pattern.compile("(-?\\d+(\\.\\d+)?)|([+\\-*/])");
        Matcher matcher;
        Stack<String> operations = new Stack<>();
        StringBuilder resString = new StringBuilder();
        while(true) {
            matcher = digitOrOperation.matcher(part);
            if(matcher.find()) {
                String symbol =  part.substring(matcher.start(), matcher.end());
                if (isNumeric(symbol)){
                    resString.append(symbol).append(" ");
                } else {
                    if (symbol.equals("-") || symbol.equals("+")) {
                        if (!operations.empty()) {
                            resString.append(operations.peek()).append(" ");
                            operations.pop();
                        }
                        operations.add(symbol);
                    } else if (symbol.equals("*") || symbol.equals("/")) {
                        if (!operations.empty() && (operations.peek().equals("*") || operations.peek().equals("/"))) {
                            resString.append(operations.peek()).append(" ");
                            operations.pop();
                        }
                        operations.add(symbol);
                    } else {
                        throw new RuntimeException("Unsupported operation!");
                    }
                }
            } else {
                break;
            }
            part = new StringBuilder(part.substring(matcher.end()));
            matcher.reset();
        }
        while(!operations.empty()) {
            resString.append(operations.peek()).append(" ");
            operations.pop();
        }
        return resString;
    }
    public String computeRPN (String exp) {
        StringBuilder RPNString = toRPN(new StringBuilder(exp));
        Stack<Double> calculation = new Stack<>();
        Pattern digitOrOperation = Pattern.compile("(-?\\d+(\\.\\d+)?)|([+\\-*/])");
        Matcher matcher;
        while (true) {
            matcher = digitOrOperation.matcher(RPNString);
            if (matcher.find()) {
                String symbol =  RPNString.substring(matcher.start(), matcher.end());
                if (isNumeric(symbol)) {
                    calculation.add(Double.valueOf(symbol));
                } else {
                    Double b = calculation.peek();
                    calculation.pop();
                    Double a = calculation.peek();
                    calculation.pop();
                    calculation.add(computeSimpleExpression(a, b, symbol));
                }
            } else {
                break;
            }
            RPNString = new StringBuilder(RPNString.substring(matcher.end()));
            matcher.reset();
        }
        return String.valueOf(calculation.peek());
    }
    private Double computeSimpleExpression(Double a, Double b, String op) throws RuntimeException {
        switch (op) {
            case "+" -> {
                return a + b;
            }
            case "-" -> {
                return a - b;
            } case "*" -> {
                return a * b;
            } case "/" -> {
                if (b.equals(0.0)) {
                    throw new RuntimeException("Division by zero");
                }
                return a / b;
            }
            default -> throw new RuntimeException("Unsupported operation");
        }
    }
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public String getExp() {
        return exp;
    }
}
