import java.util.Scanner;

public class MatrixIslandsWithDiagonals {

    public int countIslands(int[][] grid) {
        int count = 0, n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int[][] dirs = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  // vertical and horizontal
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // diagonal
        };

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfs(grid, visited, i, j, dirs);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int[][] grid, boolean[][] visited, int i, int j, int[][] dirs) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length ||
            grid[i][j] == 0 || visited[i][j]) return;

        visited[i][j] = true;
        for (int[] d : dirs)
            dfs(grid, visited, i + d[0], j + d[1], dirs);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int n = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int m = scanner.nextInt();

        int[][] grid = new int[n][m];
        System.out.println("Enter the grid row by row (1 for land, 0 for water):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                while (true) {
                    System.out.print("grid[" + i + "][" + j + "]: ");
                    int val = scanner.nextInt();
                    if (val == 0 || val == 1) {
                        grid[i][j] = val;
                        break;
                    } else {
                        System.out.println("Invalid input. Enter 0 or 1 only.");
                    }
                }
            }
        }

        MatrixIslandsWithDiagonals mwd = new MatrixIslandsWithDiagonals();
        int result = mwd.countIslands(grid);
        System.out.println("Number of islands (including diagonals): " + result);
    }
}
