package com.company;

//public class Node{
//    Board currentBoard;
//    Node parentNode;
//    int parentNodeMove;
//    Node[] children;

//    public Node(Board b, Node parentNode, int parentNodeMove){
//        this.currentBoard = new Board(b);
//        this.parentNode = parentNode;
//        this.parentNodeMove = parentNodeMove;
//    }
//}



import java.util.ArrayList;
import java.util.Collections;

public class Node{
    Board currentBoard;
    Node[] children;
    int parentNodeMove;
    Node parentNode;
    int optimalMove = 1;
    boolean extraMoveEarned = false;

    public Node(Board board, int parentNodeMove, Node parentNode){
        currentBoard = board;
        this.parentNodeMove = parentNodeMove;
        this.parentNode = parentNode;
    }

    public boolean checkNodeEndGame(){
        int player1[] = this.currentBoard.getPlayer1Bins();
        int sum = 0;
        for(int i=0; i<player1.length; i++){
            sum += player1[i];
        }
        if(sum == 0) return true;

        int player2[] = this.currentBoard.getPlayer2Bins();
        sum = 0;
        for(int i=0;i<player2.length; i++){
            sum+= player2[i];
        }
        if(sum == 0) return true;

        return false;
    }


    public void buildChildren(int playerNo){
//        Board currentBoard = new Board(this.currentBoard);
        if(playerNo == 1){
            int totalChildrenNo = 0;
            ArrayList<Integer> validMoves = new ArrayList<>();
            for(int i=0; i<currentBoard.getPlayer1Bins().length; i++){
                if(currentBoard.getPlayer1Bins()[i] > 0){
                    validMoves.add(i+1);
                }
            }
            Collections.shuffle(validMoves);
            children = new Node[validMoves.size()];
            int idx = 0;
            for(Integer validMove : validMoves){
                boolean childExtraMove = false;
                int marbles = currentBoard.getPlayer1Bins()[validMove-1];
                for(int i=7-validMove; i<=48; i+=13){
                    if(marbles == i){
                        childExtraMove = true;
                        break;
                    }
                }
//                children[idx++] = new Node(GameLogic.move(new Board(currentBoard),validMove, playerNo), validMove, this );
                children[idx] = new Node(GameLogic.move(new Board(currentBoard),validMove, playerNo), validMove, this );
                children[idx].extraMoveEarned = childExtraMove;
                idx++;
            }
            validMoves.clear();
        }
        else if(playerNo == 2){
            int totalChildrenNo = 0;
            ArrayList<Integer> validMoves = new ArrayList<>();
            for(int i=0; i<currentBoard.getPlayer2Bins().length; i++){
                if(currentBoard.getPlayer2Bins()[i] > 0){
//                    totalChildrenNo++;
                    validMoves.add(6-i);
                }
            }
            Collections.shuffle(validMoves);
            children = new Node[validMoves.size()];
            int idx = 0;
            for(Integer validMove: validMoves){
                boolean childExtraMove = false;
                int marbles = currentBoard.getPlayer2Bins()[6-validMove];
                for(int i=7-validMove; i<=48; i+=13){
                    if(marbles == i){
                        childExtraMove = true;
                        break;
                    }
                }
                children[idx] = new Node(GameLogic.move(new Board(currentBoard), validMove, playerNo),  validMove, this);
                children[idx].extraMoveEarned = childExtraMove;
                idx++;
//                children[idx++] = new Node(GameLogic.move(new Board(currentBoard), validMove, playerNo),  validMove, this);
            }
            validMoves.clear();
        }
    }

    public int evalFuncOne(int playerNo){  //(stones_in_my_storage – stones_in_opponents_storage)
        if(playerNo == 1){
            return currentBoard.getPlayer1Storage() - currentBoard.getPlayer2Storage();
        }
        else{
            return currentBoard.getPlayer2Storage() - currentBoard.getPlayer1Storage();
        }
    }

