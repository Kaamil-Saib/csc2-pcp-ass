package serialAbelianSandpile;

import java.util.concurrent.RecursiveTask;

public class updateGridTask extends RecursiveTask<Boolean> {

    private final int THRESHOLD;
    private int rows;
    private int columns;
    private int[][] grid;
    private int[][] updateGrid;
    private int offset;
    private boolean change;

    public updateGridTask(int rows, int columns, int[][] grid, int[][] updateGrid, int offset, int THRESHOLD) {
        this.rows = rows;
        this.columns = columns;
        this.grid = grid;
        this.updateGrid = updateGrid;
        this.offset = offset;
        change = false;
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public Boolean compute() {
        // do not update border
        if (columns < THRESHOLD) {
            for (int i = 1; i <= rows; i++) {
                for (int j = offset; j < columns + offset; j++) {
                    updateGrid[i][j] = (grid[i][j] % 4) +
                            (grid[i - 1][j] / 4) +
                            grid[i + 1][j] / 4 +
                            grid[i][j - 1] / 4 +
                            grid[i][j + 1] / 4;
                    if (grid[i][j] != updateGrid[i][j]) {
                        change = true;
                    }
                }
            }
            return change;
        } else {
            int middle = columns / 2;
            updateGridTask leftTask = new updateGridTask(rows, middle, grid, updateGrid, offset, THRESHOLD);
            updateGridTask rightTask = new updateGridTask(rows, columns - middle, grid, updateGrid, middle + offset,
                    THRESHOLD);

            leftTask.fork();
            boolean rightBool = rightTask.compute();
            boolean leftBool = leftTask.join();
            return rightBool || leftBool;
        }

    }
}
