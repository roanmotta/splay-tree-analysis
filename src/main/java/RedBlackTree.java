// Implementação de uma árvore vermelha e preta para fins comparativos com a Splay Tree.
public class RedBlackTree<T extends Comparable<T>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node<T> root;
    private int size;
    private final Node<T> NIL;

    public RedBlackTree() {
        this.NIL = new Node (null);
        this.NIL.color = BLACK;
        this.NIL.left = this.NIL.right = this.NIL.parent = this.NIL;
        this.root = this.NIL;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.root == NIL;
    }

    public int size() {
        return this.size;
    }

    public void add(T v) {

        Node<T> newNode = new Node<>(v);
        Node<T> parent = this.NIL;
        Node<T> aux = this.root;

        while (aux != NIL) {
            parent = aux;

            int comp = v.compareTo(aux.value);

            if (comp == 0) {
                aux.count ++;
                return;
            }
            else if (comp > 0) {
                aux = aux.right;
            }
            else {
                aux = aux.left;
            }
        }

        if (parent == NIL) this.root = newNode;
        else if (v.compare(parent.v) < 0) parent.left = newNode;
        else parent.right = newNode;

        newNode.parent = parent;
        newNode.left = newNode.right = NIL;
        size++;

        // Método para ajustar a arvore
    }

    public Node<T> search(T v) {
        Node<T> aux = this.root;
        while (aux != NIL) {
            int comp = v.compareTo(aux.value);
            if (comp == 0) {
                return aux;
            } else if (comp < 0) {
                aux = aux.left;
            } else {
                aux = aux.right;
            }
        }
        return null;
    }

    public int height() {
        return height(this.root);
    }

    private int height(Node<T> n) {
        if (n == NIL)
            return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    public boolean equals(BasicBST<T> outra) {
        if (outra == null)
            return false;
        return equals(this.root, outra.root);
    }

    private boolean equals(Node<T> a, Node<T> b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null || a.value.compareTo(b.value) != 0)
            return false;
        return equals(a.left, b.left) && equals(a.right, b.right);
    }

    public Node<T> min() {
        if (isEmpty()) return null;
        return min(this.root);
    }

    private Node<T> min(Node<T> node) {
        if (node.left == NIL) return node;
        return min(node.left);
    }

    public Node<T> sucessor(Node<T> node) {
        if (node == NIL)
            return null;

        if (node.right != NIL) {
            return min(node.right);
        } else {
            Node<T> aux = node.parent;

            while (aux != NIL && aux.value.compareTo(node.value) < 0) {
                aux = aux.parent;
            }

            return aux;
        }
    }

    public boolean remove(T value) {
        Node<T> toRemove = search(value);
        if (toRemove != null) {
            remove(toRemove);
            this.size--;
            return true;
        }
        return false;
    }

    private void remove(Node<T> toRemove) {
        // Nó folha:
        if (toRemove.isLeaf()) {
            if (toRemove == this.root) {
                this.root = null;
            } else {
                if (toRemove == toRemove.parent.left) {
                    toRemove.parent.left = null;
                } else {
                    toRemove.parent.right = null;
                }
            }
        }
        // Só filho a esquerda:
        else if (toRemove.hasOnlyLeftChild()) {
            if (toRemove == this.root) {
                this.root = toRemove.left;
                this.root.parent = null;
            } else {
                toRemove.left.parent = toRemove.parent;
                if (toRemove == toRemove.parent.left) {
                    toRemove.parent.left = toRemove.left;
                } else {
                    toRemove.parent.right = toRemove.left;
                }
            }
        }
        // Só filho a direita:
        else if (toRemove.hasOnlyRightChild()) {
            if (toRemove == this.root) {
                this.root = toRemove.right;
                this.root.parent = null;
            } else {
                toRemove.right.parent = toRemove.parent;
                if (toRemove == toRemove.parent.left) {
                    toRemove.parent.left = toRemove.right;
                } else {
                    toRemove.parent.right = toRemove.right;
                }
            }
        }
        // 2 filhos:
        else {
            Node<T> sucessor = sucessor(toRemove);
            toRemove.value = sucessor.value;
            remove(sucessor);
        }
    }

    protected class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        int count;
        boolean color;

        Node(T v) {
            this.value = v;
            this.count = 1;
            this.color = RED;
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
}
