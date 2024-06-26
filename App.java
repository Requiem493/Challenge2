import git.tools.client.GitSubprocessClient;
import github.tools.client.GitHubApiClient;
import github.tools.client.RequestParams;
import github.tools.responseObjects.CreateRepoResponse;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class App {
    private static String projectPath;
    private static String projectName;
    private static GitSubprocessClient gitSubprocessClient;
    private static GitHubApiClient gitHubApiClient;
    private static String userName;
    private static String repoUrl;

    public static void main(String[] args) {
        // create JFrame
        JFrame frame = new JFrame("QBay");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // make it so when JFrame is closed via the x button, the entire program stops
        frame.setLocationRelativeTo(null); // make JFrame open center screen
        frame.setResizable(false);

        //create JLabels
        JLabel pathLabel = new JLabel("Path: ");
        JLabel tokenLabel = new JLabel("Github Token: ");

        //create JTextFields
        JTextField pathField = new JTextField();
        JTextField tokenField = new JTextField();
        pathField.setPreferredSize(new Dimension(100, 50));    
        tokenField.setPreferredSize(new Dimension(100, 50)); 

        // create main panel (canvas)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // no layout so you can put things wherever you want
        mainPanel.setBackground(Color.white);

        //added logo
        ImageIcon QUBAY = new ImageIcon("QUBAY2.png");
        JLabel imageLabel = new JLabel(QUBAY);
        imageLabel.setSize(50, 50);
        mainPanel.add(imageLabel);
 
        //creates button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setVisible(true);
        mainPanel.add(buttonPanel);
        buttonPanel.setSize(800, 800);

        //Labels panel
        JPanel labelPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setVisible(true);
        mainPanel.add(pathLabel);
        mainPanel.add(pathField);
        mainPanel.add(tokenLabel);
        mainPanel.add(tokenField);
        mainPanel.add(buttonPanel);
        mainPanel.setSize(600,600);

        //creates buttons 
        JButton convertButton = new JButton("Convert to Git Repo");
        convertButton.addActionListener(e -> {
            initializeRepo();
            createGitignore();
            try {
                createReadMe();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            createInitCommit();
            createGitHubRepo("Project Description", false);
            setOrigin();
            pushCommit();
            displayURL();
        });
        buttonPanel.add(convertButton);
        frame.setContentPane(mainPanel);

        frame.setVisible(true);
    }

    private static void initializeRepo() {
        gitSubprocessClient = new GitSubprocessClient(projectPath);
        gitSubprocessClient.gitInit();
    }

    private static void createGitignore() {
        File sourceFile = new File(".gitignore");
        File destinationFile = new File(String.format("%s/.gitignore", projectPath));
        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static void createGitHubRepo(String description, boolean isPrivate) {
        try {
            gitHubApiClient = new GitHubApiClient(userName, getAPIKey());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RequestParams requestParams = new RequestParams();
        requestParams.addParam("name", projectName);
        requestParams.addParam("description", description);
        requestParams.addParam("private", isPrivate);

        CreateRepoResponse createRepoResponse = gitHubApiClient.createRepo(requestParams);
        repoUrl = createRepoResponse.getUrl();
    }

    private static void setOrigin() {
        gitSubprocessClient.gitRemoteAdd("origin", String.format("%s.git", repoUrl));
    }

    private static void pushCommit() {
        gitSubprocessClient.gitPush("master");
    }

    private static void displayURL() {
        JOptionPane.showMessageDialog(null, "GitHub Repository URL: " + repoUrl);
    }

    private static String getAPIKey() throws IOException {
        //Gets key from a file named "key.txt"
        //File has been gitignored
        Scanner keyReader = new Scanner(new File("key.txt"));
        return keyReader.nextLine();
    }
}
