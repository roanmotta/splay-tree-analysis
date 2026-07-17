package benchmark;

import java.io.*;
import java.util.*;

public class Benchmark {
    private final Timer timer = new Timer();
    private final List<BenchmarkResult> results = new ArrayList<>();

    // descart: numero de execucoes "de graca" antes de medir,
    // para dar tempo do JIT compilar o codigo quente (evita medir bytecode interpretado)
    public double medir(Runnable operation, int rep, int descart) {
        for (int i = 0; i < descart; i++) operation.run();

        timer.reset();
        for (int i = 0; i < rep; i++) {
            timer.start();
            operation.run();
            timer.stop();
        }
        return timer.getTotal() / rep;
    }

    public void registrar(String structure, String operation, String dataset, int len,
                          Runnable action, int rep, int descart) {
        double averageTime = medir(action, rep, descart);
        results.add(new BenchmarkResult(structure, operation, dataset, len, averageTime));
        System.out.printf("%s [%s, %s, n=%d]: %.4f ms%n", structure, operation, dataset, len, averageTime);
    }

    public void exportarCSV(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("structure,operation,dataset,len,averageTime\n");
            for (BenchmarkResult result : results) {
                writer.write(result.toCsvLine());
                writer.newLine();
            }
        }
    }
}