package textbook.ch7_3_binaryTree.arithmetic_expression_term;

/**
 * Class for a the addition operator in an arithmetic expression
 */
public class AdditionOperator extends ExpressionOperator {
    public Integer getValue() {
        return firstOperand + secondOperand;
    }
    public String toString() {
        return new String("+");
    }
}
