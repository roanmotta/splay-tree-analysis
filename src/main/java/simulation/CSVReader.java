package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader{
    private static final int COLUNA_TITLE = 0;
    private static final int COLUNA_AUTOR = 1;
    private static final int COLUNA_ISBN = 2;
    private static final int COLUNA_PAGES = 3;
    private static final int COLUNA_YEAR = 4;
   
    public List<Livro> readCSV(String filePath) {
        List<Livro> livros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                Livro livro = processCSV(fields);
                livros.add(livro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return livros;
    }

    private Livro processCSV(String[] field) {
        // Implementation for processing the CSV file
        String title = field[COLUNA_TITLE].trim();
        String autor = field[COLUNA_AUTOR].trim();
        String isbn = field[COLUNA_ISBN].trim();
        String pages = field[COLUNA_PAGES].trim();
        int year = Integer.parseInt(field[COLUNA_YEAR].trim());


        // Do something with the extracted data, e.g., create a Livro object
        Livro livro = new Livro(title, autor, isbn, pages, year);
        return livro;
    }
}