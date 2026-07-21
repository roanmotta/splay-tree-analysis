package Benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.openjdk.jmh.annotations.*;
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
public class HotSearchBenchmark {

    @Param({"100", "1000", "10000", "100000", "1000000"})
    public int n;

    @Param({"random", "sorted", "reverse"})
    public String datasetName;

    @Param({"AVLTree", "RedBlackTree", "SplayTree"})
    public String structureName;

    private SearchTree<Integer> tree;
    private int[] hotKeys;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String path = "src/DataSets/" + datasetName + "_" + n + ".txt";

        switch (datasetName) {
            case "random"  -> DataLoader.generateRandomNumbers(path, n);
            case "sorted"  -> DataLoader.generateOrderedNumbers(path, n);
            case "reverse" -> DataLoader.generateReverseNumbers(path, n);
        }

        int[] data = DataLoader.toLoad(path);

        tree = criarEstrutura(structureName);
        for (int v : data) {
            tree.add(v);
        }

        Random random = new Random(42);
        hotKeys = new int[10];
        for (int i = 0; i < hotKeys.length; i++) {
            hotKeys[i] = data[random.nextInt(n)];
        }
    }

    private SearchTree<Integer> criarEstrutura(String name) {
        return switch (name) {
            case "AVLTree" -> new AVLTree<>();
            case "RedBlackTree" -> new RedBlackTree<>();
            case "SplayTree" -> new SplayTree<>();
            default -> throw new IllegalArgumentException("Estrutura desconhecida: " + name);
        };
    }

    @Benchmark
    public void searchHot(Blackhole bh) {
        for (int i = 0; i < 100000; i++) {
            bh.consume(tree.search(hotKeys[i % hotKeys.length]));
        }
    }
}