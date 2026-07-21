package simulation;

import java.io.IOException;
import java.util.List;

import DataStructures.AVLTree;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;
import IO.FastOutput;


public class SimulationTree {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        ParetoGenerator pareto = new ParetoGenerator();
        TreeTimer benchmark = new TreeTimer();

        String inputFilePath = "src/main/resources/livros.csv";
        String outputFilePath = "experiment_reports/pareto_simulation_results.csv";

        int totalSearches = 10000;

        List<Livro> livros = reader.readCSV(inputFilePath);
        List<Livro> targetBooks = pareto.gerarBuscasPareto(livros, totalSearches);

        SplayTree<Livro> tree = new SplayTree<>();
        AVLTree<Livro> bst = new AVLTree<>();
        RedBlackTree<Livro> rbt = new RedBlackTree<>();

        for (Livro livro : livros) {
            tree.add(livro);
            bst.add(livro);
            rbt.add(livro);
        }

        double[] splayTimes = benchmark.measureSplay(tree, targetBooks);
        double[] avlTimes = benchmark.measureAvl(bst, targetBooks);
        double[] rbtTimes = benchmark.measureRbt(rbt, targetBooks);

        try {
            FastOutput fo = new FastOutput(outputFilePath);
            fo.println("SearchIndex;TargetTitle;SplayTimeNS;AvlTimeNS;RBTimeNS");

            for (int i = 0; i < totalSearches; i++) {
                Livro book = targetBooks.get(i);
               
                String csvLine = String.format("%d;%s;%.0f;%.0f;%.0f",
                                    (i + 1),
                                    book.getTitle(),
                                    splayTimes[i],
                                    avlTimes[i],
                                    rbtTimes[i]);
                fo.println(csvLine);
            }
           
            fo.close();
        } catch (IOException e) {
            System.err.println("Error while generating CSV report: " + e.getMessage());
        }
    }
}

