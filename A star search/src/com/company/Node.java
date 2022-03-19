package com.company;

import java.util.ArrayList;

public class Node {
    int[][] board;
    int moves;
    Node previousNode;
    String key = "";
    int emptyPosRow;
    int emptyPosColumn;


    public Node(int[][] board, Node previousNode, int moves){
        this.board = board;
        this.previousNode = previousNode;
        this.moves = moves;

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                key = key + board[i][j];
                if(board[i][j] == 0){
                    emptyPosRow = i;
                    emptyPosColumn = j;
                }
            }
        }
    }

    public String getKey(){
        return key;
    }

    private int getInversionCount(){
        int inversionCount = 0;
        for(int i=0;i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                int val = board[i][j];
                for(int k=i; k<board.length; k++){
                    for(int l = 0;l<board[k].length;l++){
                        if(k == i && l<=j){
                            continue;
                        }
                        else if(val > board[k][l] && board[k][l] != 0){
                            inversionCount++;
                        }
                    }
                }
            }
        }
        return inversionCount;
    }

    public boolean checkSolvable(){
        int inversionCount;
        if(board.length % 2 == 1){
            inversionCount = getInversionCount();
            if(inversionCount%2 == 0){
                return true;
            }
        }
        else{
            inversionCount = getInversionCount();
            int emptyRow = 0;
            for(int i=0;i<board.length;i++){
                for(int j=0; j<board[i].length; j++){
                    if(board[i][j] == 0){
                        emptyRow = i;
                        break;
                    }
                }
            }
            if(emptyRow%2 == 0 && inversionCount%2 == 1){
                return true;
            }
            else if(emptyRow%2 == 1 && inversionCount%2 == 0){
                return true;
            }
        }
        return false;
    }

    public int getHammingDistance(){
        int val = 1;
        int hammingDistance = 0;
        for(int i=0;i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] > 0 && board[i][j] != val){
                    hammingDistance++;
                }
                val++;
            }
        }
//        System.out.println(hammingDistance);
        return hammingDistance;
    }

    public int getManhattanDistance(){
        int gridSize = board.length;
        int manhattanDistance = 0;
        for(int i=0;i<board.length;i++){
            for(int j=0; j<board[i].length; j++){
                if(board[i][j] > 0){
                    int actualRow = (board[i][j]-1)/gridSize;
                    int actualColumn = (board[i][j]-1)%gridSize;
                    manhattanDistance = manhattanDistance + Math.abs(actualRow-i) + Math.abs(actualColumn-j);
                }
            }
        }
        return manhattanDistance;
    }

    public int getLinearConflict(){
        int gridSize = board.length;
        int linearConflict = getManhattanDistance();
        int conflictNo = 0;
        for(int i=0;i<board.length; i++){
            int[] valuesAtCorrectRow = new int[board.length];
            int idx = 0;
            for(int j=0;j<board[i].length; j++){
                if(board[i][j] != 0 && (board[i][j]-1)/gridSize == i){
                    valuesAtCorrectRow[idx++] = board[i][j];
                }
            }

            for(int j=0; j<idx; j++){
                int val = valuesAtCorrectRow[j];
                for(int k=j+1; k<idx; k++){
                    if(val > valuesAtCorrectRow[k]){
                        conflictNo++;
                    }
                }
            }
        }

        linearConflict = linearConflict + 2*conflictNo;
        return linearConflict;
    }

    public ArrayList<Node> getAdjacentNodes(){
        ArrayList<Node> nodes = new ArrayList<>();
        int rowIdx = emptyPosRow-1;
        int colIdx = emptyPosColumn;
        if(rowIdx >= 0){
            int[][] nodeArray = getCopyOfBoard();
            nodeArray[emptyPosRow][emptyPosColumn] = nodeArray[rowIdx][colIdx];
            nodeArray[rowIdx][colIdx] = 0;
            Node node1 = new Node(nodeArray, this,this.moves+1);
            if(this.previousNode != null){
                if(!node1.getKey().equalsIgnoreCase(this.previousNode.getKey())){
                    nodes.add(node1);
                }
            }
            else{
                nodes.add(node1);
            }
        }

        rowIdx = emptyPosRow;
        colIdx = emptyPosColumn-1;
        if(colIdx >= 0){
            int[][] nodeArray = getCopyOfBoard();
            nodeArray[emptyPosRow][emptyPosColumn] = nodeArray[rowIdx][colIdx];
            nodeArray[rowIdx][colIdx] = 0;
            Node node2 = new Node(nodeArray, this,this.moves+1);
            if(this.previousNode != null){
                if(!node2.getKey().equalsIgnoreCase(this.previousNode.getKey())){
                    nodes.add(node2);
                }
            }
            else{
                nodes.add(node2);
            }
        }

        rowIdx = emptyPosRow;
        colIdx = emptyPosColumn + 1;
        if(colIdx < board.length){
            int[][] nodeArray = getCopyOfBoard();
            nodeArray[emptyPosRow][emptyPosColumn] = nodeArray[rowIdx][colIdx];
            nodeArray[rowIdx][colIdx] = 0;
            Node node3 = new Node(nodeArray, this,this.moves+1);
            if(this.previousNode != null){
                if(!node3.getKey().equalsIgnoreCase(this.previousNode.getKey())){
                    nodes.add(node3);
                }
            }
            else{
                nodes.add(node3);
            }
        }

        rowIdx = emptyPosRow +1;
        colIdx = emptyPosColumn;
        if(rowIdx < board.length){
            int[][] nodeArray = getCopyOfBoard();
            nodeArray[emptyPosRow][emptyPosColumn] = nodeArray[rowIdx][colIdx];
            nodeArray[rowIdx][colIdx] = 0;
            Node node4 = new Node(nodeArray, this,this.moves+1);
            if(this.previousNode != null){
                if(!node4.getKey().equalsIgnoreCase(this.previousNode.getKey())){
                    nodes.add(node4);
                }
            }
            else{
                nodes.add(node4);
            }
        }
        return nodes;
    }

    private int[][] getCopyOfBoard(){
        int[][] copy = new int[board.length][board.length];
        for(int i=0;i<board.length; i++){
            for(int j=0; j<board.length; j++){
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    public void nodePrint(){
        for(int i=0; i<board.length; i++){
            for(int j=0; j< board.length; j++){
                if(board[i][j] == 0){
                    System.out.print("*    ");
                }
                else
                    System.out.print(board[i][j] + "    ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
