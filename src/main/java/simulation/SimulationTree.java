package simulation;

import java.util.List;

import DataStructures.AVLTree;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;

/**
 * Classe principal responsável por executar a simulação de buscas
 * sob a distribuição de Pareto em árvores Splay, AVL e Red-Black.
 */
public class SimulationTree {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        ParetoGenerator pareto = new ParetoGenerator();
        TreeTimer benchmark = new TreeTimer();

        String inputFilePath = "src/main/resources/livros.csv";
        String outputFilePath = "experiment_reports/pareto_simulation_results.csv";

        // Quantidade total de consultas a serem simuladas
        int totalSearches = 10000;

        List<Livro> livros = reader.readCSV(inputFilePath);
        List<Livro> targetBooks = pareto.gerarBuscasPareto(livros, totalSearches);

        SplayTree<Livro> tree = new SplayTree<>();
        AVLTree<Livro> avl = new AVLTree<>();
        RedBlackTree<Livro> rbt = new RedBlackTree<>();

        for (Livro livro : livros) {
            tree.add(livro);
            avl.add(livro);
            rbt.add(livro);
        }

        // 5. Medição de desempenho de cada árvore para a mesma carga de buscas
        double[] splayTimes = benchmark.measureSplay(tree, targetBooks);
        double[] avlTimes = benchmark.measureAvl(avl, targetBooks);
        double[] rbtTimes = benchmark.measureRbt(rbt, targetBooks);

        CSVWriter writer = new CSVWriter();
        writer.writeResults(outputFilePath, targetBooks, splayTimes, avlTimes, rbtTimes);
    }
}

