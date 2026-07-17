import java.util.*;
import java.io.*;
import java.util.function.Supplier;

import Benchmark.Benchmark;
import IO.DataLoader;
import DataStructures.SearchTree;
import DataStructures.AVLTree;
import DataStructures.BasicBST;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;

public class Main {

    public static void main(String[] args) throws IOException {

        File pasta = new File("src/DataSets");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        Benchmark bench = new Benchmark();

        for (int k = 10; k <= 100; k *= 10) {

            DataLoader.generateRandomNumbers("src/DataSets/random.txt", k);
            DataLoader.generateOrderedNumbers("src/DataSets/sorted.txt", k);
            DataLoader.generateReverseNumbers("src/DataSets/reverse.txt", k);

            String[] dataSets = {"random", "sorted", "reverse"};
            for (String nomeDataSet : dataSets) {

                int[] data = DataLoader.toLoad("src/DataSets/" + nomeDataSet + ".txt"); // faltava o []
                int n = data.length;

                testStructure(bench, AVLTree::new, "AVLTree", nomeDataSet, data, n);
                testStructure(bench, RedBlackTree::new, "RedBlackTree", nomeDataSet, data, n);
                testStructure(bench, SplayTree::new, "SplayTree", nomeDataSet, data, n);

                // Na BST básica, quando os elementos são inseridos de forma ordenada ou reversa, a arvoré se torna
                // uma espécie de array linear. Dessa forma, as operações de busca e inserção se tornam O(n).
                // Como esssas operações são executadas n vezes, isso se torna um O(n²).
                // Assim, na hora de testar esse tipo, talvez seja necessário reduzir o tamanho da entrada
                // 10000 operações ou algo próximo disso deve funcionar, números maiores já causariam problemas.
                testStructure(bench, BasicBST::new, "BasicBST", nomeDataSet, data, n);
            }
        }

        bench.exportarCSV("resultados.csv");
    }

    private static void testStructure(Benchmark bench, Supplier<SearchTree<Integer>> factory, String name,
                                      String dataset, int[] data, int n) {

        bench.register(name, "insert", dataset, n, () -> {
            SearchTree<Integer> tree = factory.get();
            for (int v : data) tree.add(v);
        }, 20, 5);

        // Para busca, a arvore e construida UMA vez fora da medicao;
        // so o custo das buscas em si e cronometrado.
        // Obs.: na SplayTree, buscar reestrutura a arvore, entao os
        // tempos das repeticoes tendem a cair ao longo do teste.
        // Assim, vale prestar atenção nesse detalhe e destacar na análise
        SearchTree<Integer> treeForSearch = factory.get();
        for (int v : data) treeForSearch.add(v);

        bench.register(name, "search", dataset, n, () -> {
            for (int v : data) treeForSearch.search(v);
        }, 20, 5);
    }
}
