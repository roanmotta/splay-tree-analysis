// Implementação de uma AVL Tree para fins comparativos com a Splay Tree.
public class AVLTree<T extends Comparable<T>> implements SearchTree <T>{

    private Node<T> root;
    private int size;

    public AVLTree() {
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


    private int height(Node<T> node) {
        if (node == null) return null;
        else return node.height;
    }

    // Recalcula a altura de um nó a partir da altura dos filhos.
    // Precisa ser chamada sempre que a subárvore de um nó muda
    // (inserção, remoção ou rotação).
    private void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node<T> node) {
        return height(node.left) - height(node.right);
    }

    private void rotateLeft(Node<T> node) {

        Node<T> newParent = node.right;
        node.right = newParent.left;

        if (newParent.left != null) {
            newParent.left.parent = node;
        }

        newParent.parent = node.parent;

        if (node.parent == null) {
            this.root = newParent;
        } else if (node == node.parent.left) {
            node.parent.left = newParent;
        } else {
            node.parent.right = newParent;
        }

        newParent.left = node;
        node.parent = newParent;

        // Atualiza a altura de baixo para cima (node desceu, então é o primeiro a
        // ser recalculado; newParent depende da altura de node)
        updateHeight(node);
        updateHeight(newParent);
    }

    private void rotateRight(Node<T> node) {

        Node<T> newParent = node.left;
        node.left = newParent.right;

        if (newParent.right != null) {
            newParent.right.parent = node;
        }

        newParent.parent = node.parent;

        if (node.parent == null) {
            this.root = newParent;
        } else if (node == node.parent.left) {
            node.parent.left = newParent;
        } else {
            node.parent.right = newParent;
        }

        newParent.right = node;
        node.parent = newParent;

        updateHeight(node);
        updateHeight(newParent);
    }

    // Verifica o fator de balanceamento de um nó e aplica a rotação adequada
    private void rebalance(Node<T> node) {
        updateHeight(node);
        int balance = balanceFactor(node);

        if (balance > 1) {
            // Pesado à esquerda
            if (balanceFactor(node.left) < 0) {
                // Caso LR: filho esquerdo é pesado à direita -> alinha antes de rotacionar
                rotateLeft(node.left);
            }
            rotateRight(node); // Caso LL
        } else if (balance < -1) {
            // Pesado à direita
            if (balanceFactor(node.right) > 0) {
                // Caso RL
                rotateRight(node.right);
            }
            rotateLeft(node); // Caso RR
        }
    }

    // Sobe do nó informado até a raiz, atualizando alturas e rebalanceando sempre
    // que necessário. É chamada após add() e remove().
    private void rebalanceUp(Node<T> node) {
        while (node != null) {
            rebalance(node);
            node = node.parent;
        }
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
                    rebalanceUp(aux); // NOVO
                    return;
                }
                aux = aux.left;
            } else if (comp > 0) {
                if (aux.right == null) {
                    Node<T> newNode = new Node<>(v);
                    aux.right = newNode;
                    newNode.parent = aux;
                    this.size++;
                    rebalanceUp(aux); // NOVO
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

    public boolean equals(AVLTree<T> outra) {
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
        if (toRemove.isLeaf()) {
            Node<T> parent = toRemove.parent;
            if (toRemove == this.root) {
                this.root = null;
            } else {
                if (toRemove == toRemove.parent.left) {
                    toRemove.parent.left = null;
                } else {
                    toRemove.parent.right = null;
                }
            }
            rebalanceUp(parent);
        }

        else if (toRemove.hasOnlyLeftChild()) {
            Node<T> parent = toRemove.parent;
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

            if (parent != null) rebalanceUp(parent);
            else rebalanceUp(toRemove.left);
        }

        else if (toRemove.hasOnlyRightChild()) {
            Node<T> parent = toRemove.parent;
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
            if (parent != null) rebalanceUp(parent);
            else rebalanceUp(toRemove.right);
        }

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
        int height;

        Node(T v) {
            this.value = v;
            this.count = 1;
            this.height = 0; // nó novo (folha) sempre nasce com altura 0
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