import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static final List<Character> space = Arrays.asList('b', 'f', 'n', 'r', 't', '\'');

  public static void main(String[] args) {
    try {
      String input = Files.readString(Paths.get("./src/input.vc"));
      StringBuilder temp = new StringBuilder();
      StringBuilder output = new StringBuilder();
      StateMachine.state0.nextState(input, temp, output);
      FileWriter outputFile = new FileWriter("./src/output.vctok");
      outputFile.write(output.toString());
      outputFile.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private enum StateMachine {
    state0 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        if (line.isEmpty()) return;
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        switch (current) {
          case '+':
            state12.nextState(line, temp, output);
            break;
          case '-':
            state18.nextState(line, temp, output);
            break;
          case '*':
            state25.nextState(line, temp, output);
            break;
          case '/':
            state7.nextState(line, temp, output);
            break;
          case '>':
            state8.nextState(line, temp, output);
            break;
          case '=':
            state9.nextState(line, temp, output);
            break;
          case '<':
            state10.nextState(line, temp, output);
            break;
          case '!':
            state11.nextState(line, temp, output);
            break;
          case '|':
            state13.nextState(line, temp, output);
            break;
          case '\"':
            state15.nextState(line, temp, output);
            break;
          case '&':
            state29.nextState(line, temp, output);
          case '[', ']', '{', '}', '(', ')', ';', ',':
            state14.nextState(line, temp, output);
            break;
          default:
            // Check is number
            if (Character.isDigit(current)) {
              state1.nextState(line, temp, output);
              return;
            }
            // Check is alphabet character
            if (Character.isAlphabetic(current)) {
              state6.nextState(line, temp, output);
              return;
            }
            // Check if white space (space, tab, ...)
            if (Character.isWhitespace(current)) {
              temp.setLength(0);
              state0.nextState(line, temp, output);
            } else {
              throw new Exception(
                  "Tokenizer Error State 0:" + current + (int) current + "\n" + line);
            }
        }
      }
    },

    state1 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        switch (current) {
          case '.':
            temp.append(current);
            line = line.substring(1);
            state2.nextState(line, temp, output);
            break;
          case 'E':
            temp.append(current);
            line = line.substring(1);
            state3.nextState(line, temp, output);
            break;
          default:
            if (Character.isDigit(current)) {
              temp.append(current);
              line = line.substring(1);
              state1.nextState(line, temp, output);
            } else {
              System.out.println("int_literal");
              output.append(temp).append("\n");
              temp.setLength(0);
              state0.nextState(line, temp, output);
            }
        }
      }
    },

    state2 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (Character.isDigit(current)) {
          state4.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 2: " + current + (int) current + "\n" + line);
        }
      }
    },

    state3 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        switch (current) {
          case '+', '-':
            state5.nextState(line, temp, output);
            break;
          default:
            if (Character.isDigit(current)) {
              state5.nextState(line, temp, output);
            } else {
              throw new Exception(
                  "Tokenizer Error State 3: " + current + (int) current + "\n" + line);
            }
        }
      }
    },

    state4 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == 'E') {
          state3.nextState(line, temp, output);
        } else {
          if (Character.isDigit(current)) {
            state4.nextState(line, temp, output);
          } else {
            System.out.println("float_literal");
            output.append(temp).append("\n");
            temp.setLength(0);
            state0.nextState(line, temp, output);
          }
        }
      }
    },

    state5 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        if (Character.isDigit(current)) {
          temp.append(current);
          line = line.substring(1);
          state5.nextState(line, temp, output);
        } else {
          System.out.println("float_literal");
          output.append(temp).append("\n");
          temp.setLength(0);
          state0.nextState(line, temp, output);
        }
      }
    },

    state6 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        if (Character.isDigit(current) || Character.isAlphabetic(current)) {
          temp.append(current);
          line = line.substring(1);
          state6.nextState(line, temp, output);
        } else {
          System.out.println("identifier");
          output.append(temp).append("\n");
          temp.setLength(0);
          state0.nextState(line, temp, output);
        }
      }
    },

    state7 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        if (current == '*') {
          temp.append(current);
          line = line.substring(1);
          state17.nextState(line, temp, output);
          return;
        }
        if (current == '/') {
          temp.append(current);
          line = line.substring(1);
          state16.nextState(line, temp, output);
        } else {
          System.out.println("divide_operator");
          output.append(temp).append("\n");
          temp.setLength(0);
          state0.nextState(line, temp, output);
        }
      }
    },

    state8 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '=') {
          state21.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 8: " + current + (int) current + "\n" + line);
        }
      }
    },

    state9 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        if (current == '=') {
          temp.append(current);
          line = line.substring(1);
          state22.nextState(line, temp, output);
        } else {
          System.out.println("op_assign");
          output.append(temp).append("\n");
          temp.setLength(0);
          state0.nextState(line, temp, output);
        }
      }
    },

    state10 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '=') {
          state23.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 10: " + current + (int) current + "\n" + line);
        }
      }
    },

    state11 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '=') {
          state24.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 11: " + current + (int) current + "\n" + line);
        }
      }
    },

    state12 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("plus_operator");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state13 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '|') {
          state26.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 13: " + current + (int) current + "\n" + line);
        }
      }
    },

    state14 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("separator");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state15 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '\\') {
          state27.nextState(line, temp, output);
          return;
        }
        if (current == '\"') {
          state28.nextState(line, temp, output);
        } else {
          state15.nextState(line, temp, output);
        }
      }
    },

    state16 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '\n') {
          state31.nextState(line, temp, output);
        } else {
          state16.nextState(line, temp, output);
        }
      }
    },

    state17 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '*') {
          state19.nextState(line, temp, output);
        } else {
          state17.nextState(line, temp, output);
        }
      }
    },

    state18 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("minus_operator");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state19 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '/') {
          state20.nextState(line, temp, output);
        } else {
          state17.nextState(line, temp, output);
        }
      }
    },

    state20 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("comment_multiple_lines");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state21 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_greater_equal");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state22 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_equal");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state23 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_less_equal");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state24 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_different");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state25 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("multiply_operator");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state26 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_or");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state27 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (space.contains(current) || current == '\"' || current == '\\') {
          state15.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 27: " + current + (int) current + "\n" + line);
        }
      }
    },

    state28 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("String_literal");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state29 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        char current = line.charAt(0);
        temp.append(current);
        line = line.substring(1);
        if (current == '&') {
          state30.nextState(line, temp, output);
        } else {
          throw new Exception("Tokenizer Error State 27: " + current + (int) current + "\n" + line);
        }
      }
    },

    state30 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("op_and");
        output.append(temp).append("\n");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    },

    state31 {
      @Override
      public void nextState(String line, StringBuilder temp, StringBuilder output)
          throws Exception {
        System.out.println("comment_in_line");
        temp.setLength(0);
        state0.nextState(line, temp, output);
      }
    };

    public abstract void nextState(String line, StringBuilder temp, StringBuilder output)
        throws Exception;
  }
}
