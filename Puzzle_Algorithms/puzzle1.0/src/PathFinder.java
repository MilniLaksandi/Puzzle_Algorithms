import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {
    static class  Step implements Comparable<Step> {
        int row;
        int col;
        int distanceFromStart;
        String directions;

        Queue<int[]> pathQueue = new LinkedList<>();

        Step(int row, int col, int distanceFromStart, String directions, int[] lastMove) {
            this.row = row;
            this.col = col;
            this.distanceFromStart = distanceFromStart;
            this.directions = directions + " (" + (col + 1) + ", " + (row + 1) + ")\n";
            this.pathQueue.add(lastMove);
        }

        @Override
        public int compareTo(Step otherStep) {
            return this.distanceFromStart == otherStep.distanceFromStart ? this.directions.compareTo(otherStep.directions) : this.distanceFromStart - otherStep.distanceFromStart;
        }

        @Override
        public String toString() {
            String[] moves = directions.split("\n");
            StringBuilder result = new StringBuilder();
            result.append("1. Start at ").append(moves[0]).append("\n");
            for (int i = 1; i < moves.length; i++) {
                result.append(i + 1).append(". ").append("Move ").append(moves[i]).append("\n");
            }
            result.append(moves.length + 1).append(". ").append("Done!");
            return result.toString();
        }
    }

    public String findShortestPath(int[][] maze, int[] ball, int[] hole) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];

        Queue<Step> priorityQueue = new LinkedList<>();
        priorityQueue.offer(new Step(ball[0], ball[1], 0, "", new int[]{}));

        String[] moves = {"Up", "Down", "Left", "Right"};
        int[][] moveCoords = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!priorityQueue.isEmpty()) {
            Step currentStep = priorityQueue.poll();
            if (currentStep.row == hole[0] && currentStep.col == hole[1]) {
                return currentStep.toString();
            }

            visited[currentStep.row][currentStep.col] = true; // Mark the current cell as visited

            for (int i = 0; i < moveCoords.length; i++) {
                int row = currentStep.row;
                int col = currentStep.col;
                int distanceFromStart = currentStep.distanceFromStart;
                String directions = currentStep.directions;

                while (row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] == 0 && (row != hole[0] || col != hole[1])) {
                    row += moveCoords[i][0];
                    col += moveCoords[i][1];
                    distanceFromStart += 1;
                }

                if (row != hole[0] || col != hole[1]) {
                    row -= moveCoords[i][0];
                    col -= moveCoords[i][1];
                    distanceFromStart -= 1;
                }

                if (!visited[row][col]) {
                    priorityQueue.offer(new Step(row, col, distanceFromStart, directions + moves[i], new int[]{row, col}));
                }
            }
        }

        return "No valid path found!";
    }
}
