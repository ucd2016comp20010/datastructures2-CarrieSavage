package project20280.exercises;
import project20280.interfaces.Stack;
import project20280.stacksqueues.ArrayStack;

public class ParenthesesChecker {
    public static boolean checkParentheses(String input) {
        Stack<Character> stack = new ArrayStack<>();

        for (char ch : input.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    System.out.println("Missing parentheses " + ch);
                    return false;
                }

                char top  = stack.pop();

                if (!matches(top, ch)){
                    System.out.println(ch + " does not match " + top);
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String [] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct
                "a{b[c]d}e", // correct
                "a{b(c]d}e", // not correct; ] doesn't ←- match
                "a[b{c}d]e}", // not correct; nothing ←-matches final }
                "a{b(c) ", // not correct; Nothing ←-matches opening {
                "][]][][[]][]][][[[", //
                "(((abc))((d)))))", //
        };

        for(String input : inputs) {
            boolean isBalanced = ParenthesesChecker.checkParentheses(input);
            System.out.println("isBalanced " + (isBalanced ? " yes! " : " no! ") + input);
            }
    }
}
