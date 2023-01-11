package org.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final String exp;

    public Expression(String exp) {
        this.exp = exp;
    }
    public String compute() {
        String res = null;
        StringBuilder curString = new StringBuilder(this.exp);

        return res;
    }
    public String simplify() {
        Pattern negativeNumber = Pattern.compile("\\(\\-\\d+\\)");
        Pattern numberInParentheses  = Pattern.compile("\\(\\d+\\)");
        StringBuilder curString = new StringBuilder(this.exp);
        while (true) {
            Matcher negativeMatcher = negativeNumber.matcher(curString);
            Matcher numberInParenthesesMatcher = numberInParentheses.matcher(curString);
            if (negativeMatcher.find()) {
                curString.setCharAt(negativeMatcher.start(), '[');
                curString.setCharAt(negativeMatcher.end() - 1, ']');
            } else if (numberInParenthesesMatcher.find()) {
                String into = curString.substring(numberInParenthesesMatcher.start() + 1, numberInParenthesesMatcher.end() - 1);
                curString.replace(numberInParenthesesMatcher.start(), numberInParenthesesMatcher.end(), into);
            } else {
                break;
            }
        }
        return curString.toString();
    }
    public boolean isCorrect() {
        String curString = simplify();
        StringBuilder curStringBuilder = new StringBuilder(curString);
        return checkParentheses(curStringBuilder);
    }
    private boolean checkParentheses(StringBuilder curString) {
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

    public String getExp() {
        return exp;
    }
}
