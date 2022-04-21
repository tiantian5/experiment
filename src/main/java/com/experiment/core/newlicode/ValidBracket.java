package com.experiment.core.newlicode;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/04/02/9:39 上午
 * @Description: 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * <p>
 * 输入：s = "{[]}"
 * 输出：true
 */
public class ValidBracket {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {

        System.out.println((new ValidBracket()).isValid("()[]{}"));
        System.out.println((new ValidBracket()).isValid("([)]"));
        System.out.println((new ValidBracket()).isValid("{[]}"));
    }

}
