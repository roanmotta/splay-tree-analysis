package simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    /**
     * Escreve o relatório de simulação diretamente a partir das listas/arrays de resultados.
     */
    public void writeResults(String filePath, List<Livro> targetBooks,
                                       double[] splayTimes, double[] avlTimes, double[] rbtTimes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("SearchIndex;TargetTitle;SplayTimeNS;AvlTimeNS;RBTimeNS");
            bw.newLine();

            for (int i = 0; i < targetBooks.size(); i++) {
                Livro book = targetBooks.get(i);
                String csvLine = String.format("%d;%s;%.0f;%.0f;%.0f",
                        (i + 1),
                        book.getTitle(),
                        splayTimes[i],
                        avlTimes[i],
                        rbtTimes[i]);
                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}