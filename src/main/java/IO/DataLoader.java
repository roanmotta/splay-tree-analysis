package IO;

import java.util.*;
import java.io.*;

public class DataLoader  {

    public static int[] toLoad (String path) throws IOException {
        FastInput FI = new FastInput(path);
        int n = FI.nextInt();
        int[] arr = FI.nextIntArray(n);
        FI.close();
        return arr;
    }

    public static void generateRandomNumbers (String path, int len) throws IOException {
        FastOutput FO = new FastOutput (path);
        Random generator = new Random ();
        FO.println(String.valueOf(len));

        for (int i = 0; i<len; i++) {
            int n = generator.nextInt(len*10);
            FO.print(n + " ");
        }
        FO.close();
    }

    public static void generateOrderedNumbers (String path, int len) throws IOException {
        FastOutput FO = new FastOutput (path);
        FO.println(String.valueOf(len));
        for (int i = 1; i<=len; i++) {
            FO.print(i + " ");
        }
        FO.close();
    }

    public static void generateReverseNumbers (String path, int len) throws IOException {
        FastOutput FO = new FastOutput (path);
        FO.println(String.valueOf(len));
        for (int i = len; i>0; i--) {
            FO.print(i + " ");
        }
        FO.close();
    }
}