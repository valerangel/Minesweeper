package Minesweeper;

public enum Difficulties {
    EASY(7, 8), MEDIUM(9, 15), HARD(11, 30);

    int size;
    int bombNumber;

    Difficulties(int size, int bombNumber) {
        this.size = size;
        this.bombNumber = bombNumber;
    }

    public int getSize() {
        return this.size;
    }

    public int getBombNumber() {
        return this.bombNumber;
    }
}
