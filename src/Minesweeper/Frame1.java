package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;

public class Frame1 implements MouseListener {

    private final int LENGTH = 50;

    private JFrame jframe;
    private JPanel jpanel;
    private Minesweeper minesweeper;

    private int size;

    public Frame1(int size, Minesweeper minesweeper) {
        this.size = size;
        this.minesweeper = minesweeper;
        this.setup();

        this.paint();

        MouseEvent e = new MouseEvent(jframe, 0, 0, 1,
                (size + 1) * LENGTH, (size + 1) * LENGTH, 1, false);
        this.mouseClicked(e);
    }

    private void setup() {
        this.jpanel = new JPanel(new BorderLayout());
        this.jframe = new JFrame("Minesweeper");
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setSize(size * LENGTH + 18, (size + 1) * LENGTH + 43);

        this.jframe.setVisible(true);
        this.jpanel.addMouseListener(this);
        this.jpanel.setSize(size * LENGTH, (size + 1) * LENGTH);
        this.jframe.add(this.jpanel);
    }

    private void paint() {
        paintGrid();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                this.paintCell(i, j);
            }
        }

    }

    private void paintGrid() {
        Graphics g = jpanel.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size * LENGTH, (size + 1) * LENGTH);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                g.setColor(Color.black);
                g.drawRect(i * LENGTH, j * LENGTH, LENGTH, LENGTH);
            }
        }

        g.setColor(Color.black);
        g.drawRect((size / 2) * LENGTH, (size) * LENGTH, LENGTH, LENGTH);
        this.drawNumber(g, 0, size, this.minesweeper.getFlagCounter());
        this.drawNumber(g, size - 2, size, this.minesweeper.getUntapCounter());
    }

    private void paintCell(int x, int y) {
        Graphics g = jpanel.getGraphics();
        CellType cellType = this.minesweeper.getBoard(x, y);

        CellType[] cellsNumbers = {CellType.ONE, CellType.TWO, CellType.THREE, CellType.FOUR,
                CellType.FIVE, CellType.SIX, CellType.SEVEN, CellType.EIGHT};
        for (int i = 0; i < 8; i++) {
            if(cellType==cellsNumbers[i]){
                this.drawNumber(g,x,y,i+1);
            }
        }

        if (cellType == CellType.EMPTY) {
            this.drawEmpty(g, x, y);
        } else if (cellType == CellType.SUN) {
            this.drawSun(g, x, y);
        } else if (cellType == CellType.FLAG_NO_BOMB) {
            this.drawFlag(g, x, y);
        } else if (cellType == CellType.FLAG_BOMB) {
            if (this.minesweeper.getActiveGame()) {
                this.drawFlag(g, x, y);
            } else {
                this.drawFlag(g, x, y);
                this.drawBomb(g, x, y);
            }
        } else if (cellType == CellType.BOMB) {
            if (this.minesweeper.getActiveGame() == false) {
                drawBomb(g, x, y);
            }
        }

    }

    private void drawBomb(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.fillOval((int) ((x + 0.12) * LENGTH), (int) ((y + 0.12) * LENGTH), (int) (0.8 * LENGTH), (int) (0.8 * LENGTH));
    }

    private void drawEmpty(Graphics g, int x, int y) {
        g.setColor(Color.gray);
        g.fillRect(x * LENGTH, y * LENGTH, LENGTH, LENGTH);
    }

    private void drawNumber(Graphics g, int x, int y, int number) {
        g.setColor(Color.red);
        String str = "" + number;
        int fontSize = (int) (0.75 * LENGTH);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(str, (int) ((x + 0.35) * LENGTH), (int) ((y + 0.8) * LENGTH));
    }

    private void drawSun(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillOval(x * LENGTH, y * LENGTH, LENGTH, LENGTH);
        //Draw the sunglasses of the sun
        g.setColor(Color.black);
        g.fillRect((int) ((x + 0.22) * LENGTH), (int) ((y + 0.3) * LENGTH), (int) (0.25 * LENGTH), (int) (0.15 * LENGTH));
        g.fillRect((int) ((x + 0.57) * LENGTH), (int) ((y + 0.3) * LENGTH), (int) (0.25 * LENGTH), (int) (0.15 * LENGTH));
        //Draw the mouth of the sun
        if (this.minesweeper.getActiveGame()) {
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.65) * LENGTH), (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
            g.setColor(Color.yellow);
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.55) * LENGTH), (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
        } else {
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.7) * LENGTH), (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
            g.setColor(Color.yellow);
            g.fillOval((int) ((x + 0.32) * LENGTH), (int) ((y + 0.8) * LENGTH), (int) (0.4 * LENGTH), (int) (0.2 * LENGTH));
        }
    }

    private void drawFlag(Graphics g, int x, int y) {
        g.setColor(Color.green);
        Polygon poly = new Polygon(
                new int[]{x * LENGTH, x * LENGTH, (x + 1) * LENGTH},
                new int[]{y * LENGTH, (y + 1) * LENGTH, (int) ((y + 0.5) * LENGTH)},
                3);
        g.fillPolygon(poly);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX() / LENGTH;
        int y = e.getY() / LENGTH;

        if (!e.isMetaDown()) {
            this.minesweeper.firstUntap(x, y);
        } else {
            this.minesweeper.putFlag(x, y);
        }
        this.paint();

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
