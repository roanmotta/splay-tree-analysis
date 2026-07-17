package IO;

import java.io.*;

// Acumula em StringBuilder e so escreve no disco no flush/close.
// Evita chamadas repetidas de I/O do sistema, que sao caras.
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
