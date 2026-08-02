package simulation;

import java.util.List;
import DataStructures.AVLTree;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;

public class TreeTimer {
    /**
     * Mede o tempo de busca para cada livro em uma SplayTree.
     */
    public double[] measureSplay(SplayTree<Livro> tree, List<Livro> targetBooks) {
        double[] durations = new double[targetBooks.size()];
       
        for (int i = 0; i < targetBooks.size(); i++) {
            Livro target = targetBooks.get(i);
           
            // Registra o tempo em nanossegundos antes da busca
            double startTime = System.nanoTime();
            tree.search(target);
            double endTime = System.nanoTime();
           
            // Armazena a diferença de tempo para o livro específico
            durations[i] = endTime - startTime;
        }
        return durations;
    }

    /**
     * Mede o tempo de busca para cada livro em uma AVLTree.
     */
    public double[] measureAvl(AVLTree<Livro> tree, List<Livro> targetBooks) {
        double[] durations = new double[targetBooks.size()];
       
        for (int i = 0; i < targetBooks.size(); i++) {
            Livro target = targetBooks.get(i);
           
            double startTime = System.nanoTime();
            tree.search(target);
            double endTime = System.nanoTime();
           
            durations[i] = endTime - startTime;
        }
        return durations;
    }

    /**
     * Mede o tempo de busca para cada livro em uma RedBlackTree.
     */
    public double[] measureRbt(RedBlackTree<Livro> tree, List<Livro> targetBooks) {
        double[] durations = new double[targetBooks.size()];
   
        for (int i = 0; i < targetBooks.size(); i++) {
            Livro target = targetBooks.get(i);
           
            double startTime = System.nanoTime();
            tree.search(target);
            double endTime = System.nanoTime();
           
            durations[i] = endTime - startTime;
        }
        return durations;
    }
}
