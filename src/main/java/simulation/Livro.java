package simulation;

import java.util.Objects;

public class Livro{
    private String title;
    private String autor;
    private String isbn;
    private String pages;
    private int yearPublication;

    public Livro(String title, String autor, String isbn, String pages, int yearPublication) {
        this.title = title;
        this.autor = autor;
        this.isbn = isbn;
        this.pages = pages;
        this.yearPublication = yearPublication;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        return Objects.equals(isbn, other.isbn);
    }
}