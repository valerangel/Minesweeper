package Minesweeper;

public enum Difficulties {
    EASY(7, 8,9), MEDIUM(30,20, 25),
    HARD(30, 20,100);

    int sizeX;
    int sizeY;
    int bombNumber;

    Difficulties(int sizeX, int sizeY, int bombNumber) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.bombNumber = bombNumber;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY(){
        return this.sizeY;
    }

    public int getBombNumber() {
        return this.bombNumber;
    }
}
