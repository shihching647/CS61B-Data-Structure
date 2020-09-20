package textbook.ch7_3_binaryTree.arithmetic_expression_term;

/**
 * Class for a operator of an arithmetic expression
 */

public class ExpressionOperator extends ExpressionTerm {
    protected Integer firstOperand, secondOperand;
    public void setOperands(Integer firstOperand, Integer secondOperand ) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }
}
