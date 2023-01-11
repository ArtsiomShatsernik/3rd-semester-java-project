package org.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private String exp;

    public Expression(String exp) {
        this.exp = exp;
    }
    public String compute() {
        String res = null;
        StringBuilder curString = new StringBuilder(this.exp);

        return res;
    }
    public void simplify() {
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
        this.exp = curString.toString();
    }
    public boolean isCorrect() {

        return true;
    }
    private boolean checkParentheses() {

        return true;
    }

    public String getExp() {
        return exp;
    }
}
