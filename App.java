import git.tools.client.GitSubprocessClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static String projectPath;
    private static String projectName;
    private static GitSubprocessClient gitSubprocessClient;
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
        frame.setContentPane(mainPanel);

        ImageIcon QUBAY = new ImageIcon("QUBAY.png");
        JLabel imageLabel = new JLabel(QUBAY);
        imageLabel.setSize(1024, 1024);
        mainPanel.add(imageLabel);


        frame.setVisible(true);
        try {
            createReadMe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void initializeRepo() {
        gitSubprocessClient.gitInit();
    }

    private static void createGitignore() {
    }

    private static void createReadMe() throws IOException {
        File readMe = new File(String.format("%s/README.md", projectPath));
        readMe.createNewFile();
        FileWriter writer = new FileWriter(readMe);
        writer.write(String.format("# %s", projectName));
        writer.close();
    }

    private static void createInitCommit() {
        gitSubprocessClient.gitAddAll();
        gitSubprocessClient.gitCommit("Initial commit");
    }

    private static void createGitHubRepo() {

    }

    private static void setOrigin() {

    }

    private static void pushCommit() {

    }

    private static void displayURL() {

    }

    private static String getAPIKey() throws IOException {
        //Gets key from a file named "key.txt"
        //File has been gitignored
        Scanner keyReader = new Scanner(new File("key.txt"));
        return keyReader.nextLine();
    }
}
