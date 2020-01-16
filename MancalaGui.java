import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import java.util.TimerTask;

public class MancalaGui extends JPanel {
    private MancalaPanelPlayer1[] p1panels;
    private MancalaPanelPlayer2[] p2panels;
    private MancalaPlayer1 player1;
    private MancalaPlayer2 player2;
    private MancalaPanel[] wholeBoard;
    private JPanel panel = new JPanel();

    private JFrame holeNumbers;
    private MenuScreen m;

    public MancalaGui(MenuScreen m) {
        super();
        this.m = m;
        p1panels = new MancalaPanelPlayer1[6];
        p2panels = new MancalaPanelPlayer2[6];
        player1 = new MancalaPlayer1();
        player2 = new MancalaPlayer2();
        wholeBoard = new MancalaPanel[14];

        panel.setLayout(new GridLayout(2,6));
        setLayout(new BorderLayout());

        for (int i = 0; i < 6; i++) {
            p2panels[5 - i] = new MancalaPanelPlayer2();
            panel.add(p2panels[5 - i]);
            wholeBoard[12 - i] = p2panels[5 - i];
        }

        for (int i = 0; i < 6; i++) {
            p1panels[i] = new MancalaPanelPlayer1();
            panel.add(p1panels[i]);
            wholeBoard[i] = p1panels[i];
        }

        wholeBoard[6] = player1;
        wholeBoard[13] = player2;

        add(player1, BorderLayout.EAST);
        add(player2, BorderLayout.WEST);
        add(panel, BorderLayout.CENTER);
        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void startGame() {
        for (MancalaPanelPlayer1 panel : p1panels) {
            panel.setNumOfMarbles(4);
        }
        for (MancalaPanelPlayer2 panel : p2panels) {
            panel.setNumOfMarbles(4);
        }
        repaint();
    }

    public void moveSpaces(int startingSpace, boolean isPlayer1) {
        int hand = 0;
        int space = startingSpace;
        if (isPlayer1) {
            hand = p1panels[startingSpace].getNumOfMarbles();
            p1panels[startingSpace].setNumOfMarbles(0);
        } else {
            hand = p2panels[startingSpace - 7].getNumOfMarbles();
            p2panels[startingSpace - 7].setNumOfMarbles(0);
        }
        int marbles;

        for (int i = 1; i < hand + 1; i++) {
            space = (startingSpace + i) % 14;
            if (space != startingSpace) {
                if (isPlayer1) {
                    if (space != 13) {
                        marbles = wholeBoard[space].getNumOfMarbles();
                        wholeBoard[space].setNumOfMarbles(marbles + 1);

                    } else {
                        hand++;
                    }
                } else {
                    if (space != 6) {
                        marbles = wholeBoard[space].getNumOfMarbles();
                        wholeBoard[space].setNumOfMarbles(marbles + 1);

                    } else {
                        hand++;
                    }
                }
            } else {
                hand++;
            }
        }
    }

    public void specialRuleExec(int boardSpace1, int boardSpace2, boolean isPlayerOne) {
        int marbleDump = wholeBoard[boardSpace1].getNumOfMarbles() + wholeBoard[boardSpace2].getNumOfMarbles();
        wholeBoard[boardSpace1].setNumOfMarbles(0);
        wholeBoard[boardSpace2].setNumOfMarbles(0);
        if (isPlayerOne) {
            wholeBoard[6].setNumOfMarbles(wholeBoard[6].getNumOfMarbles() + marbleDump);
        } else {
            wholeBoard[13].setNumOfMarbles(wholeBoard[13].getNumOfMarbles() + marbleDump);
        }
    }

    public void makeBoardInfoGUI(int [] board) {
        holeNumbers = new JFrame();
        holeNumbers.setSize(500,200);
        JPanel info = new JPanel(new GridLayout(2,6));
        for (int i = 12; i > -1; i--) {
            if (i > 6) {
                info.add(new JLabel(String.format("<html>Space at %d:<br>%d marbles  </html>", i - 6, board[i])));
            } else if (i < 6) {
                info.add(new JLabel(String.format("<html>Space at %d:<br>%d marbles  </html>", 6 - i, board[5 - i])));
            }
        }
        holeNumbers.add(info);
        holeNumbers.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        holeNumbers.setResizable(false);
        holeNumbers.setVisible(true);
    }

    public void closeBoardInfoGUI() {
        holeNumbers.dispose();
    }


    public int[] getWholeBoard() {
        int[] board = new int[14];
        int i = 0;
        for(MancalaPanel m : wholeBoard) {
            board[i] = m.getNumOfMarbles();
            i++;
        }
        return board;
    }
}
