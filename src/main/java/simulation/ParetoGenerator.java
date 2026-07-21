package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParetoGenerator {

    private final Random random;

    public ParetoGenerator() {
        this.random = new Random();
    }

    public List<Livro> gerarBuscasPareto(List<Livro> livros, int quantidadeBuscas) {
        if (livros == null || livros.isEmpty() || quantidadeBuscas <= 0) {
            return new ArrayList<>();
        }

        int popularCount = 10;
        int safePopularCount = Math.min(popularCount, livros.size());
        int longTailCount = livros.size() - safePopularCount;

        List<Livro> buscas = new ArrayList<>(quantidadeBuscas);

        for (int i = 0; i < quantidadeBuscas; i++) {
            if (random.nextDouble() < 0.80 || longTailCount == 0) {
                int popularIndex = random.nextInt(safePopularCount);
                buscas.add(livros.get(popularIndex));
            } else {
                int longTailIndex = safePopularCount + random.nextInt(longTailCount);
                buscas.add(livros.get(longTailIndex));
            }
        }

        return buscas;
    }
}

