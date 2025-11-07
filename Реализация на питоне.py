def is_valid(board, row, col, num):
    """
    Проверяет, можно ли разместить число num в клетке (row, col).
    """
    # Проверка строки (раздел 5.4 методички - аналогично проверке в maze)
    for j in range(9):
        if board[row][j] == num:
            return False

    # Проверка столбца
    for i in range(9):
        if board[i][col] == num:
            return False

    # Проверка 3x3 блока
    start_row = (row // 3) * 3
    start_col = (col // 3) * 3
    for i in range(3):
        for j in range(3):
            if board[start_row + i][start_col + j] == num:
                return False

    return True

def find_empty_cell(board):
    """
    Находит первую пустую клетку (значение 0) на доске.
    Возвращает (row, col) или None, если пустых нет.
    """
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return (i, j)
    return None

def solve_sudoku(board):
    """
    Решает судоку с помощью backtracking (раздел 5.1, 5.2 методички).
    Изменяет переданную доску (board) на месте.
    Возвращает True, если решение найдено, иначе False.
    """
    empty_cell = find_empty_cell(board)
    if not empty_cell:
        return True  # Базовый случай: Судоку решено (нет пустых клеток)

    row, col = empty_cell

    # Рекурсивный случай: перебираем числа от 1 до 9
    for num in range(1, 10):
        if is_valid(board, row, col, num):
            board[row][col] = num  # Сделать выбор (make_choice)

            if solve_sudoku(board):  # Рекурсивный вызов
                return True  # Решение найдено

            # Откат (undo_choice), если путь не привел к решению
            board[row][col] = 0

    # Если ни одно число не работает, возвращаем False
    return False

# Пример использования:
if __name__ == "__main__":
    # Пустые клетки обозначены 0
    sudoku_board = [
        [5, 3, 0, 0, 7, 0, 0, 0, 0],
        [6, 0, 0, 1, 9, 5, 0, 0, 0],
        [0, 9, 8, 0, 0, 0, 0, 6, 0],
        [8, 0, 0, 0, 6, 0, 0, 0, 3],
        [4, 0, 0, 8, 0, 3, 0, 0, 1],
        [7, 0, 0, 0, 2, 0, 0, 0, 6],
        [0, 6, 0, 0, 0, 0, 2, 8, 0],
        [0, 0, 0, 4, 1, 9, 0, 0, 5],
        [0, 0, 0, 0, 8, 0, 0, 7, 9]
    ]

    print("Исходное судоку:")
    for row in sudoku_board:
        print(row)

    if solve_sudoku(sudoku_board):
        print("\nРешенное судоку:")
        for row in sudoku_board:
            print(row)
    else:
        print("\nРешение не найдено.")