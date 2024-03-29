package test;

import Minesweeper.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {

    @Test
    public void firstTap3x3Board0Bombs() {
        //First test, clear board
        CellType[][] boardTest0Bombs = {
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED}
        };

        Minesweeper game0Bombs = new Minesweeper(boardTest0Bombs);

        CellType[][] boardFinal0Bombs = {
                {CellType.EMPTY, CellType.EMPTY, CellType.EMPTY},
                {CellType.EMPTY, CellType.EMPTY, CellType.EMPTY},
                {CellType.EMPTY, CellType.EMPTY, CellType.EMPTY}
        };

        assertArrayEquals(boardFinal0Bombs, game0Bombs.tap(1, 1), "The board is not the same");
    }

    @Test
    public void firstTap3x3Board8Bombs() {
        //Test 3x3 Board with 8 bombs
        CellType[][] boardTest8Bombs = {
                {CellType.BOMB, CellType.BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.UNTAPPED, CellType.BOMB},
                {CellType.BOMB, CellType.BOMB, CellType.BOMB}
        };

        Minesweeper game8Bombs = new Minesweeper(boardTest8Bombs);

        CellType[][] boardFinal8Bombs = {
                {CellType.BOMB, CellType.BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.EIGHT, CellType.BOMB},
                {CellType.BOMB, CellType.BOMB, CellType.BOMB}
        };

        assertArrayEquals(boardFinal8Bombs, game8Bombs.tap(1, 1), "The board is not the same");
    }

    @Test
    public void firstTap3x3Board1Bombs() {
        //Test for 1 bomb
        CellType[][] boardTest1Bombs = {
                {CellType.BOMB, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED}
        };

        Minesweeper game1Bombs = new Minesweeper(boardTest1Bombs);

        CellType[][] boardFinal1Bombs = {
                {CellType.BOMB, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.ONE, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED}
        };

        assertArrayEquals(boardFinal1Bombs, game1Bombs.tap(1, 1), "The board is not the same");
    }

    @Test
    public void firstPutFlag3x3Board1Bombs() {
        //Test for 1 bomb
        CellType[][] boardTest1BombsFlag = {
                {CellType.BOMB, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED}
        };

        Minesweeper game1BombsFlag = new Minesweeper(boardTest1BombsFlag);

        CellType[][] boardFinal1BombsFlag = {
                {CellType.BOMB, CellType.UNTAPPED, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.FLAG_NO_BOMB, CellType.UNTAPPED},
                {CellType.UNTAPPED, CellType.UNTAPPED, CellType.UNTAPPED}
        };

        assertArrayEquals(boardFinal1BombsFlag, game1BombsFlag.putFlag(1, 1),
                "The board is not the same");
    }

    @Test
    public void firstPutFlag3x3Board9Bombs() {
        //Test for 9 bomb
        CellType[][] boardTest9BombsFlag = {
                {CellType.BOMB, CellType.BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.BOMB, CellType.BOMB}
        };

        Minesweeper game9BombsFlag = new Minesweeper(boardTest9BombsFlag);

        CellType[][] boardFinal9BombsFlag = {
                {CellType.BOMB, CellType.BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.FLAG_BOMB, CellType.BOMB},
                {CellType.BOMB, CellType.BOMB, CellType.BOMB}
        };

        assertArrayEquals(boardFinal9BombsFlag, game9BombsFlag.putFlag(1, 1),
                "The board is not the same");


    }


}