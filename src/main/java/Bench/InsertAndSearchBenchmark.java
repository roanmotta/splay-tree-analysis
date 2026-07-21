package Bench;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import DataStructures.SearchTree;
import DataStructures.AVLTree;
import DataStructures.RedBlackTree;
import DataStructures.SplayTree;
import IO.DataLoader;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)

public class InsertAndSearchBenchmark {

    @Param({"100", "1000", "10000", "100000", "1000000"})
    public int n;

    @Param({"random", "sorted", "reverse"})
    public String datasetName;

    @Param({"AVLTree", "RedBlackTree", "SplayTree"})
    public String structureName;

    private int[] data;
    private SearchTree<Integer> prebuiltTree; // para o benchmark de search

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/DataSets/" + datasetName + "_" + n + ".txt";
        switch (datasetName) {
            case "random"  -> DataLoader.generateRandomNumbers(path, n);
            case "sorted"  -> DataLoader.generateOrderedNumbers(path, n);
            case "reverse" -> DataLoader.generateReverseNumbers(path, n);
        }
        data = DataLoader.toLoad(path);

        prebuiltTree = createStructure(structureName);
        for (int v : data) {
            prebuiltTree.add(v);
        }
    }

    private SearchTree<Integer> createStructure(String name) {
        return switch (name) {
            case "AVLTree" -> new AVLTree<>();
            case "RedBlackTree" -> new RedBlackTree<>();
            case "SplayTree" -> new SplayTree<>();
            default -> throw new IllegalArgumentException(name);
        };
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void insert(Blackhole bh) {
        SearchTree<Integer> tree = createStructure(structureName);
        for (int v : data) {
            tree.add(v);
        }
        bh.consume(tree);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void search(Blackhole bh) {
        for (int v : data) {
            bh.consume(prebuiltTree.search(v));
        }
    }
}