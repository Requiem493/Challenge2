

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Color;

public class App {
    public static void main(String[] args) {
        // create JFrame
        JFrame frame = new JFrame("QBay");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // make it so when JFrame is closed via the x button, the entire program stops
        frame.setLocationRelativeTo(null); // make JFrame open center screen
        frame.setResizable(false);

         // create main panel (canvas)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // no layout so you can put things wherever you want
        mainPanel.setBackground(Color.blue);
    }

    private static void initializeRepo() {

    }

    private static void createGitignore() {

    }

    private static void createReadMe() {

    }

    private static void createInitCommit() {

    }

    private static void createGitHubRepo() {

    }

    private static void setOrigin() {

    }

    private static void pushCommit() {

    }

    private static void displayURL() {

    }

    private static void getAPIKey() {
        //Gets key from a file named "key.txt"
        //File has been gitignored
    }
}
