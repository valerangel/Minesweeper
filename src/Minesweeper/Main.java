package Minesweeper;

import Minesweeper.Minesweeper;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        String[] difficultiesString = {"Easy", "Medium", "Hardcore"};
        int select = JOptionPane.showOptionDialog(null, "Select a difficulty",
                "Select",0,0,null,difficultiesString,0);
        Difficulties[] difficulties = {Difficulties.EASY, Difficulties.MEDIUM, Difficulties.HARD};
        Minesweeper game = new Minesweeper(difficulties[select]);

    }
}
