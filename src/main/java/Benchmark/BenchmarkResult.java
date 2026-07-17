package benchmark;

public record BenchmarkResult(
        String estrutura,
        String operacao,
        String dataset,
        int tamanhoEntrada,
        double tempoMedioMs
) {
    public String toCsvLine() {
        return estrutura + "," + operacao + "," + dataset + "," + tamanhoEntrada + "," + tempoMedioMs;
    }
}