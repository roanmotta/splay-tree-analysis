package simulation;

import java.util.List;
import DataStructures.AVLTree;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;

public class TreeTimer {
    public double[] measureSplay(SplayTree<Livro> tree, List<Livro> targetBooks) {
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
