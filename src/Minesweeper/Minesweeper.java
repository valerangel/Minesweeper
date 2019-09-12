
package Minesweeper;


import javax.swing.*;

public class Minesweeper {

    private CellType[][] Board;
    private int sizeX;
    private int sizeY;
    private int bombNumber;
    private int flagCounter;
    private int untapCounter;
    private boolean activeGame;
    private Frame1 frame;

    public Minesweeper(Difficulties difficulty) {
        this.sizeX = difficulty.getSizeX();
        this.sizeY = difficulty.getSizeY();
        this.bombNumber = difficulty.getBombNumber();
        this.Board = new CellType[sizeX][sizeY + 1];

        this.setup();

        this.frame = new Frame1(sizeX, sizeY, this);

    }

    private void setup() {
        fillTheBoard();
        introduceBombs();
        this.flagCounter = this.bombNumber;
        this.activeGame = true;
        this.untapCounter = sizeX * sizeY - this.bombNumber;
    }

    private void fillTheBoard() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.Board[i][j] = CellType.UNTAPPED;
            }
            if (i == sizeX / 2) {
                this.Board[i][sizeY] = CellType.SUN;
            } else {
                this.Board[i][sizeY] = CellType.NULL;
            }
        }
    }

    private void introduceBombs() {
        int bombsLeft = bombNumber;
        while (bombsLeft > 0) {
            int x = (int) (Math.random() * sizeX);
            int y = (int) (Math.random() * sizeY);
            if (this.Board[x][y] == CellType.UNTAPPED) {
                this.Board[x][y] = CellType.BOMB;
                bombsLeft--;
            }
        }

    }

    public void firstUntap(int x, int y) {
        if (x == sizeX / 2 && y == sizeY) {
            setup();

        } else if (this.activeGame) {
            untap(x, y);
        }
    }


    private void untap(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            if (this.Board[x][y] == CellType.BOMB) {
                this.lose();
            }
            if (this.Board[x][y] == CellType.UNTAPPED) {
                this.untapNoBomb(x,y);
            }
        }

    }

    private void lose(){
        this.activeGame = false;
        JOptionPane.showMessageDialog(null, "You lost.");
    }

    private void untapNoBomb(int x, int y){
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

        this.checkForWin();
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
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
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


    private void checkForWin(){
        if (this.untapCounter == 0) {
            JOptionPane.showMessageDialog(null, "You won!.");
        }
    }

    private void changeCellToANumber(int x, int y, int bombsAround) {
        CellType[] cellsNumbers = {CellType.ONE, CellType.TWO, CellType.THREE, CellType.FOUR,
                CellType.FIVE, CellType.SIX, CellType.SEVEN, CellType.EIGHT};
        this.Board[x][y] = cellsNumbers[bombsAround - 1];
    }


    public void putFlag(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY && this.activeGame) {
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

    public CellType getBoardCell(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY + 1) {
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
