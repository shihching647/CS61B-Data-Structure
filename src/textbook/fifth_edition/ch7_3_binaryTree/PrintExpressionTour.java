package textbook.fifth_edition.ch7_3_binaryTree;

import textbook.fifth_edition.ch6_2_ch_6_3_nodeList.Position;
import textbook.fifth_edition.ch7_3_binaryTree.arithmetic_expression_term.ExpressionTerm;

/**
 * Print out the expression associated with an arithmetic expression tree.
 */
public class PrintExpressionTour extends EulerTour<ExpressionTerm, String> {

    public String execute(BinaryTree<ExpressionTerm> tree) {
        init(tree);
        System.out.print("Expression: ");
        eulerTour(tree.root());
        System.out.println();
        return null;
    }

    public void visitLeft(Position<ExpressionTerm> node, TourResult<String> result) {
        if(tree.isInternal(node))
            System.out.print("(");
    }

    public void visitBelow(Position<ExpressionTerm> node, TourResult<String> result) {
        System.out.print(node.element());
    }

    public void visitRight(Position<ExpressionTerm> node, TourResult<String> result) {
        if(tree.isInternal(node))
            System.out.print(")");
    }

    public static void main(String[] args) {
        BinaryTree<ExpressionTerm> expressionTree = BuildExpressionTree.buildExpressionTree("((1+2)+(3+4))");
        PrintExpressionTour printTour = new PrintExpressionTour();
        printTour.execute(expressionTree);
    }
}
