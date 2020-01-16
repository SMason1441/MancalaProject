import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuScreen {
    private JFrame frame;
    private JPanel panel;
    private JButton rules;
    private JButton twoP;
    private JButton vsAI;
    private JButton next;
    private JButton gotIt;
    private JPanel rulesPanel;
    private JPanel imagePanel;
    private MancalaGui mancalaGui;
    private JLabel prompt;
    private JPanel responsePanel;
    private JButton submitResponse = new JButton("   Enter   ");
    private JTextField response = new JTextField("1-6");
    private JPanel chooserOuter;
    private JPanel chooserInner;
    private JButton meFirst;
    private JButton ai;
    private JPanel outerGamePane;

    public MenuScreen() {
        frame = new JFrame();
        panel = new JPanel(new BorderLayout());
        rules = new JButton("Rules");
        twoP = new JButton("Two Players");
        vsAI = new JButton("Single Player vs AI");

        panel.setBackground(Color.BLACK);
        panel.add(new JLabel("<html><font color=white size=6><b><center> Welcome to Simon's Mancala Java Applet!</center></b></font></html>"), BorderLayout.NORTH);
        try {
            panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/mancala.jpg")))), BorderLayout.CENTER);
        } catch (IOException ignored) {
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(rules);
        bottomPanel.add(twoP);
        bottomPanel.add(vsAI);
        bottomPanel.setBackground(Color.BLACK);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        rules.addActionListener(actionEvent -> {
            makeRulesWindow();
        });

        twoP.addActionListener(actionEvent -> startTwoPlayerGame());

        vsAI.addActionListener(actionEvent -> startSinglePlayerGame());

        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
    }

    private void makeRulesWindow() {
        rules.setVisible(false);
        twoP.setVisible(false);
        vsAI.setVisible(false);
        panel.setVisible(false);
        rulesPanel = new JPanel(new BorderLayout());
        JPanel insidePanel = new JPanel(new GridLayout(2, 1));
        next = new JButton("Next");
        insidePanel.setBackground(Color.BLACK);
        rulesPanel.setBackground(Color.BLACK);
        try {
            insidePanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/MANCALA-game_bg_combined.jpg")))));
        } catch (IOException ignored) {
        }
        rulesPanel.add(new JLabel("<html><font color=white size=6>Rules of Mancala<font></html"), BorderLayout.NORTH);
        insidePanel.add(new JLabel("<html><font color=white size=4>Start: Each small hole will start by containing 4 marbles<br>" +
                "Objective: Whoever has the most marbles in their Mancala (the biggest hole) at the end of the game wins<br><br>" +
                "<b>Game Play:</b><br>" +
                "*    1. The game begins by one player picking up all the marbles from a hole on their side<br>" +
                "*    2. The player then continues to lay down each marble one by one in a counterclockwise direction,<br>        including laying a marble in their own mancala<br>" +
                "*    3. When the player reaches the opponents mancala, they skip it, and continue laying marbles on their side<br>" +
                "*    4. If the last piece dropped is in the players own mancala, they take another turn <br>" +
                "*    5. If the last piece dropped is in the players own side that contains 0 marbles,<br>        they capture each piece in the opposite hole in their own mancala<br>" +
                "*    6. The game ends when an entire row of holes is empty<br>" +
                "*    7. Player with the most holes in their mancala + side wins</font><html>"));
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(next);
        rulesPanel.add(bottomPanel, BorderLayout.SOUTH);

        next.addActionListener(actionEvent -> makeSecondRulesWindow());

        rulesPanel.add(insidePanel, BorderLayout.CENTER);
        frame.add(rulesPanel);
        frame.setVisible(true);
    }

    public void makeSecondRulesWindow() {
        rulesPanel.setVisible(false);
        next.setVisible(false);
        imagePanel = new JPanel(new BorderLayout());
        JPanel insideImage = new JPanel(new GridLayout(1, 1));
        JPanel bottomPanel = new JPanel(new FlowLayout());
        gotIt = new JButton("Got it!");

        try {
            insideImage.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/help screen.png")))));
        } catch (IOException ignored) {
        }
        imagePanel.add(insideImage, BorderLayout.CENTER);
        imagePanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
        insideImage.setBackground(Color.BLACK);
        bottomPanel.add(gotIt);
        imagePanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(imagePanel);
        frame.setVisible(true);
        gotIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gotIt.setVisible(false);
                imagePanel.setVisible(false);
                panel.setVisible(true);
                rules.setVisible(true);
                twoP.setVisible(true);
                vsAI.setVisible(true);
                frame.setVisible(true);
            }
        });
    }

    public void startSinglePlayerGame() {
        rules.setVisible(false);
        twoP.setVisible(false);
        vsAI.setVisible(false);
        panel.setVisible(false);

        chooserOuter = new JPanel(new BorderLayout());
        chooserInner = new JPanel(new FlowLayout());
        meFirst = new JButton("I will go first");
        ai = new JButton("Allow computer to go first");
        JLabel question = new JLabel("<html><font face=Arial color=white size=6> Who will go first? </font></html>", SwingConstants.CENTER);

        chooserInner.setBackground(Color.BLACK);
        chooserOuter.setBackground(Color.BLACK);

        chooserInner.add(ai);
        chooserInner.add(meFirst);
        chooserOuter.add(question, BorderLayout.CENTER);
        chooserOuter.add(chooserInner, BorderLayout.SOUTH);

        meFirst.addActionListener(actionEvent -> makeSinglePlayerGame(false));

        ai.addActionListener(actionEvent -> makeSinglePlayerGame(true));

        frame.add(chooserOuter);
        frame.setVisible(true);
    }

    public void makeSinglePlayerGame(boolean aiFirst) {
        chooserInner.setVisible(false);
        chooserOuter.setVisible(false);
        ai.setVisible(false);
        meFirst.setVisible(false);

        prompt = new JLabel("");
        if (aiFirst) {
            prompt.setText("<html><font face=Arial color=white size=4>Computer has made his move, Player make your turn<font></html>");
        } else {
            prompt.setText("<html><font face=Arial color=white size=4>Go first<font></html>");
        }
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        outerGamePane = new JPanel(new GridLayout(2, 1));
        responsePanel = new JPanel(new FlowLayout());
        JPanel gamePane = new JPanel(new BorderLayout());

        responsePanel.setBackground(Color.BLACK);
        infoPanel.setBackground(Color.BLACK);

        mancalaGui = new MancalaGui(this);
        gamePane.add(mancalaGui, BorderLayout.CENTER);

        infoPanel.add(prompt);
        infoPanel.add(responsePanel);
        gamePane.add(infoPanel, BorderLayout.SOUTH);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);


        response.setFocusable(true);
        response.setEditable(true);
        response.setEnabled(true);
        response.setPreferredSize(new Dimension(40, 20));


        responsePanel.add(submitResponse);
        responsePanel.add(response);
        mancalaGui.startGame();
        frame.add(gamePane);
        frame.setVisible(true);
        MancalaGameLogic.startSinglePlayerGameLogic(mancalaGui, this, aiFirst);
    }

    public void startTwoPlayerGame() {
        rules.setVisible(false);
        twoP.setVisible(false);
        vsAI.setVisible(false);
        panel.setVisible(false);

        prompt = new JLabel("<html><font face=Arial color=white size=4>Player 1 will be Magenta on the bottom and will go first.<br>" +
                "Leftmost circle will be the first hole for Player 1<br>" +
                "Rightmost hole for Orange/ Player 2 the first hole numbered 1</font></html>", SwingConstants.CENTER);
        JButton letsGo = new JButton("Lets Go!");

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        outerGamePane = new JPanel(new GridLayout(2, 1));
        responsePanel = new JPanel(new FlowLayout());
        JPanel gamePane = new JPanel(new BorderLayout());

        responsePanel.setBackground(Color.BLACK);
        infoPanel.setBackground(Color.BLACK);

        mancalaGui = new MancalaGui(this);
        gamePane.add(mancalaGui, BorderLayout.CENTER);

        infoPanel.add(prompt);
        responsePanel.add(letsGo);
        infoPanel.add(responsePanel);
        gamePane.add(infoPanel, BorderLayout.SOUTH);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);


        response.setFocusable(true);
        response.setEditable(true);
        response.setEnabled(true);
        response.setPreferredSize(new Dimension(40, 20));

        letsGo.addActionListener(actionEvent -> {
            prompt.setText("Player 1 make your move");
            letsGo.setVisible(false);
            responsePanel.add(submitResponse);
            responsePanel.add(response);
            mancalaGui.startGame();
            frame.setVisible(true);
            MancalaGameLogic.startTwoPlayerGameLogic(mancalaGui, this);
        });

        frame.add(gamePane);
        frame.setVisible(true);
    }

    public void makeWinningGUI(int[] scores, boolean twoPlayer, boolean aiFirst) {
        JFrame newFrame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        JPanel insidePanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JLabel player1 = new JLabel();
        JLabel player2 = new JLabel();
        JLabel score1 = new JLabel();
        JLabel score2 = new JLabel();
        JButton playAgain = new JButton();
        JButton noThanks = new JButton();
        String p1;
        String p2;
        String header;

        if (twoPlayer) {
            p1 = "<html><font face=Arial color=blue size=6><b> Player 1 </b></font></html>";
            p2 = "<html><font face=Arial color=red size=6><b> Player 2 </b></font></html>";
            if (scores[0] > scores[1]) {
                header = "<html><font face=Arial color=black size=8><b> Player 1 wins! </b></font></html>";
            } else {
                header = "<html><font face=Arial color=black size=8><b> Player 2 wins! </b></font></html>";
            }
        } else {
            if (aiFirst) {
                p1 = "<html><font face=Arial color=blue size=6><b> Artificial Intelligence </b></font></html>";
                p2 = "<html><font face=Arial color=red size=6><b> Player </b></font></html>";
                if (scores[0] > scores[1]) {
                    header = "<html><font face=Arial color=black size=8><b> The computer has won </b></font></html>";
                } else {
                    header = "<html><font face=Arial color=black size=8><b> You Won!! </b></font></html>";
                }
            } else {
                p2 = "<html><font face=Arial color=red size=6><b> Artificial Intelligence </b></font></html>";
                p1 = "<html><font face=Arial color=blue size=6><b> Player </b></font></html>";
                if (scores[0] > scores[1]) {
                    header = "<html><font face=Arial color=black size=8><b> You Won!! </b></font></html>";
                } else {
                    header = "<html><font face=Arial color=black size=8><b> The computer has won </b></font></html>";
                }
            }
        }

        playAgain.addActionListener(actionEvent -> new MenuScreen());
        noThanks.addActionListener(actionEvent -> newFrame.dispose());

        buttonPanel.add(noThanks);
        buttonPanel.add(playAgain);

        player1.setText(p1);
        player2.setText(p2);
        panel.add(new JLabel(header), BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        insidePanel.add(player1);
        insidePanel.add(player2);
        score1.setText(String.format("<html><font face=Arial color=blue size=6><b> %d </b></font></html>", scores[0]));
        score2.setText(String.format("<html><font face=Arial color=red size=6><b> %d </b></font></html>", scores[1]));
        insidePanel.add(score1);
        insidePanel.add(score2);
        panel.add(insidePanel, BorderLayout.CENTER);


        newFrame.add(panel);
        newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newFrame.setVisible(true);
        newFrame.setSize(400,400);
        frame.dispose();

    }

    JButton getSubmitResponse() {
        return submitResponse;
    }

    JTextField getResponse() {
        return response;
    }

    JLabel getPrompt() {
        return prompt;
    }


    public static void main(String[] args) {
        new MenuScreen();
    }
}
