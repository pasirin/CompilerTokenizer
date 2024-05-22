public class Tree {
  static class RootNode {
    public Node root = new Node();
    public String preOperator = "";
    public String preValue = "";

    public RootNode() {}

    public void add(String input) throws Exception {
      if (preOperator.matches("[*/]") && !input.matches("[*/]") && !preValue.matches("[-+*/]")) {
        Node temp = newNode(input);
        temp.left = this.root;
        this.root = temp;
      }
      this.root = this.root.addNode(newNode(input));
      if (input.matches("[-+*/]")) this.preOperator = input;
      this.preValue = input;
    }

    public void add(String[] array) throws Exception {
      for (String input : array) {
        if (preOperator.matches("[*/]") && !input.matches("[*/]") && !preValue.matches("[-+*/]")) {
          Node temp = newNode(input);
          temp.left = this.root;
          this.root = temp;
        } else {
          this.root = this.root.addNode(newNode(input));
        }
        if (input.matches("[*/]")) this.preOperator = input;
        this.preValue = input;
      }
    }

    public void print() {
      System.out.println(printTree(this.root));
    }
  }

  static class Node {
    public String data;
    public Node left, right;

    public Node() {}

    public boolean isNotFull() {
      return left == null || right == null;
    }

    public Node addNode(Node temp) throws Exception {
      // If empty
      if (this.data == null) {
        return temp;
      }
      // If Current Node Is Equal
      if (this.data.contains("=")) {
        if (this.right != null) {
          this.right = this.right.addNode(temp);
        } else {
          this.right = temp;
        }
        return this;
      }
      // If Temp Is Equal
      if (temp.data.contains("=")) {
        temp.left = this;
        return temp;
      }
      // Current Is Operator
      if (this.data.matches("[-+*/]")) {
        // Temp Is Number
        if (!temp.data.matches("[-+*/]")) {
          if (this.right == null) {
            this.right = temp;
          } else {
            this.right = this.right.addNode(temp);
          }
          return this;
        }
        // Temp Is Operator +-
        if (temp.data.matches("[-+]")) {
          if (this.isNotFull()) {
            throw new Exception(
                "Input Exception At Temp Operator +-: \n"
                    + printTree(this)
                    + "\n"
                    + printTree(temp));
          }
          temp.left = this;
          return temp;
        }
        // Temp Is Operator */
        if (temp.data.matches("[*/]")) {
          if (this.isNotFull()) {
            throw new Exception(
                "Input Exception At Temp Operator */: \n"
                    + printTree(this)
                    + "\n"
                    + printTree(temp));
          }
          this.right = this.right.addNode(temp);
          return this;
        }
      }
      // Current Is Number
      if (!temp.data.matches("[-+*/]")) {
        throw new IllegalArgumentException(
            "\nNode: "
                + this.data
                + " Is Already A Number: \n"
                + printTree(this)
                + "\n"
                + printTree(temp));
      }
      temp.left = this;
      return temp;
    }
  }

  static String printTree(Node root) {
    if (root.left != null && root.right != null) {
      return "(" + printTree(root.left) + " " + root.data + " " + printTree(root.right) + ")";
    } else if (root.left == null && root.right != null) {
      return "( LeftUndefined " + root.data + " " + printTree(root.right);
    } else if (root.left != null) {
      return "(" + printTree(root.left) + " " + root.data + " RightUndefined)";
    }
    return root.data;
  }

  static Node newNode(String data) {
    Node temp = new Node();
    temp.data = data;
    temp.left = null;
    temp.right = null;
    return temp;
  }

  public static void main(String[] args) throws Exception {
    RootNode test = new RootNode();
    test.add(new String[] {"a", "+", "b", "*", "c", "-", "d", "/", "e"});
    test.print();
  }
}
