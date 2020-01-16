import javax.swing.*;
import java.awt.*;

public class MancalaPlayer2 extends MancalaPanel {
    private int numOfMarbles = 0;


    public MancalaPlayer2() {
        this.setPreferredSize(new Dimension(80,80));
        setBackground(Color.BLACK);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.MAGENTA);
        g.fillOval(0,50,80,290);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString(String.valueOf(numOfMarbles), 43,125);
    }

    public void setNumOfMarbles(int marbles) {
        this.numOfMarbles = marbles;
        this.repaint();
    }

    public int getNumOfMarbles() {
        return numOfMarbles;
    }
}
