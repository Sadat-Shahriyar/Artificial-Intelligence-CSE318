package com.company;

import java.security.PublicKey;
import java.util.Arrays;

public class Board {
    private int[] player1Bins = new int[6];
    private int[] player2Bins = new int[6];
    private int player1Storage;
    private int player2Storage;

    public int[] getPlayer1Bins() {
        return player1Bins;
    }

    public void setPlayer1Bins(int[] player1Bins) {
        this.player1Bins = player1Bins;
    }

    public int[] getPlayer2Bins() {
        return player2Bins;
    }

    public void setPlayer2Bins(int[] player2Bins) {
        this.player2Bins = player2Bins;
    }

    public int getPlayer1Storage() {
        return player1Storage;
    }

    public void setPlayer1Storage(int player1Storage) {
        this.player1Storage = player1Storage;
    }

    public int getPlayer2Storage() {
        return player2Storage;
    }

    public void setPlayer2Storage(int player2Storage) {
        this.player2Storage = player2Storage;
    }

    public Board(Board board){
        player1Bins = Arrays.copyOf(board.player1Bins, board.player1Bins.length);
        player2Bins = Arrays.copyOf(board.player2Bins, board.player2Bins.length);
        player1Storage = board.player1Storage;
        player2Storage = board.player2Storage;
    }

    public Board(){
        for(int i=0;i<6;i++){
            player1Bins[i] = 4;
            player2Bins[i] = 4;
        }
        player1Storage = 0;
        player2Storage = 0;
    }

    public Board(int[] player1Bins, int[] player2Bins, int player1Storage, int player2Storage){
        this.player1Bins = player1Bins;
        this.player2Bins = player2Bins;
        this.player1Storage = player1Storage;
        this.player2Storage = player2Storage;
    }


    public void printBoard(){
        System.out.println();
        System.out.println("p2: " + getPlayer2Storage());
        for(int i=0; i<getPlayer2Bins().length; i++){
            System.out.print(getPlayer2Bins()[i] + " ");
        }
        System.out.println();
        for(int i=0; i<getPlayer1Bins().length; i++){
            System.out.print(getPlayer1Bins()[i] + " ");
        }
        System.out.println();
        System.out.println("      p1: " + getPlayer1Storage());
        System.out.println();
    }
}

