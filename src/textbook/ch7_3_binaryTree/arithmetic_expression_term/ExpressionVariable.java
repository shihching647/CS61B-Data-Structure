package textbook.ch7_3_binaryTree.arithmetic_expression_term;

/**
 * Class for a variable of an arithmetic expression
 */
public class ExpressionVariable extends ExpressionTerm {
    protected Integer var;
    public ExpressionVariable(Integer var) { this.var = var;}
    public void setVariable(Integer var) { this.var = var;}
    public Integer getValue() { return var;}
    public String toString() { return var.toString();}
}
