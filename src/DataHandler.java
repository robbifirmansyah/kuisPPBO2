import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataHandler {
    private static final String FILE_PATH = "library_data.txt";

    public static void saveBooks(List<Book> books) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        }
    }

    public static List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    Book book = new Book(data[0], data[1], data[2], data[3], data[4], new Date(data[5]), new Date(data[6]));
                    books.add(book);
                }
            }
        }
        return books;
    }
}