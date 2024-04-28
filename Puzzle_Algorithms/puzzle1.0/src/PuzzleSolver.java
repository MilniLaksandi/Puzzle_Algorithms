import java.io.File;
import java.util.Scanner;

public class PuzzleSolver {
    private final static Scanner scanner = new Scanner(System.in);
    private static PuzzleParser puzzleParser;
    private static boolean skipLoad;
    private static File selectedFile;
    private static String selectedFileName;

    public static void main(String[] args) throws Exception {
        System.out.println("Puzzle Solver");

        while (true) {
            skipLoad = false;

            System.out.println("\nMain Menu:");
            System.out.println("N to Load new Puzzle");
            System.out.println("Q to Quit the Puzzle");

            System.out.print("\nEnter Your Choice: ");
            String choice = scanner.nextLine();

            switch (choice.toUpperCase()) {
                case "Q":
                    System.out.println("Exiting the Puzzle. Goodbye!");
                    System.exit(0);
                    break;
                case "N":
                    loadNewInput();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void loadNewInput() {
        while (true) {
            if (skipLoad)
                return;

            System.out.println("\nInput Selection:");
            System.out.println("'A' to select a graph using file explorer");
            System.out.println("'B' to enter the name of the graph file manually");
            System.out.println("'C' to go back to main menu");

            System.out.print("\nEnter Your Choice: ");
            String choice = scanner.nextLine();
            boolean loadError = false;

            switch (choice) {
                case "A":
                    System.out.println("Choose the text file");
                    System.out.println("Please check the taskbar for a new icon");
                    try {
                        PuzzleParser parser = new PuzzleParser();
                        parser.selectFile();
                        parser.loadLines();
                        parser.loadValues();
                        if (!parser.hasFileRead()) {
                            throw new Exception("File not loaded");
                        }
                        selectedFileName = parser.getFileName();
                        selectedFile = parser.getFile();
                        puzzleParser = parser;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("\nError reading input file, please try again.\n");
                        loadError = true;
                    }
                    if (!loadError) {
                        skipLoad = true;
                        calculateShortestDistance();
                    } else {
                        continue;
                    }
                    break;
                case "B":
                    System.out.println("Enter the name of the Example text file with '.txt' extension:");
                    try {
                        String userInputFileName;
                        do {
                            System.out.print("Text File Name:  ");
                            userInputFileName = scanner.nextLine();
                        } while ((!userInputFileName.endsWith(".txt")));

                        PuzzleParser parser = new PuzzleParser();
                        parser.readFile("src/Example/" + userInputFileName);
                        parser.loadLines();
                        parser.loadValues();
                        if (!parser.hasFileRead()) {
                            throw new Exception("File not loaded");
                        }
                        selectedFileName = parser.getFileName();
                        selectedFile = parser.getFile();
                        puzzleParser = parser;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("\nError reading input file, please try again\n");
                        loadError = true;
                    }

                    if (!loadError) {
                        skipLoad = true;
                        calculateShortestDistance();
                    } else {
                        continue;
                    }
                    break;
                case "C":
                    System.out.println("Returning to the main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void calculateShortestDistance() {
        while (true) {
            System.out.println("Number of lines in input: " + puzzleParser.getLines().size());

            int[][] maze = puzzleParser.getMaze();
            int[] startPoint = puzzleParser.getStartPoint();
            int[] endPoint = puzzleParser.getEndPoint();

            PathFinder solver = new PathFinder();

            String shortestPath = solver.findShortestPath(maze, startPoint, endPoint);

            // Print the shortest path
            System.out.println("\nFinding the shortest Path\n");
            System.out.println(shortestPath);

            // Ask if the user wants to rerun a puzzle
            System.out.println("\nDo you want to re-run a New puzzle Again? (Press 'Y' for Yes, 'N' for No)");
            System.out.print("Your choice: ");
            String rerunChoice = scanner.nextLine().trim();

            // Validate user input
            if (!rerunChoice.equalsIgnoreCase("Y") && !rerunChoice.equalsIgnoreCase("N")) {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            } else if (rerunChoice.equalsIgnoreCase("N")) {
                System.out.println("\nExiting the program. Goodbye!");
                System.exit(0);
            } else {
                // Returning to the main menu
                System.out.println("\nReturning to the main menu...");
                selectedFileName = null;
                puzzleParser = null;
                skipLoad = true;
                return;
            }
        }
    }
}
