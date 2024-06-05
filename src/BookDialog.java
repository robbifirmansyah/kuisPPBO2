import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookDialog extends JDialog {
    private JTextField idField, titleField, authorField, genreField, publisherField, publishDateField;
    private boolean confirmed;
    private Book book;

    public BookDialog(Book book, String title) {
        this.book = book;

        setTitle(title);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false);
        idField.setText(book != null ? book.getId() : ""); // Auto-generated ID

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(book != null ? book.getTitle() : "");

        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField(book != null ? book.getAuthor() : "");

        JLabel genreLabel = new JLabel("Genre:");
        genreField = new JTextField(book != null ? book.getGenre() : "");

        JLabel publisherLabel = new JLabel("Publisher:");
        publisherField = new JTextField(book != null ? book.getPublisher() : "");

        JLabel publishDateLabel = new JLabel("Publish Date (dd-MM-yyyy):");
        publishDateField = new JTextField(book != null ? new SimpleDateFormat("dd-MM-yyyy").format(book.getPublishDate()) : "");

        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateDate(publishDateField.getText())) {
                    confirmed = true;
                    updateBookDetails();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(BookDialog.this, "Invalid date format. Please use dd-MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(genreLabel);
        panel.add(genreField);
        panel.add(publisherLabel);
        panel.add(publisherField);
        panel.add(publishDateLabel);
        panel.add(publishDateField);
        panel.add(confirmButton);
        panel.add(cancelButton);

        add(panel);
        setModal(true);
        setVisible(true);
    }

    private void updateBookDetails() {
        if (book != null) {
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setGenre(genreField.getText());
            book.setPublisher(publisherField.getText());
            try {
                book.setPublishDate(new SimpleDateFormat("dd-MM-yyyy").parse(publishDateField.getText()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Book getBook() {
        if (book == null) {
            try {
                return new Book(
                        idField.getText(), // Auto-generated ID
                        titleField.getText(),
                        authorField.getText(),
                        genreField.getText(),
                        publisherField.getText(),
                        new SimpleDateFormat("dd-MM-yyyy").parse(publishDateField.getText()),
                        new Date() // Current date/time
                );
            } catch (ParseException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return book;
    }

    private boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
