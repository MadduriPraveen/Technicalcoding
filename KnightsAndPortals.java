import java.util.*;

public class KnightsAndPortals {

    static class Node {
        int x, y, usedTeleport, dist;
        Node(int x, int y, int usedTeleport, int dist) {
            this.x = x;
            this.y = y;
            this.usedTeleport = usedTeleport;
            this.dist = dist;
        }
    }

    public int shortestPath(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 0) emptyCells.add(new int[]{i, j});

        Queue<Node> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[n][m][2];

        queue.add(new Node(0, 0, 0, 0));
        visited[0][0][0] = true;

        int[][] dirs = {{0,1},{1,0},{-1,0},{0,-1}};

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.x == n - 1 && curr.y == m - 1) return curr.dist;

            for (int[] dir : dirs) {
                int nx = curr.x + dir[0], ny = curr.y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] == 0 && !visited[nx][ny][curr.usedTeleport]) {
                    visited[nx][ny][curr.usedTeleport] = true;
                    queue.offer(new Node(nx, ny, curr.usedTeleport, curr.dist + 1));
                }
            }

            if (curr.usedTeleport == 0) {
                for (int[] cell : emptyCells) {
                    if (!visited[cell[0]][cell[1]][1]) {
                        visited[cell[0]][cell[1]][1] = true;
                        queue.offer(new Node(cell[0], cell[1], 1, curr.dist + 1));
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int n = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int m = scanner.nextInt();

        int[][] grid = new int[n][m];
        System.out.println("Enter the grid values row by row (0 for empty, 1 for wall):");

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

        KnightsAndPortals k = new KnightsAndPortals();
        int result = k.shortestPath(grid);
        System.out.println("Shortest Path Distance: " + (result == -1 ? "Not reachable" : result));
    }
}