    public int evalFuncTwo(int playerNo){  //W1 * (stones_in_my_storage – stones_in_opponents_storage) + W2 * (stones_on_my_side –
                                            // stones_on_opponents_side)
        if(playerNo == 1){
            int sumStonePlayer = 0;
            int player1[] = currentBoard.getPlayer1Bins();
            for(int i=0;i<player1.length; i++){
                sumStonePlayer += player1[i];
            }

            int sumStoneOpponent = 0;
            int player2[] = currentBoard.getPlayer2Bins();
            for(int i=0;i<player2.length; i++){
                sumStoneOpponent+= player2[i];
            }

            return (int) (0.5*(currentBoard.getPlayer1Storage() - currentBoard.getPlayer2Storage()) + 0.5*(sumStonePlayer-sumStoneOpponent));
        }
        else{
            int sumStonePlayer = 0;
            int player2[] = currentBoard.getPlayer2Bins();
            for(int i=0;i<player2.length; i++){
                sumStonePlayer+= player2[i];
            }

            int sumStoneOpponent = 0;
            int player1[] = currentBoard.getPlayer1Bins();
            for(int i=0;i<player1.length; i++){
                sumStonePlayer += player1[i];
            }

            return (int) (0.5*(currentBoard.getPlayer2Storage() - currentBoard.getPlayer1Storage()) + 0.5*(sumStonePlayer-sumStoneOpponent));
        }
    }

    public int evalFuncThree(int playerNo){// W1 * (stones_in_my_storage – stones_in_opponents_storage) + W2 * (stones_on_my_side –
                                            //stones_on_opponents_side) + W3 * (additional_move_earned)
        if(playerNo == 1){
            int sumStonePlayer = 0;
            int player1[] = currentBoard.getPlayer1Bins();
            for(int i=0;i<player1.length; i++){
                sumStonePlayer += player1[i];
            }

            int sumStoneOpponent = 0;
            int player2[] = currentBoard.getPlayer2Bins();
            for(int i=0;i<player2.length; i++){
                sumStoneOpponent+= player2[i];
            }

            int extraMoves = 0;

            for(int j=0; j<currentBoard.getPlayer1Bins().length; j++){
                boolean extra = false;
                int moveNo = j+1;
                int marbles = currentBoard.getPlayer1Bins()[moveNo-1];
                for(int i=7-moveNo; i<=48; i+=13){
                    if(marbles == i){
                        extra = true;
                        break;
                    }
                }
                if(extra) extraMoves++;
            }

            return (int)(0.4*(currentBoard.getPlayer1Storage() - currentBoard.getPlayer2Storage()) + 0.4*(sumStonePlayer-sumStoneOpponent) + 0.2*extraMoves);

        }
        else{
            int sumStonePlayer = 0;
            int player2[] = currentBoard.getPlayer2Bins();
            for(int i=0;i<player2.length; i++){
                sumStonePlayer+= player2[i];
            }

            int sumStoneOpponent = 0;
            int player1[] = currentBoard.getPlayer1Bins();
            for(int i=0;i<player1.length; i++){
                sumStonePlayer += player1[i];
            }

            int extraMoves = 0;

            for(int j=0; j<currentBoard.getPlayer2Bins().length; j++){
                boolean extra = false;
                int moveNo = j+1;
                int marbles = currentBoard.getPlayer2Bins()[6-moveNo];
                for(int i=7-moveNo; i<=48; i+=13){
                    if(marbles == i){
                        extra = true;
                        break;
                    }
                }
                if(extra) extraMoves++;
            }

            return (int)(0.4*(currentBoard.getPlayer2Storage() - currentBoard.getPlayer1Storage()) + 0.4*(sumStonePlayer-sumStoneOpponent) + 0.2*extraMoves);
        }
    }

    public int evalFuncFour(int playerNo){ //Number of stones close to my storage - Number of stones close to opponents storage
        if(playerNo == 1){
            int player1[] = currentBoard.getPlayer1Bins();
            int totalMarbles = 0;
            for(int i=0; i<player1.length; i++){
                int marble = player1[i];
                int moveAvailable = 6-(i+1);
                int marblesInMySide = marble-moveAvailable;
                if(marblesInMySide<0){
                    totalMarbles+= marble;
                }
                else{
                    totalMarbles += marblesInMySide;
                }
            }

            return totalMarbles;
        }
        else{
            int player2[] = currentBoard.getPlayer2Bins();
            int totalMarbles = 0;
            for(int i=5;i>=0;i--){
                int marble = player2[i];
                int moveAvailable = i+1;
                int marblesInMySide = marble-moveAvailable;
                if(marblesInMySide<0){
                    totalMarbles += marble;
                }
                else {
                    totalMarbles+= marblesInMySide;
                }
            }

            return totalMarbles;
        }
    }

    public int evalFuncFive(int playerNo){
        return (int)(0.5*evalFuncFour(playerNo) + 0.5*evalFuncTwo(playerNo));
    }

    public int evalFuncSix(int playerNo){
        return (int)(0.5*evalFuncFour(playerNo) + 0.5*evalFuncThree(playerNo));
    }
}
