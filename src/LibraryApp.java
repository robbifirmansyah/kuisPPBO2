import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LibraryApp extends JFrame {
    private LibraryManager libraryManager;

    public LibraryApp() {
        setTitle("Library App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        libraryManager = new LibraryManager();
        add(libraryManager);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                libraryManager.saveBooks();
                super.windowClosing(e);
            }
        });

        setVisible(true);
    }
}
