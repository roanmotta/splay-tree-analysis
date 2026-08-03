package IO;

import java.io.*;

/**
 * Classe responsável pela saída de dados, otimizando a geração dos dados utilizados nos testes.
 * É importante destacar que essa etapa de gerar os dados utilizados não é contabilizada no cálculo
 * das métricas de teste das estruturas, sendo uma etapa separada.
 */

public class FastOutput {
    private final BufferedWriter writer;
    private final StringBuilder sb = new StringBuilder();

    public FastOutput(String filePath) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath), 1 << 16);
    }

    public FastOutput(OutputStream output) {
        this.writer = new BufferedWriter(new OutputStreamWriter(output), 1 << 16);
    }

    public void print(String text) {
        sb.append(text);
    }

    public void println(String text) {
        sb.append(text).append('\n');
    }

    public void flush() throws IOException {
        writer.write(sb.toString());
        writer.flush();
        sb.setLength(0);
    }

    public void close() throws IOException {
        flush();
        writer.close();
    }
}
