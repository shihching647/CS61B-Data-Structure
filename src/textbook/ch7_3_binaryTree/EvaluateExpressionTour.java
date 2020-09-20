package textbook.ch7_3_binaryTree;

import textbook.ch6_2_ch_6_3_nodeList.Position;
import textbook.ch7_3_binaryTree.arithmetic_expression_term.ExpressionOperator;
import textbook.ch7_3_binaryTree.arithmetic_expression_term.ExpressionTerm;

/**
 * Compute the value of an arithmetic expression tree.
 */
public class EvaluateExpressionTour extends EulerTour<ExpressionTerm,Integer>  {

    public Integer execute(BinaryTree<ExpressionTerm> tree) {
        init(tree);
        return eulerTour(tree.root());
    }

    protected void visitRight(Position<ExpressionTerm> node, TourResult<Integer> result) {
        ExpressionTerm term = node.element();
        if(tree.isInternal(node)) {
            ExpressionOperator operator = (ExpressionOperator) term;
            operator.setOperands(result.left, result.right);
        }
        result.out = term.getValue();
    }

    public static void main(String[] args) {
        BinaryTree<ExpressionTerm> expressionTree = BuildExpressionTree.buildExpressionTree("((1+2)+(3+4))");
        EvaluateExpressionTour evaluateTour = new EvaluateExpressionTour();
        System.out.println("Result: " + evaluateTour.execute(expressionTree));
    }
}
