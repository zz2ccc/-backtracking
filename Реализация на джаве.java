import java.util.Arrays;

public class SudokuSolver {

    /**
     * Проверяет, можно ли разместить число num в клетке (row, col).
     */
    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Проверка строки (раздел 5.4 методички - аналогично проверке в maze)
        for (int j = 0; j < 9; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        // Проверка столбца
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Проверка 3x3 блока
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Находит первую пустую клетку (значение 0) на доске.
     * Возвращает массив [row, col] или [-1, -1], если пустых нет.
     */
    public static int[] findEmptyCell(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Пустых клеток нет
    }

    /**
     * Решает судоку с помощью backtracking (раздел 5.1, 5.2 методички).
     * Изменяет переданную доску (board) на месте.
     * Возвращает true, если решение найдено, иначе false.
     */
    public static boolean solveSudoku(int[][] board) {
        int[] emptyCell = findEmptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        // Базовый случай: если нет пустых клеток, судоку решено
        if (row == -1) {
            return true;
        }

        // Рекурсивный случай: перебираем числа от 1 до 9
        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num; // Сделать выбор (make_choice)

                if (solveSudoku(board)) { // Рекурсивный вызов
                    return true; // Решение найдено
                }

                // Откат (undo_choice), если путь не привел к решению
                board[row][col] = 0;
            }
        }

        // Если ни одно число не работает, возвращаем false
        return false;
    }

    public static void main(String[] args) {
        int[][] sudoku_board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("Исходное судоку:");
        for (int[] row : sudoku_board) {
            System.out.println(Arrays.toString(row));
        }

        if (solveSudoku(sudoku_board)) {
            System.out.println("\nРешенное судоку:");
            for (int[] row : sudoku_board) {
                System.out.println(Arrays.toString(row));
            }
        } else {
            System.out.println("\nРешение не найдено.");
        }
    }
}