package Benchmark;

import java.io.*;
import java.util.*;

public class Benchmark {
    private final TimerBench timer = new TimerBench();
    private final List<BenchmarkResult> results = new ArrayList<>();

    // warmup: numero de execucoes "de graca" antes de medir,
    // para dar tempo do JIT compilar o codigo quente (evita medir bytecode interpretado)
    public double measure(Runnable operation, int rep, int warmup) {
        for (int i = 0; i < warmup; i++) operation.run();

        timer.reset();
        for (int i = 0; i < rep; i++) {
            timer.start();
            operation.run();
            timer.stop();
        }
        return timer.getTotal() / rep;
    }

    public void register(String structure, String operation, String dataset, int len,
                         Runnable action, int rep, int warmup) {
        double averageTime = measure(action, rep, warmup);
        results.add(new BenchmarkResult(structure, operation, dataset, len, averageTime));
        System.out.printf("%s [%s, %s, n=%d]: %.4f ms%n", structure, operation, dataset, len, averageTime);
    }

    public void exportarCSV(String filePath) throws IOException {

        File file = new File(filePath);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("structure,operation,dataset,len,averageTime\n");
            for (BenchmarkResult result : results) {
                writer.write(result.toCsvLine());
                writer.newLine();
            }
            writer.flush();
        }
    }
}