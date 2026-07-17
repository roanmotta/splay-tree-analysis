package structures;

// Interface comum a todas as arvores (BST, AVL, RedBlack, Splay).
// Permite que o Benchmark trate qualquer estrutura de forma uniforme.

public interface SearchTree<T extends Comparable<T>> {
    void add (T valor);
    boolean search(T valor);
    int height();
    int size();
}