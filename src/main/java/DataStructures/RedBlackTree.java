package DataStructures;
// Implementação de uma árvore vermelha e preta para fins comparativos com a Splay Tree.
public class RedBlackTree<T extends Comparable<T>> implements SearchTree<T> {

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

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(T v) {

        Node<T> newNode = new Node<>(v);
        Node<T> parent = this.NIL;
        Node<T> aux = this.root;

        while (aux != NIL) {
            parent = aux;

            int comp = v.compareTo(aux.value);

            if (comp == 0) {
                aux.count ++;
                size++;
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
        else if (v.compareTo(parent.value) < 0) parent.left = newNode;
        else parent.right = newNode;

        newNode.parent = parent;
        newNode.left = newNode.right = NIL;
        size++;

        insertFixup(newNode);
    }

    private void insertFixup(Node<T> node) {
        while (node.parent.color == RED) {

            // Se o pai for filho a esquerda do avô
            if (node.parent == node.parent.parent.left) {
                Node<T> uncleRight = node.parent.parent.right;

                if (uncleRight.color == RED) {
                    // Caso 1: tio vermelho -> recolorir e subir
                    node.parent.color = BLACK;
                    uncleRight.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        // Caso 2: triângulo -> rotação para virar linha
                        node = node.parent;
                        rotateLeft(node);
                    }
                    // Caso 3: linha -> rotação + recoloração
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                Node<T> uncleLeft = node.parent.parent.left;

                if (uncleLeft.color == RED) {
                    node.parent.color = BLACK;
                    uncleLeft.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }

        this.root.color = BLACK;
    }


    @Override
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
        return NIL;
    }

    @Override
    public int height() {
        return height(this.root);
    }

    private int height(Node<T> n) {
        if (n == NIL)
            return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    public boolean equals(RedBlackTree<T> outra) {
        if (outra == null)
            return false;
        return equals(this.root, outra.root);
    }

    private boolean equals(Node<T> a, Node<T> b) {
        if (a == NIL && b == NIL)
            return true;
        if (a == NIL || b == NIL || a.value.compareTo(b.value) != 0)
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

            while (aux != NIL && node == aux.right) {
                node = aux;
                aux = aux.parent;
            }

            return aux;
        }
    }

    public boolean remove(T value) {
        Node<T> toRemove = search(value);
        if (toRemove != NIL) {
            if (toRemove.count > 1) {
                toRemove.count --;
            }
            else {
                remove(toRemove);
            }
            this.size--;
            return true;
        }
        return false;
    }

    private void transplant(Node<T> target, Node<T> substitute) {
        if (target.parent == NIL) {
            this.root = substitute;
        } else if (target == target.parent.left) {
            target.parent.left = substitute;
        } else {
            target.parent.right = substitute;
        }
        substitute.parent = target.parent;
    }

    private void remove(Node<T> toRemove) {
        Node<T> removed = toRemove;
        boolean removedOriginalColor = removed.color;
        Node<T> moved;

        if (toRemove.left == NIL) {
            moved = toRemove.right;
            transplant(toRemove, toRemove.right);
        } else if (toRemove.right == NIL) {
            moved = toRemove.left;
            transplant(toRemove, toRemove.left);
        } else {
            removed = min(toRemove.right); // sucessor de toRemove
            removedOriginalColor = removed.color;
            moved = removed.right;

            if (removed.parent == toRemove) {
                moved.parent = removed;
            } else {
                transplant(removed, removed.right);
                removed.right = toRemove.right;
                removed.right.parent = removed;
            }

            transplant(toRemove, removed);
            removed.left = toRemove.left;
            removed.left.parent = removed;
            removed.color = toRemove.color;
        }

        if (removedOriginalColor == BLACK) {
            deleteFixup(moved);
        }
    }

    private void deleteFixup(Node<T> moved) {
        while (moved != this.root && moved.color == BLACK) {

            if (moved == moved.parent.left) {
                Node<T> sibling = moved.parent.right;

                if (sibling.color == RED) {
                    // Caso 1: irmão vermelho -> vira caso 2/3/4 preto
                    sibling.color = BLACK;
                    moved.parent.color = RED;
                    rotateLeft(moved.parent);
                    sibling = moved.parent.right;
                }

                if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
                    // Caso 2: ambos os filhos do irmão são pretos
                    sibling.color = RED;
                    moved = moved.parent;
                } else {
                    if (sibling.right.color == BLACK) {
                        // Caso 3: filho próximo vermelho, distante preto
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = moved.parent.right;
                    }
                    // Caso 4: filho distante vermelho
                    sibling.color = moved.parent.color;
                    moved.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    rotateLeft(moved.parent);
                    moved = this.root;
                }
            } else {
                Node<T> sibling = moved.parent.left;

                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    moved.parent.color = RED;
                    rotateRight(moved.parent);
                    sibling = moved.parent.left;
                }

                if (sibling.right.color == BLACK && sibling.left.color == BLACK) {
                    sibling.color = RED;
                    moved = moved.parent;
                } else {
                    if (sibling.left.color == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        rotateLeft(sibling);
                        sibling = moved.parent.left;
                    }
                    sibling.color = moved.parent.color;
                    moved.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rotateRight(moved.parent);
                    moved = this.root;
                }
            }
        }

        moved.color = BLACK;
    }

    private void rotateLeft (Node<T> node) {

        Node <T> newParent = node.right;
        node.right = newParent.left;

        if (newParent.left != NIL) {
            newParent.left.parent = node;
        }

        newParent.parent = node.parent;

        if (node.parent == NIL) {
            this.root = newParent;
        }
        else if (node == node.parent.left) {
            node.parent.left = newParent;
        }
        else if (node == node.parent.right) {
            node.parent.right = newParent;
        }

        newParent.left = node;
        node.parent = newParent;
    }

    private void rotateRight (Node<T> node) {

        Node <T> newParent = node.left;
        node.left = newParent.right;

        if (newParent.right != NIL) {
            newParent.right.parent = node;
        }

        newParent.parent = node.parent;

        if (node.parent == NIL) {
            this.root = newParent;
        }
        else if (node == node.parent.left) {
            node.parent.left = newParent;
        }
        else if (node == node.parent.right) {
            node.parent.right = newParent;
        }

        newParent.right = node;
        node.parent = newParent;
    }
}
