import java.util.Arrays;

public class ArtificialIntelligence {
    boolean isPlayer1;

    public ArtificialIntelligence() {
    }


    private int getBoardScore(int[] board) {
        if (board[6] > 24) {
            return 1000;
        } else if (board[13] > 24) {
            return -1000;
        }

        int score = 0;
        for (int i = 0; i < 14; i++) {
            if (i < 6) {
                score += board[i];
            } else if (i == 6) {
                score += 0 * board[i];
            } else if (i == 13) {
                score -= board[i];
            } else {
                score -= 0 * board[i];
            }
        }
        return score;
    }

    private boolean canPlay(int[] board, boolean isPlayer1) {
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

    private static int[] makePseudoPlay(int[] board, boolean isPlayer1, int boardSpace) {
        int hand = 0;
        int seedLoc = 0;

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
                return board;
            } else if (board[seedLoc] == 1 && seedLoc < 6) {
                board[6] += board[12 - seedLoc] + 1;
                board[12 - seedLoc] = 0;
                board[seedLoc] = 0;
            }
        } else {
            if (seedLoc == 13) {
                return board;
            } else if (seedLoc > 6 && seedLoc < 13 && board[seedLoc] == 1) {
                board[13] += board[12 - seedLoc] + 1;
                board[seedLoc] = 0;
                board[12 - seedLoc] = 0;
            }
        }
        return board;
    }

    private int getFinalScores(int[] board, boolean isPlayer1) {
        int score1 = 0;
        int score2 = 0;
        if (isPlayer1) {
            for (int i = 0; i < 6; i++) {
                score1 += board[i];
            }
        } else {
            for (int i = 7; i < 13; i++) {
                score2 += board[i];
            }
        }
        if (score1 > 24) {
            return 1000;
        } else {
            return -1000;
        }
    }


   int bestMove(int[] board, boolean isPlayer1) {
        int bestMove = -1;
        if (isPlayer1) {
            int bestVal = -1000;
            for (int i = 0; i < 6; i++) {
                if (board[i] != 0) {
                    int[] newBoard;
                    newBoard = Arrays.copyOf(board, 14);
                    newBoard = makePseudoPlay(newBoard, true, i);
                    int moveVal = minimax(newBoard, 0, false);
                    System.out.println(moveVal);
                    if (moveVal > bestVal) {
                        bestMove = i;
                        bestVal = moveVal;
                    }
                }
            }
        } else {
            int bestVal = 1000;
            for (int i = 7; i < 13; i++) {
                if (board[i] != 0) {
                    int[] newBoard;
                    newBoard = Arrays.copyOf(board, 14);
                    newBoard = makePseudoPlay(newBoard, false, i);
                    int moveVal = minimax(newBoard, 0, true);
                    if (moveVal < bestVal) {
                        bestMove = i;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }


   private int minimax(int[] board, int depth, boolean isMaximizingPlayer) {
        int score = getBoardScore(board);

        if (score == 1000 || score == -1000) {
            return score;
        }
        if (depth == 7) {
            return score;
        }
        if (!canPlay(board, isMaximizingPlayer)) {
            return getFinalScores(board, isMaximizingPlayer);
        }
        if (isMaximizingPlayer) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 6; i++) {
                int[] newBoard;
                newBoard = Arrays.copyOf(board, 14);
                newBoard = makePseudoPlay(newBoard, true, i);
                best = Math.max(best, minimax(newBoard, depth + 1, false));
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 7; i < 13; i++) {
                int[] newBoard;
                newBoard = Arrays.copyOf(board, 14);
                newBoard = makePseudoPlay(newBoard, false, i);
                best = Math.min(best, minimax(newBoard, depth + 1, true));
            }
            return best;
        }
    }
}

