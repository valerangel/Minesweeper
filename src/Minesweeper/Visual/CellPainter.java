package Minesweeper.Visual;

import Minesweeper.CellType;
import Minesweeper.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class CellPainter {

    private FrameMain frame;
    private Graphics g;
    private int sizeX;
    private int sizeY;
    private int LENGTH;
    private Minesweeper minesweeper;

    private boolean loseMessageAppeared;
    private boolean winMessageAppeared;

    public CellPainter(FrameMain frame){
        this.frame = frame;
        this.g = this.frame.getGraphics();
        this.sizeX = this.frame.getSizeX();
        this.sizeY = this.frame.getSizeY();
        this.LENGTH = this.frame.getLENGTH();
        this.minesweeper = this.frame.getMinesweeper();

        this.loseMessageAppeared = false;
        this.winMessageAppeared = false;
    }

    public void paint() {
        this.paintAllWhite();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY ; j++) {
                this.paintCell(i, j);
            }
        }

        drawSun(sizeX/2, sizeY);

        paintGrid();

        this.checkForWin();

        this.checkForLose();
    }

    private void paintGrid() {
        this.paintAllRectangles();
        this.paintLastRow();
    }

    private void paintAllWhite(){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sizeX * LENGTH, (sizeY + 1) * LENGTH);
    }

    private void paintAllRectangles(){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                g.setColor(Color.black);
                g.drawRect(i * LENGTH, j * LENGTH, LENGTH, LENGTH);
            }
        }
    }

    private void paintLastRow(){
        g.setColor(Color.black);
        g.drawRect((sizeX / 2) * LENGTH, (sizeY) * LENGTH, LENGTH, LENGTH);
        this.drawNumber(0, sizeY, this.minesweeper.getFlagCounter());
        this.drawNumber(sizeX - 2, sizeY, this.minesweeper.getUntappedCellsCounter());
    }

    private void paintCell(int x, int y) {
        CellType cellType = this.minesweeper.getBoardCell(x, y);

        switch (cellType) {
            case ONE:
                this.drawNumber(x,y,1);
                break;
            case TWO:
                this.drawNumber(x,y,2);
                break;
            case THREE:
                this.drawNumber(x,y,3);
                break;
            case FOUR:
                this.drawNumber(x,y,4);
                break;
            case FIVE:
                this.drawNumber(x,y,5);
                break;
            case SIX:
                this.drawNumber(x,y,6);
                break;
            case SEVEN:
                this.drawNumber(x,y,7);
                break;
            case EIGHT:
                this.drawNumber(x,y,8);
                break;
            case BOMB:
                if (!this.minesweeper.getActiveGame()) {
                    drawBomb( x, y);
                }
                break;
            case FLAG_BOMB:
                if (this.minesweeper.getActiveGame()) {
                    this.drawFlag( x, y);
                } else {
                    this.drawFlag( x, y);
                    this.drawBomb( x, y);
                }
                break;
            case FLAG_NO_BOMB:
                this.drawFlag( x, y);
                break;
            case EMPTY:
                this.drawEmpty(x, y);
                break;
            case UNTAPPED:
                break;
        }

    }

    private void drawBomb( int x, int y) {
        g.setColor(Color.BLACK);
        g.fillOval((int) ((x + 0.12) * LENGTH), (int) ((y + 0.12) * LENGTH),
                (int) (0.8 * LENGTH), (int) (0.8 * LENGTH));
    }

    private void drawEmpty( int x, int y) {
        g.setColor(Color.gray);
        g.fillRect(x * LENGTH, y * LENGTH, LENGTH, LENGTH);
    }

    private void drawNumber( int x, int y, int number) {
        g.setColor(Color.red);
        String str = "" + number;
        int fontSize = (int) (0.75 * LENGTH);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(str, (int) ((x + 0.35) * LENGTH), (int) ((y + 0.8) * LENGTH));
    }

    private void drawSun(int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillOval(x * LENGTH, y * LENGTH, LENGTH, LENGTH);

        this.drawSunSunglasses(x,y);
        this.drawSunMouth(x,y);
    }

    private void drawSunSunglasses(int x,int y){
        g.setColor(Color.black);
        g.fillRect((int) ((x + 0.25) * LENGTH), (int) ((y + 0.35) * LENGTH),
                (int) (0.25 * LENGTH), (int) (0.15 * LENGTH));
        g.fillRect((int) ((x + 0.58) * LENGTH), (int) ((y + 0.35) * LENGTH),
                (int) (0.25 * LENGTH), (int) (0.15 * LENGTH));
    }

    private void drawSunMouth(int x, int y){
        g.setColor(Color.black);
        if (this.minesweeper.getActiveGame()) {
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.65) * LENGTH),
                    (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
            g.setColor(Color.yellow);
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.55) * LENGTH),
                    (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
        } else {
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.7) * LENGTH),
                    (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
            g.setColor(Color.yellow);
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.8) * LENGTH),
                    (int) (0.5 * LENGTH), (int) (0.3 * LENGTH));
        }
    }

    private void drawFlag(int x, int y) {
        g.setColor(Color.green);
        Polygon poly = new Polygon(
                new int[]{x * LENGTH, x * LENGTH, (x + 1) * LENGTH},
                new int[]{y * LENGTH, (y + 1) * LENGTH, (int) ((y + 0.5) * LENGTH)},
                3);
        g.fillPolygon(poly);
    }

    private void checkForWin(){
        if (this.minesweeper.getUntappedCellsCounter() == 0
            && !this.winMessageAppeared) {
            JOptionPane.showMessageDialog(null, "You won!.");
            this.winMessageAppeared = true;
        } else if(this.minesweeper.getUntappedCellsCounter() != 0
            && this.winMessageAppeared){
            this.winMessageAppeared= false;
        }
    }

    private void checkForLose(){
        if(!this.minesweeper.getActiveGame()
                && !this.loseMessageAppeared){
            JOptionPane.showMessageDialog(null, "You lost.");
            this.loseMessageAppeared = true;
        } else if(this.minesweeper.getActiveGame()
                && this.loseMessageAppeared) {
            this.loseMessageAppeared = false;
        }

    }


}
