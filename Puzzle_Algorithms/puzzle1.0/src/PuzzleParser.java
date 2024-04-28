import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class PuzzleParser {
    private final ArrayList<String> lines = new ArrayList<>();
    private boolean hasRead;
    protected int[] startPoint;
    protected int[] endPoint;
    protected int[][] maze;
    private boolean hasLoaded;
    private File inputFile;

    public Boolean hasFileRead() {
        return this.hasRead;
    }

    public int[] getStartPoint() {
        if (hasLoaded()) {
            return this.startPoint;
        }
        return null;
    }

    public int[] getEndPoint() {
        if (hasLoaded()) {
            return this.endPoint;
        }
        return null;
    }

    public int[][] getMaze() {
        if (hasLoaded()) {
            return this.maze;
        }
        return null;
    }

    public void loadLines() throws IOException {
        if (this.hasRead) {
            lines.addAll(Files.readAllLines(inputFile.toPath(), Charset.defaultCharset()));
            this.hasLoaded = true;
        }
    }
    public void selectFile() throws Exception {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select Example File");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setDirectory(System.getProperty("user.dir"));
        fileDialog.setFile("*.txt");
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        String fileType = file.substring(Math.max(0, file.length() - 4));

        if (!fileType.equals(".txt"))
            throw new Exception("File extension of the input file should be .txt");

        File inputFile = fileDialog.getFiles()[0];
        if (inputFile.length() == 0) throw new Exception("No file selected");

        this.inputFile = fileDialog.getFiles()[0];
        this.hasRead = true;
    }
    public String getFileName() {
        if (hasLoaded) {
            return inputFile.getName();
        }
        return null;
    }

    public ArrayList<String> getLines() {
        if (this.hasRead) {
            return this.lines;
        }
        return null;
    }

    public File getFile() {
        if (this.hasRead) {
            return inputFile;
        }
        return null;
    }

    public boolean loadValues() {
        ArrayList<String> lines = this.getLines();
        int numRows = lines.size();
        int maxLength = 0;

        // Find the maximum length of a line
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        // Initialize maze with dimensions based on input puzzle size
        this.maze = new int[numRows][maxLength];
        this.startPoint= null;
        this.endPoint = null;

        for (int i = 0; i < numRows; i++) {
            String line = lines.get(i);
            int length = line.length();
            for (int j = 0; j < length; j++) {
                char c = line.charAt(j);
                switch (c) {
                    case '.':
                        // Free path
                        this.maze[i][j] = 0;
                        break;
                    case 'S':
                        // Start point
                        this.maze[i][j] = 0;
                        this.startPoint = new int[]{i, j};
                        break;
                    case 'F':
                        // End point
                        this.maze[i][j] = 0;
                        this.endPoint = new int[]{i, j};
                        break;
                    case '0':
                        // Wall
                        this.maze[i][j] = 1;
                        break;
                    default:
                        // Treat any other character as a wall
                        this.maze[i][j] = 1;
                        break;
                }
            }
        }
        return true;
    }

    public void readFile(String path) throws FileNotFoundException {
        File inputFile = new File(path);

        if (inputFile.length() == 0) {
            throw new FileNotFoundException("File " + path + " does not exist");
        }

        this.inputFile = inputFile;
        this.hasRead = true;
    }

    public Boolean hasLoaded() {
        if (this.hasFileRead()) {
            return this.hasLoaded;
        }
        return null;
    }
}
