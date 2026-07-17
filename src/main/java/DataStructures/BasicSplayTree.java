package DataStructures;
// Classe utilizada apenas para a produção do material didático.

public class BasicSplayTree {

    private Node root;
    private int size;

    public BasicSplayTree() {
        this.root = null;
        this.size = 0;
    }

    private void splay(Node node) {
        while (node.parent != null) {
            Node father = node.parent;
            Node grandFather = father.parent;

            if (grandFather == null) {
                if (node == father.left) {
                    rotateRight(father);
                } else {
                    rotateLeft(father);
                }
            } else if (node == father.left && father == grandFather.left) {
                rotateRight(grandFather);
                rotateRight(father);
            } else if (node == father.right && father == grandFather.right) {
                rotateLeft(grandFather);
                rotateLeft(father);
            } else if (node == father.left && father == grandFather.right) {
                rotateRight(father);
                rotateLeft(grandFather);
            } else if (node == father.right && father == grandFather.left) {
                rotateLeft(father);
                rotateRight(grandFather);
            }
        }

        this.root = node;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public Node maxSubTree(Node node) {
        Node aux = node;
        while (aux.right != null) {
            aux = aux.right;
        }
        return aux;
    }

    public Node minSubTree(Node node) {
        Node aux = node;
        while (aux.left != null) {
            aux = aux.left;
        }
        return aux;
    }

    public void add(int value) {
        if (isEmpty()) {
            this.root = new Node(value);
            size++;
        } else {
            Node aux = this.root;

            while (aux != null) {
                if (value < aux.value) {
                    if (aux.left == null) {
                        aux.left = new Node(value);
                        aux.left.parent = aux;
                        size++;
                        splay(aux.left);
                        return;
                    }
                    aux = aux.left;
                } else {
                    if (aux.right == null) {
                        aux.right = new Node(value);
                        aux.right.parent = aux;
                        size++;
                        splay(aux.right);
                        return;
                    }
                    aux = aux.right;
                }
            }
        }
    }

    public Node search(int value) {
        if (isEmpty()) {
            return null;
        }

        Node aux = this.root;
        Node last = null;

        while (aux != null) {
            last = aux;

            if (value == aux.value) {
                splay(aux);
                return aux;
            }
            
            if (value < aux.value) {
                aux = aux.left;
            } else {
                aux = aux.right;
            }
        }

        if (last != null) {
            splay(last);
        }
        return null;
    }

    public Node remove(int value) {
        Node nodeToRemove = search(value);
        
        if (nodeToRemove == null || nodeToRemove.value != value) {
            return null;
        }

        Node left = this.root.left;
        Node right = this.root.right;

        if (left != null) left.parent = null;
        if (right != null) right.parent = null;

        this.root.left = null;
        this.root.right = null;

        if (left == null) {
            this.root = right;
        } else {
            this.root = left;
            Node maxSubLeft = maxSubTree(this.root);
            splay(maxSubLeft);

            this.root.right = right;
            if (right != null) {
                right.parent = this.root;
            }
        }
        
        size--;
        return nodeToRemove;
    }

    private void rotateLeft(Node node) {
        Node newParent = node.right;
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
    }

    private void rotateRight(Node node) {
        Node newParent = node.left;
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
    }

    private class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
}
