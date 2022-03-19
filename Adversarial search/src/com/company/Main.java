package com.company;

import java.util.Scanner;

public class Main {

    public static void main (String[] args)
    {
        Board board = new Board();
        GameLogic gameLogic = new GameLogic(board);
        gameLogic.printBoard();

        int moveNo;
        int playerNo = 1;
        System.out.println("Player " + playerNo);
        System.out.print("Move: ");
        Scanner scanner = new Scanner(System.in);
        int maxDepth = 10;

//        moveNo = AIAgent.findMove(board, 0, maxDepth, playerNo);
//        System.out.println(moveNo);
        moveNo = scanner.nextInt();
        System.out.println(moveNo);

        while(true){
            boolean extraMoveEarned = gameLogic.applyMove(moveNo, playerNo);
            gameLogic.printBoard();
            int[] gameWinner = gameLogic.gameEnd();
            if(gameWinner[0] == 1 && gameWinner[1] == 1){
                System.out.println("Draw");
                break;
            }
            else if(gameWinner[0] == 1) {
                System.out.println("Player 1 won");
                break;
            }
            else if (gameWinner[1] == 1){
                System.out.println("Player 2 won");
                break;
            }
            if(extraMoveEarned){
                System.out.println("Player " + playerNo);
                System.out.print("Move: ");
                if(playerNo == 1){
                    moveNo = scanner.nextInt();
                }
                else{
                    moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
                }
//                moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
//                moveNo = scanner.nextInt();
                System.out.println(moveNo);

            }
            else{
                if(playerNo == 1) playerNo = 2;
                else playerNo = 1;
                System.out.println("Player " + playerNo);
                System.out.print("Move: ");
                if(playerNo == 1){
                    moveNo = scanner.nextInt();
                }
                else{
                    moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
                }
//                moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);

//                moveNo = scanner.nextInt();
                System.out.println(moveNo);
            }
        }

    }

    public static void reportGen (String[] args)
    {
        for(int d = 4; d<11; d++){
            int playerOneWon = 0;
            int playerTwoWon = 0;
            int draw = 0;
            for(int i=0;i<100; i++){
                Board board = new Board();
                GameLogic gameLogic = new GameLogic(board);
//            gameLogic.printBoard();

                int moveNo;
                int playerNo = 1;
//            System.out.println("Player " + playerNo);
//            System.out.print("Move: ");
                Scanner scanner = new Scanner(System.in);
                int maxDepth = d;

                moveNo = AIAgent.findMove(board, 0, maxDepth, playerNo);
//            System.out.println(moveNo);
//        moveNo = scanner.nextInt();
//        System.out.println(moveNo);

                while(true){
                    boolean extraMoveEarned = gameLogic.applyMove(moveNo, playerNo);
//                gameLogic.printBoard();
                    int[] gameWinner = gameLogic.gameEnd();
                    if(gameWinner[0] == 1 && gameWinner[1] == 1){
//                    System.out.println("Draw");
                        draw++;
                        break;
                    }
                    else if(gameWinner[0] == 1) {
//                    System.out.println("Player 1 won");
                        playerOneWon++;
                        break;
                    }
                    else if (gameWinner[1] == 1){
//                    System.out.println("Player 2 won");
                        playerTwoWon++;
                        break;
                    }
                    if(extraMoveEarned){
//                    System.out.println("Player " + playerNo);
//                    System.out.print("Move: ");
//                if(playerNo == 1){
//                    moveNo = scanner.nextInt();
//                }
//                else{
//                    moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
//                }
                        moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
//                moveNo = scanner.nextInt();
//                    System.out.println(moveNo);

                    }
                    else{
                        if(playerNo == 1) playerNo = 2;
                        else playerNo = 1;
//                    System.out.println("Player " + playerNo);
//                    System.out.print("Move: ");
//                if(playerNo == 1){
//                    moveNo = scanner.nextInt();
//                }
//                else{
//                    moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);
//                }
                        moveNo = AIAgent.findMove(gameLogic.getCurrentBoard(), 0, maxDepth, playerNo);

//                moveNo = scanner.nextInt();
//                    System.out.println(moveNo);
                    }
                }
            }

            System.out.println("After 100 iterations for depth "+ d + " :");
            System.out.println("Player 1 won " + playerOneWon + " times");
            System.out.println("Player 2 won " + playerTwoWon + " times");
            System.out.println("Draw " + draw + " times" );
        }

    }
}


