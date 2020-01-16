import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class MancalaGameLogic {
    private static int[] board;
    private static boolean isWinner;
    private static MancalaGui g;
    private static MenuScreen menuScreen;
    private static String resp;
    private static boolean turn;
    private static boolean aiTurn;
    private static int space;
    private static ArtificialIntelligence ai;

    public static void main(String[] args) {
        //prompt user for gamemode option
    }

    public static void startSinglePlayerGameLogic(MancalaGui game, MenuScreen menu, boolean aiFirst) {
        g = game;
        menuScreen = menu;
        ai = new ArtificialIntelligence();
        isWinner = false;
        turn = true;
        aiTurn = false;

        if (aiFirst) {
            makePlay(g.getWholeBoard(), turn, ai.bestMove(g.getWholeBoard(), turn));
        }

        menuScreen.getSubmitResponse().addActionListener(actionEvent -> {
            int move = -1;
            resp = menuScreen.getResponse().getText();
            space = checkSpace(resp, g.getWholeBoard(), turn);
            if (space >= 0 ) {
                makePlay(g.getWholeBoard(), turn, space);
                menuScreen.getPrompt().setText("<html><font face=Arial color=white size=4>Landed in own mancala, take another turn<font></html>");
                while (aiFirst == turn) {
                    move = ai.bestMove(g.getWholeBoard(), turn);
                    try {
                        makePlay(g.getWholeBoard(), turn, move);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        menuScreen.makeWinningGUI(g.getWholeBoard(), true, aiFirst);
                        return;
                    }
                    if (move > 13) {
                        move -= 6;
                    } else {
                        move++;
                    }
                    menuScreen.getPrompt().setText(String.format("<html><font face=Arial color=white size=4>Computer has made his move at space %d, Player make your turn<font></html>", move));
                }

                try {
                    g.closeBoardInfoGUI();
                } catch (NullPointerException ignored) {
                }
            } else {
                System.out.println(space);
                g.makeBoardInfoGUI(g.getWholeBoard());
            }

            menuScreen.getResponse().setText("");
            menuScreen.getResponse().requestFocus();
            if (!checkPlayable(g.getWholeBoard())) {
                menuScreen.makeWinningGUI(getScores(g.getWholeBoard()), true, aiFirst);
                return;
            }
        });
    }

    public static void startTwoPlayerGameLogic(MancalaGui game, MenuScreen menu) {
        g = game;
        menuScreen = menu;
        turn = true;
        menuScreen.getSubmitResponse().addActionListener(actionEvent -> {
            resp = menuScreen.getResponse().getText();
            space = checkSpace(resp, g.getWholeBoard(), turn);
            if (space >= 0 ) {
                makePlay(g.getWholeBoard(), turn, space);
                try {
                    g.closeBoardInfoGUI();
                } catch (NullPointerException ignored) {

                }
            } else {
                System.out.println(space);
                g.makeBoardInfoGUI(g.getWholeBoard());
            }
            if (turn) {
                menuScreen.getPrompt().setText("<html><font face=Arial color=white size=4>Player 1 will continue<font></html>");
            } else {
                menuScreen.getPrompt().setText("<html><font face=Arial color=white size=4>Player 2's turn<font></html>");
            }
            menuScreen.getResponse().setText("");
            menuScreen.getResponse().requestFocus();
            if (!checkPlayable(g.getWholeBoard())) {
                menuScreen.makeWinningGUI(getScores(g.getWholeBoard()), true, false);
            }

        });

    }

    private static int[] makePlay(int[] board, boolean isPlayer1, int boardSpace) {
        int hand = 0;
        int seedLoc = 0;

        g.moveSpaces(boardSpace, isPlayer1);
        hand = board[boardSpace];
        board[boardSpace] = 0;
        for (int i = 1; i < hand + 1; i++) {
            seedLoc = (boardSpace + i) % 14;
            if (seedLoc != boardSpace) {
                if (isPlayer1) {
                    if (seedLoc != 13) {
                        board[seedLoc]++;
                    } else {
                        hand++;
                    }
                } else {
                    if (seedLoc != 6) {
                        board[seedLoc]++;
                    } else {
                        hand++;
                    }
                }
            } else {
                hand++;
            }
        }
        if (isPlayer1) {
            if (seedLoc == 6) {
                printBoard(board);
                turn = true;
                return board;
            } else if (board[seedLoc] == 1 && seedLoc < 6) {
                board[6] += board[12 - seedLoc] + 1;
                board[12 - seedLoc] = 0;
                board[seedLoc] = 0;
                g.specialRuleExec(seedLoc, 12 - seedLoc, true);
                turn = false;
            }
            turn = false;

        } else {
            if (seedLoc == 13) {
                printBoard(board);
                turn = false;
                return board;
            } else if (seedLoc > 6 && seedLoc < 13 && board[seedLoc] == 1) {
                board[13] += board[12 - seedLoc] + 1;
                board[seedLoc] = 0;
                board[12 - seedLoc] = 0;
                g.specialRuleExec(seedLoc, 12 - seedLoc, false);
                turn = true;
            }
            turn = true;
        }
        return board;
    }

    private static boolean checkPlayable(int[] board) {
        boolean playable = false;
        for (int i = 0; i < 6; i++) {
            if (board[i] != 0) {
                playable = true;
                break;
            }
        }
        if (!playable) {
            return false;
        }
        for (int j = 7; j < 14 ; j++) {
            if (board[j] != 0) {
                return true;
            }
        }
        return false;
    }

    private static int[] getScores(int[] board) {
        int player1 = 0, player2 = 0;

        for (int i = 0; i < 14; i++) {
            if (i < 7) {
                player1 += board[i];
            } else {
                player2 += board[i];
            }
        }
        return new int[]{player1, player2};
    }
    private static void printBoard(int[] board){
        for (int i = 13; i > 6; i--) {
            System.out.print(board[i]);
        }
        System.out.println();
        for (int i = 0; i < 7; i++) {
            System.out.print(board[i]);
        }
        System.out.println();
    }

    public static int checkSpace(String sp, int[] board, boolean isPLayer1) {
        try {
            Objects.requireNonNull(sp);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Thanks for playing!!", "End Screen", JOptionPane.INFORMATION_MESSAGE);
            System.exit(69);
        }
        int space = 0;
        try {
            space = Integer.parseInt(sp);
        } catch (NumberFormatException e) {
            System.out.println("hi");
            return -2;
        }
        if (isPLayer1) {
            space--;
             try {
                 if (board[space] != 0 && space < 6) {
                     return space;
                 } else {
                     return -2;
                 }
             } catch (ArrayIndexOutOfBoundsException e){
                 return -2;
            }
        } else {
            space += 6;
            try {
                if (board[space] != 0 && space > 6 && space < 13 ){
                    return space;
                } else {
                    return -2;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return -2;
            }
        }
    }
}
