package IO;

import java.io.*;
import java.util.StringTokenizer;

public class FastInput {
    private final BufferedReader br;
    private StringTokenizer st;

    public FastInput(String filePath) throws IOException {
        this.br = new BufferedReader(new FileReader(filePath), 1 << 16);
    }

    public FastInput(InputStream input) {
        this.br = new BufferedReader(new InputStreamReader(input), 1 << 16);
    }

    private String nextTokenIO() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null) return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextTokenIO());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextTokenIO());
    }

    public String nextString() throws IOException {
        return nextTokenIO();
    }

    public int[] nextIntArray(int len) throws IOException {
        int[] vetor = new int[len];
        for (int i = 0; i < len; i++) vetor[i] = nextInt();
        return vetor;
    }

    public void close() throws IOException {
        br.close();
    }
}
