package DataStructures;

/**
 * Classe de nó, utilizada em todas as árvores binárias de pesquisa. O Node é a base
 * para a implementação das estruturas. Foi implementado de uma forma que pudesse ser
 * utilizado nas diferentes árvores abordadas. Assim, apresenta atributos como height,
 * característico da AVL, como também color, característica da árvore preta e vermelha.
 */

public class Node<T> {
    public T value;
    public Node<T> left;
    public Node<T> right;
    public Node<T> parent;
    public int count;
    public int height; // AVL
    public boolean color; // RedBlack

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