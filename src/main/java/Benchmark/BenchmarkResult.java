package Benchmark;

public record BenchmarkResult(
        String structure,
        String operation,
        String dataset,
        int len,
        double averageTime
) {
    public String toCsvLine() {
        return structure + "," + operation + "," + dataset + "," + len + "," + averageTime;
    }
}
