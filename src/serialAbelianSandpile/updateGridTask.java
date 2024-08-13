package serialAbelianSandpile;

import java.util.concurrent.RecursiveAction;

public class updateGridTask extends RecursiveAction {

    private static final int THRESHOLD = 16;
    private int rows;
    private int columns;
    private int[][] grid;
    private int[][] updateGrid;
    private int start;
    private int end;

    public updateGridTask(int rows, int columns, int[][] grid, int[][] updateGrid, int start, int end) {
        this.rows = rows;
        this.columns = columns;
        this.grid = grid;
        this.updateGrid = updateGrid;
        this.start = start;
        this.end = end;
    }

    @Override
    public void compute() {
        // do not update border
        if (end - start <= THRESHOLD) {
            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < columns - 1; j++) {
                    updateGrid[i][j] = (grid[i][j] % 4) +
                            (grid[i - 1][j] / 4) +
                            grid[i + 1][j] / 4 +
                            grid[i][j - 1] / 4 +
                            grid[i][j + 1] / 4;
                }
            }
        } else {
            int middle = (start + end) / 2;
            updateGridTask leftTask = new updateGridTask(rows, columns, grid, updateGrid, start, middle);
            updateGridTask rightTask = new updateGridTask(rows, columns, grid, updateGrid, middle, end);

            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

    }
}
