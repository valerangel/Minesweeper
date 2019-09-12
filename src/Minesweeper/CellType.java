package Minesweeper;

public enum CellType {
    /*
    ONE,..., EIGHT - The number of bombs around.
    UNTAPPED - The initial cells without bombs.
    BOMB - The cell with bomb.
    FLAG_BOMB, FLAG_NO_BOMB - A cell with a Flag (to mark there is a bomb).
    EMPTY - A tapped cell without bombs around.
    NULL - IT's like interface, cell out of the game.
    SUN - To start another game.
    */
    ONE(), TWO(), THREE(), FOUR(),
    FIVE(), SIX(), SEVEN(), EIGHT(),
    UNTAPPED(), BOMB(), FLAG_BOMB(), FLAG_NO_BOMB(),
    EMPTY(), NULL(), SUN();


    CellType() { }


}
