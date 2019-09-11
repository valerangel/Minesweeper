
package Minesweeper;

import org.jetbrains.annotations.Contract;

import javax.swing.*;

public class Minesweeper {

    private CellType[][] Board;
    private int size;
    private int bombNumber;
    private int flagCounter;
    private int untapCounter;
    private boolean activeGame;
    private Frame1 frame;

    public Minesweeper(Difficulties difficulty) {
        this.size = difficulty.getSize();
        this.bombNumber = difficulty.getBombNumber();
        this.Board = new CellType[size][size + 1];

        this.setup();

        this.frame = new Frame1(size, this);

    }

    private void setup() {
        fillTheBoard();
        introduceBombs();
        this.flagCounter = this.bombNumber;
        this.activeGame = true;
        this.untapCounter = size * size - this.bombNumber;
    }

    private void fillTheBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.Board[i][j] = CellType.UNTAPPED;
            }
            if (i == size / 2) {
                this.Board[i][size] = CellType.SUN;
            } else {
                this.Board[i][size] = CellType.NULL;
            }
        }
    }

    private void introduceBombs() {
        int bombsLeft = bombNumber;
        while (bombsLeft > 0) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if (this.Board[x][y] == CellType.UNTAPPED) {
                this.Board[x][y] = CellType.BOMB;
                bombsLeft--;
            }
        }

    }

    public void firstUntap(int x, int y) {
        if (x == size / 2 && y == size) {
            setup();

        } else if (this.activeGame) {
            untap(x, y);
        }
    }


    private void untap(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            if (this.Board[x][y] == CellType.BOMB) {
                this.activeGame = false;
                JOptionPane.showMessageDialog(null, "You lost.");
            }
            if (this.Board[x][y] == CellType.UNTAPPED) {
                int bombsAround = getBombsAround(x, y);
                if (bombsAround == 0) {
                    if (this.Board[x][y] == CellType.UNTAPPED) {
                        this.Board[x][y] = CellType.EMPTY;
                        untapSurrounding(x, y);
                    }
                } else {
                    changeCellToANumber(x, y, bombsAround);
                }
                this.untapCounter--;

                if (this.untapCounter == 0) {
                    JOptionPane.showMessageDialog(null, "You won!.");
                }
            }
        }

    }

    private int getBombsAround(int x, int y) {
        int numberOfBombsAround = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(x == i && y == j)) {
                    if (isABomb(i, j)) {
                        numberOfBombsAround++;
                    }
                }
            }
        }
        return numberOfBombsAround;
    }

    private boolean isABomb(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return this.Board[x][y] == CellType.BOMB || this.Board[x][y] == CellType.FLAG_BOMB;
        }
        return false;
    }

    private void untapSurrounding(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(x == i && y == j)) {
                    untap(i, j);
                }
            }
        }
    }

    private void changeCellToANumber(int x, int y, int bombsAround) {
        CellType[] cellsNumbers = {CellType.ONE, CellType.TWO, CellType.THREE, CellType.FOUR,
                CellType.FIVE, CellType.SIX, CellType.SEVEN, CellType.EIGHT};
        this.Board[x][y] = cellsNumbers[bombsAround - 1];
    }


    public void putFlag(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && this.activeGame) {
            if (this.Board[x][y] == CellType.UNTAPPED) {
                this.Board[x][y] = CellType.FLAG_NO_BOMB;
                this.flagCounter--;
            } else if (this.Board[x][y] == CellType.BOMB) {
                this.Board[x][y] = CellType.FLAG_BOMB;
                this.flagCounter--;
            } else if (this.Board[x][y] == CellType.FLAG_BOMB) {
                this.Board[x][y] = CellType.BOMB;
                this.flagCounter++;
            } else if (this.Board[x][y] == CellType.FLAG_NO_BOMB) {
                this.Board[x][y] = CellType.UNTAPPED;
                this.flagCounter++;
            }
        }
    }

    public CellType getBoard(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size + 1) {
            return this.Board[x][y];
        } else return null;
    }

    public boolean getActiveGame() {
        return this.activeGame;
    }

    public int getFlagCounter() {
        return this.flagCounter;
    }

    public int getUntapCounter() {
        return this.untapCounter;
    }

}
