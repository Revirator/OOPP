package nl.tudelft.oopp.demo.data;

public class Quote {

    // these match the properties in Quote entity on server
    // but we only include what we need on client
    // No setter necessary, since we don't update on client.

    private String quote;
    private String author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Quote{"
                + "quote='" + quote + '\''
                + ", author='" + author + '\''
                + '}';
    }
}
