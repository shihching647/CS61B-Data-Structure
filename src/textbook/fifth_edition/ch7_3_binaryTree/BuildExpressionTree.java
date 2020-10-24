package textbook.fifth_edition.ch7_3_binaryTree;

import textbook.fifth_edition.ch5_1.ArrayStack;
import textbook.fifth_edition.ch5_1.Stack;
import textbook.fifth_edition.ch7_3_binaryTree.arithmetic_expression_term.AdditionOperator;
import textbook.fifth_edition.ch7_3_binaryTree.arithmetic_expression_term.ExpressionTerm;
import textbook.fifth_edition.ch7_3_binaryTree.arithmetic_expression_term.ExpressionVariable;

/**
 * Build an arithmetic expression tree.
 */
public class BuildExpressionTree {

    public static BinaryTree<ExpressionTerm> buildExpressionTree(String expression) {
        expression = expression.replace(" ", "");
        Stack<LinkedBinaryTree<ExpressionTerm>> stack = new ArrayStack<>();
        for(int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if(ch == '(') {
                continue;
            } else if(ch == ')'){
                LinkedBinaryTree<ExpressionTerm> tree2 = stack.pop();
                LinkedBinaryTree<ExpressionTerm> tree = stack.pop();
                LinkedBinaryTree<ExpressionTerm> tree1 = stack.pop();
                tree.attach(tree.root(), tree1, tree2);
                stack.push(tree);
            } else {
                LinkedBinaryTree<ExpressionTerm> tree = new LinkedBinaryTree<>();
                tree.addRoot(getExpressionTerm(ch));
                stack.push(tree);
            }
        }
        return stack.pop();
    }

    private static ExpressionTerm getExpressionTerm(char ch) {
        ExpressionTerm result = null;
        switch(ch) {
            case '+' : result = new AdditionOperator(); break;
            default: result = new ExpressionVariable(Integer.parseInt(Character.toString(ch))); break;
        }
        return result;
    }
}
