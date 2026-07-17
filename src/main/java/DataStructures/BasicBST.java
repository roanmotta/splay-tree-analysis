package DataStructures;
// Implementação de uma BST básica sem balanceamento para fins comparativos com a Splay Tree.
public class BasicBST<T extends Comparable<T>> implements SearchTree<T> {

    private Node<T> root;
    private int size;

    public BasicBST() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(T v) {
        if (isEmpty()) {
            this.root = new Node<>(v);
            this.size++;
            return;
        }

        Node<T> aux = this.root;

        while (aux != null) {
            int comp = v.compareTo(aux.value);

            if (comp < 0) {
                if (aux.left == null) {
                    Node<T> newNode = new Node<>(v);
                    aux.left = newNode;
                    newNode.parent = aux;
                    this.size++;
                    return;
                }
                aux = aux.left;
            } else if (comp > 0) {
                if (aux.right == null) {
                    Node<T> newNode = new Node<>(v);
                    aux.right = newNode;
                    newNode.parent = aux;
                    this.size++;
                    return;
                }
                aux = aux.right;
            } else {
                aux.count++;
                return;
            }
        }
    }

    @Override
    public Node<T> search(T v) {
        Node<T> aux = this.root;
        while (aux != null) {
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

    @Override
    public int height() {
        return height(this.root);
    }

    private int height(Node<T> n) {
        if (n == null)
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
    if (node.left == null) return node;
    return min(node.left);
}

    public Node<T> sucessor(Node<T> node) {
        if (node == null)
            return null;

        if (node.right != null) {
            return min(node.right);
        } else {
            Node<T> aux = node.parent;

            while (aux != null && aux.value.compareTo(node.value) < 0) {
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
}
