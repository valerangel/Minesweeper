
package Minesweeper;


import Minesweeper.Visual.FrameMain;

public class Minesweeper {

    private CellType[][] board;
    private int sizeX;
    private int sizeY;
    private int bombNumber;
    private int flagCounter;
    private int tapCounter;
    private boolean activeGame;

    public CellType[] cellsNumbers = {CellType.ONE, CellType.TWO, CellType.THREE, CellType.FOUR,
            CellType.FIVE, CellType.SIX, CellType.SEVEN, CellType.EIGHT};

    public Minesweeper(Difficulties difficulty) {
        this.sizeX = difficulty.getSizeX();
        this.sizeY = difficulty.getSizeY();
        this.bombNumber = difficulty.getBombNumber();
        this.board = new CellType[sizeX][sizeY];

        this.setup();

        new FrameMain(sizeX, sizeY, this);
    }

    public Minesweeper(CellType[][] board) {
        this.board = board;
        this.sizeX = board.length;
        this.sizeY = board[0].length;

        this.bombNumber = 10000;

        this.flagCounter = 0;
        this.tapCounter = sizeX * sizeY;

        this.activeGame = true;
    }


    /**
     * This function reboots the game.
     */
    public void setup() {
        fillTheBoard();
        introduceBombs();

        this.flagCounter = this.bombNumber;
        this.tapCounter = sizeX * sizeY - this.bombNumber;

        this.activeGame = true;
    }

    private void fillTheBoard() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.board[i][j] = CellType.UNTAPPED;
            }
        }
    }

    private void introduceBombs() {
        int bombsLeft = bombNumber;
        while (bombsLeft > 0) {
            int x = (int) (Math.random() * sizeX);
            int y = (int) (Math.random() * sizeY);
            if (this.board[x][y] == CellType.UNTAPPED) {
                this.board[x][y] = CellType.BOMB;
                bombsLeft--;
            }
        }

    }

    public CellType[][] tap(int x, int y) {
        if (!this.activeGame) return null;
        if (x < 0 || x >= sizeX) return null;
        if (y < 0 || y >= sizeY) return null;

        if (this.board[x][y] == CellType.BOMB) {
            this.lose();
        }
        if (this.board[x][y] == CellType.UNTAPPED) {
            this.tapNoBomb(x, y);
        }

        return this.board;
    }

    private void lose() {
        this.activeGame = false;
    }


    private void tapNoBomb(int x, int y) {
        int bombsAround = getBombsAround(x, y);
        if (bombsAround == 0) {
            if (this.board[x][y] == CellType.UNTAPPED) {
                this.board[x][y] = CellType.EMPTY;
                tapSurrounding(x, y);
            }
        } else {
            changeCellToANumber(x, y, bombsAround);
        }
        this.tapCounter--;

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

        if (x < 0 || x >= sizeX) return false;
        if (y < 0 || y >= sizeY) return false;

        return this.board[x][y] == CellType.BOMB
                || this.board[x][y] == CellType.FLAG_BOMB;

    }

    private void tapSurrounding(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (x != i || y != j) {
                    tap(i, j);
                }
            }
        }
    }

    private void changeCellToANumber(int x, int y, int bombsAround) {
        this.board[x][y] = cellsNumbers[bombsAround - 1];
    }

    public CellType[][] putFlag(int x, int y) {
        if (!this.activeGame) return null;
        if (x < 0 || x >= sizeX) return null;
        if (y < 0 && y >= sizeY) return null;

        if (this.board[x][y] == CellType.UNTAPPED) {
            this.board[x][y] = CellType.FLAG_NO_BOMB;
            this.flagCounter--;
        } else if (this.board[x][y] == CellType.BOMB) {
            this.board[x][y] = CellType.FLAG_BOMB;
            this.flagCounter--;
        } else if (this.board[x][y] == CellType.FLAG_BOMB) {
            this.board[x][y] = CellType.BOMB;
            this.flagCounter++;
        } else if (this.board[x][y] == CellType.FLAG_NO_BOMB) {
            this.board[x][y] = CellType.UNTAPPED;
            this.flagCounter++;
        }
        return this.board;
    }


    public CellType getBoardCell(int x, int y) {
        if (x < 0 || x >= sizeX) return null;
        if (y < 0 || y >= sizeY) return null;

        return this.board[x][y];

    }

    public boolean getActiveGame() {
        return this.activeGame;
    }

    public int getFlagCounter() {
        return this.flagCounter;
    }

    public int getTapCounter() {
        return this.tapCounter;
    }

    public CellType[][] getBoard() {
        return this.board;
    }

    public CellType[] getCellsNumbers() {
        return cellsNumbers;
    }

}
