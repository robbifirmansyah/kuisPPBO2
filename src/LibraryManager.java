import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryManager extends JPanel {
    private List<Book> books;
    private JTable table;
    private DefaultTableModel tableModel;
    private static final String FILE_PATH = "books.txt";
    private int nextId = 1;

    public LibraryManager() {
        setLayout(new BorderLayout());
        books = new ArrayList<>();
        loadBooks();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Genre", "Publisher", "Publish Date", "Added Date"}, 0);
        table = new JTable(tableModel);
        updateTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton editButton = new JButton("Edit Book");
        JButton deleteButton = new JButton("Delete Book");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDialog dialog = new BookDialog(null, "Add Book");
                if (dialog.isConfirmed()) {
                    Book newBook = dialog.getBook();
                    newBook.setId(String.valueOf(nextId++));
                    books.add(newBook);
                    updateTable();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Book selectedBook = books.get(selectedRow);
                    BookDialog dialog = new BookDialog(selectedBook, "Edit Book");
                    if (dialog.isConfirmed()) {
                        updateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(LibraryManager.this, "Please select a book to edit", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(LibraryManager.this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        books.remove(selectedRow);
                        updateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(LibraryManager.this, "Please select a book to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        SimpleDateFormat publishDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat addedDateFormat = new SimpleDateFormat("dd-MM-yyyy|HH-mm-ss");
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getPublisher(),
                    publishDateFormat.format(book.getPublishDate()),
                    addedDateFormat.format(book.getAddedDate())
            });
        }
    }

    public void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 7) {
                    books.add(new Book(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            new SimpleDateFormat("dd-MM-yyyy").parse(parts[5]),
                            new SimpleDateFormat("dd-MM-yyyy|HH-mm-ss").parse(parts[6])
                    ));
                    nextId = Math.max(nextId, Integer.parseInt(parts[0]) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
