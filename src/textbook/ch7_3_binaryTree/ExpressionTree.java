package textbook.ch7_3_binaryTree;

import textbook.ch5_1.ArrayStack;
import textbook.ch5_1.Stack;
import textbook.ch6_2_ch_6_3_nodeList.Position;


public class ExpressionTree {
    public static void main(String[] args) {
        String testExpression = "((3 * 7) + (4 ^ 2))";
        LinkedBinaryTree<Character> expressionTree = buildExpressionTree(testExpression);
        System.out.println(LinkedBinaryTree.toStringPostorder(expressionTree, expressionTree.root()));
        System.out.println(LinkedBinaryTree.toStringPreorder(expressionTree, expressionTree.root()));
        System.out.println(LinkedBinaryTree.toStringInorder(expressionTree, expressionTree.root()) + " = "
                + evaluateExpression(expressionTree, expressionTree.root()));
    }

    /**
     * Returns a binary Tree representing arithmetic expression
     * ex: ((1 + 2) * 3) is a FPE, however ((1 + 2) * (3)) is not
     *
     * Algorithm:
     * 1.When we encounter a variable or operator x, we create a single-node binary tree whose
     *   root stores x and we push on the stack.
     *  2.When we encounter ")", we pop the top trees from the stack which represent a subexpression(E1 operator E2)
     *    We the attach the trees for E1 and E2 to the one for operator, and push the result back to stack.
     *
     * @param expression a fully-parenthesized arithmetic expression
     * @return A binary Tree representing arithmetic expression
     */
    public static LinkedBinaryTree<Character> buildExpressionTree(String expression) {
        expression = expression.replace(" ", "");
        Stack<LinkedBinaryTree<Character>> stack = new ArrayStack<>();
        for(int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if(ch == '(') {
                continue;
            } else if(ch == ')') {
                LinkedBinaryTree<Character> tree2 = stack.pop(); //tree representing E2
                LinkedBinaryTree<Character> tree = stack.pop();  //tree representing operator
                LinkedBinaryTree<Character> tree1 = stack.pop(); //tree representing E1
                tree.attach(tree.root(), tree1, tree2);
                stack.push(tree);
            } else { //variables or operators
                LinkedBinaryTree<Character> tree = new LinkedBinaryTree<>();
                tree.addRoot(ch);
                stack.push(tree);
            }
        }
        return stack.pop();
    }

    // Evaluate the arithmetic expression tree
    public static double evaluateExpression(BinaryTree<Character> expressionTree, Position<Character> node) {
        if(expressionTree.isInternal(node)) {
            char operator = node.element();
            double x = evaluateExpression(expressionTree, expressionTree.left(node));
            double y = evaluateExpression(expressionTree, expressionTree.right(node));
            return evaluate(x, y, operator);
        } else {
            return (Double.parseDouble(node.element().toString()));
        }
    }

    private static double evaluate(double x, double y, char operator) {
        double result = 0;
        switch(operator) {
            case '+' : result = x + y; break;
            case '-' : result = x - y; break;
            case '*' : result = x * y; break;
            case '/' : result = x / y; break;
            case '^' : result = Math.pow(x, y); break;
            default : throw new ArithmeticException("The operator is not defined.");
        }
        return result;
    }


}
