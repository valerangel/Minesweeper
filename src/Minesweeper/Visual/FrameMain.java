package Minesweeper.Visual;

import Minesweeper.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrameMain implements MouseListener {

    private final int LENGTH = 30;

    private JFrame jframe;
    private JPanel jpanel;
    private Minesweeper minesweeper;
    private Graphics g;
    private CellPainter cellPainter;

    private int sizeX;
    private int sizeY;

    public FrameMain(int sizeX, int sizeY, Minesweeper minesweeper) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.minesweeper = minesweeper;
        this.setup();
        this.cellPainter = new CellPainter(this);

        this.paint();

        //We need to force the first click to get the first paint().
        this.forceClick();
    }

    private void forceClick(){
        MouseEvent e = new MouseEvent(jframe, 0, 0, 1,
                (sizeX + 1) * LENGTH, (sizeY + 1) * LENGTH, 1, false);
        this.mouseClicked(e);
    }

    /**
     * This function prepares the frame where the game runs.
     */
    private void setup() {
        this.jpanel = new JPanel(new BorderLayout());
        this.jframe = new JFrame("Minesweeper");
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setSize(sizeX * LENGTH + 18, (sizeY + 1) * LENGTH + 43);

        this.jframe.setVisible(true);
        this.jpanel.addMouseListener(this);
        this.jpanel.setSize(sizeX * LENGTH, (sizeY + 1) * LENGTH);
        this.jframe.add(this.jpanel);

        this.g = jpanel.getGraphics();
    }

    private void paint(){
        this.cellPainter.paint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX() / LENGTH;
        int y = e.getY() / LENGTH;

        if (!e.isMetaDown()) {
            this.minesweeper.tap(x, y);
        } else {
            this.minesweeper.putFlag(x, y);
        }

        tryToStartAgain(x, y);

        this.paint();
    }

    private void tryToStartAgain(int x, int y) {
        if(x == sizeX/2 && y == sizeY){
            this.minesweeper.setup();
        }
    }

    protected Minesweeper getMinesweeper(){
        return this.minesweeper;
    }

    protected Graphics getGraphics(){
        return this.g;
    }

    protected int getLENGTH(){
        return LENGTH;
    }

    protected int getSizeX(){
        return this.sizeX;
    }

    protected int getSizeY(){
        return this.sizeY;
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
