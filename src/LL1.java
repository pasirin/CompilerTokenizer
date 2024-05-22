import java.util.*;

public class LL1 {
  /*
  1.  A  →  BA'
  2.  A' → +BA'
  3.  A' → -BA'
  4.  A' →  ε
  5.  B  →  CB'
  6.  B' →  *CB'
  7.  B' →  /CB'
  8.  B' →  ε
  9.  C  →  (A)
  10. C  →  n
      n  +  -  *  /  (  )  $
  A   1              1
  A'     2  3           4  4
  B   5              5
  B'     8  8  6  7     8  8
  C  10              9
  */
  public static final String NumCharRegex = "[a-zA-Z0-9]+";

  public static final String EPSILON = "ε";

  public static List<String> input =
      new ArrayList<>(Arrays.asList("(", "a", "*", "(", "b", "+", "c", ")", ")"));

  public static Stack<String> stack = new Stack<>();

  public static StringBuilder output = new StringBuilder();

  public static void main(String[] args) {
    // Init
    stack.add("$");
    stack.add("C");
    input.add("$");
    int pointer = 0;
    while (!stack.isEmpty()) {
      if (pointer >= input.size()) {
        break;
      }
      String temp1 = input.get(pointer);
      String seek = stack.peek();
      if (Objects.equals(temp1, stack.peek())
          || (temp1.matches(NumCharRegex) && Objects.equals(stack.peek(), "n"))) {
        pointer++;
        output.append(stack.pop());
      } else controller(temp1, seek);
    }
    System.out.println(output);
  }

  public static void controller(String lookAhead, String stackToken) {
    if (Objects.equals(stackToken, EPSILON)) {
      stack.pop();
      return;
    }
    // C
    if (Objects.equals(stackToken, "C")) {
      // n
      if (lookAhead.matches(NumCharRegex)) {
        stack.pop();
        stack.add("n");
        return;
      }
      // (
      if (lookAhead.equals("(")) {
        stack.pop();
        stack.add(")");
        stack.add("A");
        stack.add("(");
        return;
      }
      throw new IllegalArgumentException("Exception at C: " + lookAhead);
    }
    // A
    if (Objects.equals(stackToken, "A")) {
      // n | (
      if (lookAhead.matches(NumCharRegex) || lookAhead.equals("(")) {
        stack.pop();
        stack.add("A'");
        stack.add("B");
        return;
      }
      throw new IllegalArgumentException("Exception at A: " + lookAhead);
    }
    // A'
    if (Objects.equals(stackToken, "A'")) {
      // +
      if (Objects.equals(lookAhead, "+")) {
        stack.pop();
        stack.add("A'");
        stack.add("B");
        stack.add("+");
        return;
      }
      // -
      if (Objects.equals(lookAhead, "-")) {
        stack.pop();
        stack.add("A'");
        stack.add("B");
        stack.add("-");
        return;
      }
      // ) | $
      if (Objects.equals(lookAhead, ")") || Objects.equals(lookAhead, "$")) {
        stack.pop();
        stack.add(EPSILON);
        return;
      }
      throw new IllegalArgumentException("Exception at A': " + lookAhead);
    }
    // B
    if (Objects.equals(stackToken, "B")) {
      // n | (
      if (lookAhead.matches(NumCharRegex) || lookAhead.equals("(")) {
        stack.pop();
        stack.add("B'");
        stack.add("C");
        return;
      }
      throw new IllegalArgumentException("Exception at B: " + lookAhead);
    }
    // B'
    if (Objects.equals(stackToken, "B'")) {
      // *
      if (Objects.equals(lookAhead, "*")) {
        stack.pop();
        stack.add("B'");
        stack.add("C");
        stack.add("*");
        return;
      }
      // /
      if (Objects.equals(lookAhead, "/")) {
        stack.pop();
        stack.add("B'");
        stack.add("C");
        stack.add("/");
        return;
      }
      // + | - | ) | $
      if (Objects.equals(lookAhead, "+")
          || Objects.equals(lookAhead, "-")
          || Objects.equals(lookAhead, ")")
          || Objects.equals(lookAhead, "$")) {
        stack.pop();
        stack.add(EPSILON);
        return;
      }
      throw new IllegalArgumentException("Exception at B': " + lookAhead);
    }
    throw new IllegalArgumentException("No Token Matched: " + stackToken + " " + lookAhead);
  }
}
