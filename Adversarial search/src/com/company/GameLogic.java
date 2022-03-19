package com.company;

public class GameLogic {
    Board currentBoard;
    int[] gameWinner = new int[2];

    public GameLogic(Board currentBoard){
        this.currentBoard = currentBoard;
        gameWinner[0] = gameWinner[1] = 0;
    }
    public Board getCurrentBoard() {
        return currentBoard;
    }
    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public boolean applyMove(int moveNo, int playerNo){
        boolean extraMoveEarned = false;
        if(playerNo == 1){
            int marbles = currentBoard.getPlayer1Bins()[moveNo-1];
            for(int i=7-moveNo; i<=48; i+=13){
                if(marbles == i){
                    extraMoveEarned = true;
                    break;
                }
            }
        }
        else if(playerNo == 2){
            int marbles = currentBoard.getPlayer2Bins()[6-moveNo];
            for(int i=7-moveNo; i<=48; i+=13){
                if(marbles == i){
                    extraMoveEarned = true;
                    break;
                }
            }
        }
        this.currentBoard = move(this.currentBoard,moveNo, playerNo);

        return extraMoveEarned;
    }

    public int[] gameEnd(){
        int[] player1Bins = currentBoard.getPlayer1Bins();
        int[] player2Bins = currentBoard.getPlayer2Bins();
        int player1Storage = currentBoard.getPlayer1Storage();
        int player2Storage = currentBoard.getPlayer2Storage();

        int binsPlayer1 = 0;
        for(int i=0;i<player1Bins.length;i++){
            binsPlayer1 += player1Bins[i];
        }

        int binsPlayer2 = 0;
        for(int i=0;i<player2Bins.length;i++){
            binsPlayer2 += player2Bins[i];
        }

        if(binsPlayer1 == 0 || binsPlayer2 == 0){
            if(binsPlayer1 + player1Storage > binsPlayer2 + player2Storage){
                gameWinner[0] = 1;
            }
            else if(binsPlayer1 + player1Storage < binsPlayer2 + player2Storage){
                gameWinner[1] = 1;
            }
            else{
                gameWinner[0] = gameWinner[1] = 1;
            }
        }
        return gameWinner;
    }

    public static Board move(Board board, int moveNo, int playerNo){
        Board board1 = new Board(board);
        int[] player1Bins = board1.getPlayer1Bins();
        int[] player2Bins = board1.getPlayer2Bins();
        int player1Storage = board1.getPlayer1Storage();
        int player2Storage = board1.getPlayer2Storage();

        if(playerNo == 1) {
            int marbles = player1Bins[moveNo-1];
            player1Bins[moveNo-1] = 0;
            int finalMarbleIdx = -1;
            boolean finalMarbleInPlayer1Bin = false;

            boolean firstTime = true;
            while(marbles > 0) {
                if(firstTime){
                    for (int i = moveNo; i < 6; i++) {
                        if (marbles > 0) {
                            player1Bins[i]++;
                            finalMarbleIdx = i;
                            finalMarbleInPlayer1Bin = true;
                            marbles--;
                        } else {
                            break;
                        }
                    }
                    firstTime = false;
                }
                else{
                    for (int i = 0; i < 6; i++) {
                        if (marbles > 0) {
                            player1Bins[i]++;
                            finalMarbleIdx = i;
                            finalMarbleInPlayer1Bin = true;
                            marbles--;
                        } else {
                            break;
                        }
                    }
                }

                if (marbles > 0) {
                    player1Storage++;
                    marbles--;
                    finalMarbleInPlayer1Bin = false;
                }

                for (int i = 5; i >= 0; i--) {
                    if (marbles > 0) {
                        player2Bins[i]++;
                        marbles--;
                        finalMarbleInPlayer1Bin = false;
                    }
                    else break;
                }
            }

            if(finalMarbleIdx != -1 && finalMarbleInPlayer1Bin){
                if(player1Bins[finalMarbleIdx] == 1 && player2Bins[finalMarbleIdx] > 0){
                    player1Storage += player1Bins[finalMarbleIdx];
                    player1Storage += player2Bins[finalMarbleIdx];
                    player1Bins[finalMarbleIdx] = 0;
                    player2Bins[finalMarbleIdx] = 0;
                }
            }
        }
        else if(playerNo == 2){
            int marbles = player2Bins[6-moveNo];
            player2Bins[6-moveNo] = 0;

            int finalMarbleIdx = -1;
            boolean finalMarbleInPlayer2Bin = false;
            boolean firstTime = true;

            while(marbles > 0) {
                if(firstTime){
                    for (int i = 6-moveNo-1; i >= 0; i--) {
                        if (marbles > 0) {
                            player2Bins[i]++;
                            finalMarbleIdx = i;
                            finalMarbleInPlayer2Bin = true;
                            marbles--;
                        } else {
                            break;
                        }
                    }
                    firstTime = false;
                }
                else{
                    for (int i = 5; i >= 0; i--) {
                        if (marbles > 0) {
                            player2Bins[i]++;
                            finalMarbleIdx = i;
                            finalMarbleInPlayer2Bin = true;
                            marbles--;
                        } else {
                            break;
                        }
                    }
                }

                if (marbles > 0) {
                    player2Storage++;
                    marbles--;
                    finalMarbleInPlayer2Bin = false;
                }
                for (int i = 0; i < 6; i++) {
                    if (marbles > 0) {
                        player1Bins[i]++;
                        marbles--;
                        finalMarbleInPlayer2Bin = false;
                    }
                    else break;
                }

            }
            if(finalMarbleIdx != -1 && finalMarbleInPlayer2Bin){
                if(player2Bins[finalMarbleIdx] == 1 && player1Bins[finalMarbleIdx] > 0){
                    player2Storage += player2Bins[finalMarbleIdx];
                    player2Storage += player1Bins[finalMarbleIdx];
                    player2Bins[finalMarbleIdx] = 0;
                    player1Bins[finalMarbleIdx] = 0;
                }
            }
        }
        Board board2 = new Board(player1Bins,player2Bins,player1Storage, player2Storage);
        return board2;
    }

    public void printBoard(){
        currentBoard.printBoard();

    }
}
