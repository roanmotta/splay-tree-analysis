package datastructures;
// Implementação de uma Splay Tree para comparação com outras arvores binárias de pesquisa
public class SplayTree <T extends Comparable<T>> implements SearchTree<T> {

    private Node <T> root;
    private int size;

    public SplayTree () {
        this.root = null; this.size = 0;
    }

    // REALIZAR ROTAÇÃO DE UM NÓ ATÉ A RAIZ, AJUSTANDO A ARVORE, AFIM DE TORNAR O NO A NOVA RAIZ
    private void splay (Node<T> node) {

        while (node.parent != null) {

            Node <T> father = node.parent;
            Node <T> grandFather = father.parent;

            // O nó é filho direto da raiz
            // Recebe o nome de "Zig", depois decidimos se vamos usar essas terminologias no material
            if (grandFather == null) {
                // O nó é filho a esquerda do pai
                if (node == father.left) {
                    rotateRight(father);
                }
                // O nó é filho a direita do pai
                else {
                    rotateLeft(father); //*********************************************
                }
            }

            // Os próximos dois casos tão nomeados como "zig-zig"

            // Há três nós e estão todos direcionados para a esquerda
            //      a
            //     /
            //    b
            //   /
            //  c
            else if (node == father.left && father == grandFather.left) {
                rotateRight(grandFather);
                rotateRight (father);
            }

            // Há três nós e estão todos direcionados para a direita
            //  a
            //   \
            //    b
            //     \
            //      c
            else if (node == father.right && father == grandFather.right) {
                rotateLeft (grandFather);
                rotateLeft (father);
            }

            // Esses dois últimos são os "zig-zag"

            //  a
            //   \
            //     b
            //   /
            //  c

            // O nó é filho a esquerda do pai e o pai é filho a direita do avó
            else if (node == father.left && father == grandFather.right) {
                rotateRight(father);
                rotateLeft (grandFather);
            }

            //    a
            //   /
            //  b
            //   \
            //    c

            // O nó é filho a direira do pai e o pai é filho a esqueda do filho
            else if (node == father.right && father == grandFather.left) {
                rotateLeft (father);
                rotateRight (grandFather);
            }
        }

        // O nó se torna a nova raiz
        this.root = node;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    private int height(Node<T> n) {
        if (n == null)
            return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    @Override
    public int height() {
        return height(this.root);
    }

    public Node<T> maxSubTree (Node<T> node) {
        Node<T> aux = node;

        while (aux.right != null)  {
            aux = aux.right;
        }

        return aux;
    }

    public Node<T> minSubTree (Node <T> node) {
        Node<T> aux = node;

        while (aux.left != null) {
            aux = aux.left;
        }

        return aux;
    }

    public Node<T> successor (Node<T> node) {
        if (node.right != null) {
            return minSubTree (node.right);
        }
        return null;
    }

    public Node<T> predecessor (Node<T> node) {
        if (node.left != null) {
            return maxSubTree(node.left);
        }
        return null;
    }

    @Override
    public void add (T value) {

        if (isEmpty()) {
            this.root = new Node<T>(value);
            this.size++;
        }

        else {
            Node<T> aux = this.root;

            while (aux != null) {

                if(value.compareTo(aux.value) == 0) {
                    aux.count += 1;
                    size++;
                    splay(aux);
                    return;
                }

                if (value.compareTo(aux.value) < 0) {
                    if (aux.left == null) {
                        aux.left = new Node<T>(value);
                        aux.left.parent = aux;
                        size++;
                        splay (aux.left); // DIFERENCIAL DA SPLAYTREE
                        return;
                    }
                    aux = aux.left;
                }

                else {
                    if (aux.right == null) {
                        aux.right = new Node<T>(value);
                        aux.right.parent = aux;
                        size++;
                        splay(aux.right); // DIFERENCIAL DA SPLAYTREE
                        return;
                    }
                    aux = aux.right;
                }
            }
        }
    }

    @Override
    public Node<T> search (T value) {
        if (isEmpty()) {
            return null;
        }

        Node<T> aux = this.root;
        Node<T> last = null; // Último nó visitado

        while (aux != null) {
            last = aux;
            if (value.compareTo(aux.value) == 0) {
                splay (aux); // DIFERENCIAL DA SPLAYTREE
                return aux;
            }
            if (value.compareTo(aux.value) < 0) aux = aux.left;
            else if (value.compareTo(aux.value) > 0) aux = aux.right;

        }

        // Caso o nó não seja encontrado na árvore, é exutado o splay no último nó visitado
        // Não é necessariamente uma propriedade da árvore, apenas uma escolha de implementação
        // capaz de melhorar a eficiência em alguns casos
        if (last != null) splay(last);
        return null;
    }

    public void clear () {
        this.root = null;
        this.size = 0;
    }

    public Node<T> remove (T value) {

        Node<T> nodeToRemov = search (value); // Buscca o nó e o coloca na raiz na Arvoré
        if (nodeToRemov == null || nodeToRemov.value.compareTo(value) != 0) return null;
        
        // Em caso de duplicata, somente diminui a frequência, sem necessidade de remoção física. Nó removido já foi pra raiz no search.
        if (nodeToRemov.count > 1) {
            nodeToRemov.count--;
            size --;
            return nodeToRemov;
        }

        Node<T> left = this.root.left; // SubArvore a esqueda da raiz
        Node<T> right = this.root.right; // SubArvore a direita da raiz

        // Separar as subArvores da esquerda e da direita da raiz
        // (Ou seja, separar o nó que quero remover do resto da árvore)
        if (left != null) left.parent = null;
        if (right != null) right.parent = null;

        this.root.left = null;
        this.root.right = null;

        // Se não houver subArvore na esqueda, a direita é a nova raiz
        if (left == null) this.root = right;

        // Se houver subArvore na esquerda, vamos buscar o maior valor nela, colocalo na raiz e liga-lo a direita
        else {
            this.root = left;
            Node<T> maxSubLeft = maxSubTree(this.root);
            splay(maxSubLeft);

            this.root.right = right;
            if (right != null) {
                right.parent = this.root;
            }
        }
        size --;
        return nodeToRemov;
    }

    //        P
    //        |
    //        X
    //       / \
    //      A   Y
    //         / \
    //        B   C

    // UTILIZADO PARA ILUSTRAR A ROTAÇÃO E AUXILIAR NA PRODUÇÃO DO MATERIAL
    // DEPOIS EXCLUIR
    // PARA EXEMPLO, VAMOS ROTACIONAR O VERTICE x



    // VERSÃO APÓS A ROTAÇÃO
    //        P
    //        |
    //        Y
    //       / \
    //      X   C
    //     / \
    //     A  B

    private void rotateLeft (Node<T> node) {

        Node <T> newParent = node.right; // O filho a direita do nó vira o pai dele [Y vira pai de X]
        node.right = newParent.left; // O filho a esquerda do nó que subiu passa a ser o filho a direita do que desceu
        // [B virá filho a direita de X

        if (newParent.left != null) {
            newParent.left.parent = node;
        } // Se o nó que subiu tinha um filho a esquerda, este vira filho do nó que desceu [X vira o pai de B]

        newParent.parent = node.parent; // O pai do que desce vira o pai do que sobe [P virá p pai de Y]

        // Três possibilidades de novas referências pra Y [Ser filho a esquerda, a direita ou raiz]
        if (node.parent == null) {
            this.root = newParent;
        }
        else if (node == node.parent.left) {
            node.parent.left = newParent;
        }
        else if (node == node.parent.right) {
            node.parent.right = newParent;
        }

        newParent.left = node; // [X virá filho a esquerda de Y]
        node.parent = newParent; // [Y vira pai de X]
    }

    // SEGUE UMA LÓGICA MUITO PARECIDA COM A ROTAÇÃO A ESQUERDA, SÓ ADAPTAR

    private void rotateRight (Node<T> node) {

        Node <T> newParent = node.left;
        node.left = newParent.right;

        if (newParent.right != null) {
            newParent.right.parent = node;
        }

        newParent.parent = node.parent;

        if (node.parent == null) {
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

    // Classe Node que representa os vértices (nós) da árvore.
    // É capaz de receber um tipo genérico, tornando a implementeção mais adaptável
    private class Node <T> {

        T value;

        Node<T> left;
        Node<T> right;
        Node<T> parent;
        int count;

        Node(T value) {
            this.value = value;
            this.count = 1;
        }
    }

}
