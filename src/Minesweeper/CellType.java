package Minesweeper;

public enum CellType {
    /**
    ONE,..., EIGHT - The number of bombs around.
    UNTAPPED - The initial cells without bombs.
    BOMB - The cell with bomb.
    FLAG_BOMB, FLAG_NO_BOMB - A cell with a Flag (to mark if there is a bomb).
    EMPTY - A tapped cell without bombs around.
    */
    ONE(), TWO(), THREE(), FOUR(),
    FIVE(), SIX(), SEVEN(), EIGHT(),
    UNTAPPED(), BOMB(), FLAG_BOMB(),
    FLAG_NO_BOMB(), EMPTY();


    CellType() { }


}
