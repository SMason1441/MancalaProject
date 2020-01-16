import javax.swing.*;
import java.awt.*;

public class MancalaPanelPlayer1 extends MancalaPanel {
    private int numOfMarbles = 0;


    public MancalaPanelPlayer1() {
        this.setPreferredSize(new Dimension(50,50));
        setBackground(Color.BLACK);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.ORANGE);
        g.fillOval(0,50,100,100);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString(String.valueOf(numOfMarbles), 43,100);
    }

    public void setNumOfMarbles(int marbles) {
        this.numOfMarbles = marbles;
        this.repaint();
    }

    public int getNumOfMarbles() {
        return numOfMarbles;
    }
}
