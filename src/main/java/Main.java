import java.util.*;
import java.io.*;

public class Main {

    public static void main (String[] args) throws IOException {
        // Interessante incrementar uma lógica do tamanho das entradas, uma espécie de for que
        // a cada passagem multiplica o tamanho da entrada por 10, até 1e6 ou algo próximo

        DataLoader.generateRandomNumbers("src/DataSets/random.txt", 1000); // Valores aleatórios
        DataLoader.generateOrderedNumbers("src/DataSets/sorted.txt", 1000); // Valores ordenados
        DataLoader.generateReverseNumbers("src/DataSets/reverse.txt", 1000); // Valores em ordem reversa

        String[] dataSets = {"random", "sorted", "reverse"};
        for (String nomeDataSet: dataSets) {

            int data = DataLoader.toLoad("src/DataSets/" + nomeDataSet + ".txt");
            int n = data.lenght;


        }

    }

    private static void testStructure (){

    }

}