
package Minesweeper;


import Minesweeper.Visual.FrameMain;

public class Minesweeper {

    private CellType[][] board;
    private int sizeX;
    private int sizeY;
    private int bombNumber;
    private int flagCounter;
    private int untapCounter;
    private boolean activeGame;

    public CellType[] cellsNumbers = {CellType.ONE, CellType.TWO, CellType.THREE, CellType.FOUR,
            CellType.FIVE, CellType.SIX, CellType.SEVEN, CellType.EIGHT};

    public Minesweeper(Difficulties difficulty) {
        this.sizeX = difficulty.getSizeX();
        this.sizeY = difficulty.getSizeY();
        this.bombNumber = difficulty.getBombNumber();
        this.board = new CellType[sizeX][sizeY + 1];

        this.setup();

        new FrameMain(sizeX, sizeY, this);

    }

    /**
     * This function reboots the game.
     */
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
                this.board[i][j] = CellType.UNTAPPED;
            }
            if (i == sizeX / 2) {
                this.board[i][sizeY] = CellType.SUN;
            } else {
                this.board[i][sizeY] = CellType.NULL;
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

    /**
     * This function is the one that we call on the first untap.
     * @param x coord
     * @param y coord
     */
    public void firstUntap(int x, int y) {
        if (x == sizeX / 2 && y == sizeY) {
            setup();

        } else if (this.activeGame) {
            untap(x, y);
        }
    }


    private void untap(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            if (this.board[x][y] == CellType.BOMB) {
                this.lose();
            }
            if (this.board[x][y] == CellType.UNTAPPED) {
                this.untapNoBomb(x,y);
            }
        }

    }

    private void lose(){
        this.activeGame = false;
    }



    private void untapNoBomb(int x, int y){
        int bombsAround = getBombsAround(x, y);
        if (bombsAround == 0) {
            if (this.board[x][y] == CellType.UNTAPPED) {
                this.board[x][y] = CellType.EMPTY;
                untapSurrounding(x, y);
            }
        } else {
            changeCellToANumber(x, y, bombsAround);
        }
        this.untapCounter--;

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
            return this.board[x][y] == CellType.BOMB
                    || this.board[x][y] == CellType.FLAG_BOMB;
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
        this.board[x][y] = cellsNumbers[bombsAround - 1];
    }


    public void putFlag(int x, int y) {
        if(!this.activeGame) return ;
        if(x < 0 || x >= sizeX) return;
        if (  y < 0 && y >= sizeY ) return;

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

    }

    public CellType getBoardCell(int x, int y) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY + 1) {
            return this.board[x][y];
        }
        return null;
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

    public CellType[] getCellsNumbers(){
        return cellsNumbers;
    }

}
