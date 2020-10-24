package textbook.fifth_edition.ch8_3_completeBinaryTree_and_Heap;

public class ArrayListCompleteBinaryTreeTester {

    public static void main(String[] args) {
        ArrayListCompleteBinaryTree<Integer> binaryTree = buildCompleteBinaryTree();
        System.out.println("Binary Tree preoder traversal representation: " + binaryTree.preorderToString(binaryTree.root()));
        System.out.println("Is empty ? " + binaryTree.isEmpty());
        System.out.println("Binary Tree has " + binaryTree.size() + " elements");
        System.out.println("Root is " + binaryTree.root().element());
        System.out.println("Left child node of root is " + binaryTree.left(binaryTree.root()).element());
        System.out.println("Right child node of root is " + binaryTree.right(binaryTree.root()).element());
        System.out.println("Removed element " + binaryTree.remove());
    }

    private static ArrayListCompleteBinaryTree<Integer> buildCompleteBinaryTree() {
        ArrayListCompleteBinaryTree<Integer> tree = new ArrayListCompleteBinaryTree<>();
        tree.add(2);
        tree.add(5);
        tree.add(3);
        tree.add(9);
        tree.add(6);
        tree.add(11);
        tree.add(4);
        tree.add(17);
        tree.add(10);
        tree.add(8);
        return tree;
    }
}
