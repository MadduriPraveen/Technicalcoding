import java.util.*;

public class SudokuValidator {

    public boolean isValidSudoku(char[][] board, List<int[][]> customZones) {
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                char number = board[i][j];
                if (number != '.') {
                    if (!seen.add(number + " in row " + i) ||
                        !seen.add(number + " in col " + j) ||
                        !seen.add(number + " in block " + i / 3 + "-" + j / 3)) {
                        return false;
                    }
                }
            }
        }

        for (int[][] zone : customZones) {
            Set<Character> zoneSet = new HashSet<>();
            for (int[] cell : zone) {
                char num = board[cell[0]][cell[1]];
                if (num == '.' || !zoneSet.add(num)) return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[9][9];

        System.out.println("Enter the Sudoku board values row by row (use '.' for empty cells):");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                while (true) {
                    System.out.print("Enter value for cell (" + i + "," + j + ") [1-9 or .]: ");
                    String input = scanner.next();
                    if (input.matches("[1-9\\.]")) {
                        board[i][j] = input.charAt(0);
                        break;
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }
                }
            }
        }

        System.out.print("Enter number of custom zones: ");
        int zoneCount = scanner.nextInt();
        List<int[][]> customZones = new ArrayList<>();

        for (int z = 0; z < zoneCount; z++) {
            System.out.println("Enter 9 cell positions (row and column) for custom zone " + (z + 1));
            int[][] zone = new int[9][2];
            for (int c = 0; c < 9; c++) {
                int row, col;
                while (true) {
                    System.out.print("Cell " + (c + 1) + " - Row (0-8): ");
                    row = scanner.nextInt();
                    System.out.print("Cell " + (c + 1) + " - Col (0-8): ");
                    col = scanner.nextInt();
                    if (row >= 0 && row < 9 && col >= 0 && col < 9) break;
                    else System.out.println("Invalid coordinates. Please enter values between 0 and 8.");
                }
                zone[c][0] = row;
                zone[c][1] = col;
            }
            customZones.add(zone);
        }

        SudokuValidator validator = new SudokuValidator();
        boolean isValid = validator.isValidSudoku(board, customZones);
        System.out.println("\nSudoku is " + (isValid ? "VALID ✅" : "INVALID ❌"));
    }
}
