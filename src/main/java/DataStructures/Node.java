package DataStructures;

public class Node<T> {
    public T value;
    public Node<T> left;
    public Node<T> right;
    public Node<T> parent;
    public int count;
    public int height; // AVL
    public boolean color; // RedBlack se precisar

    public Node(T v) {
        this.value = v;
        this.count = 1;
        this.height = 0;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public boolean hasOnlyLeftChild() {
        return this.left != null && this.right == null;
    }

    public boolean hasOnlyRightChild() {
        return this.left == null && this.right != null;
    }
}