package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter grid size: ");
        Scanner scanner = new Scanner(System.in);
        int gridSize = scanner.nextInt();
        int[][] board = new int[gridSize][gridSize];
        System.out.println("Enter the initial board position: ");
        String inp;
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                inp = scanner.next();
                if(inp.equalsIgnoreCase("*")){
                    board[i][j] = 0;
                }
                else{
                    board[i][j] = Integer.parseInt(inp);
                }
            }
        }

        Node initialNode = new Node(board, null, 0);

        HashMap<String, Node> closedList = new HashMap<>();
        PriorityQueue<Node> manhattanPQ = new PriorityQueue<>(5, new CompareManhattanDistance());
        PriorityQueue<Node> hammingPQ = new PriorityQueue<>(5, new CompareHammingDistance());
        PriorityQueue<Node> linearConflictPQ = new PriorityQueue<>(5, new CompareLinearConflict());

        if(initialNode.checkSolvable()){
            System.out.println("Solvable");
            if(gridSize == 3){
                getSolution(hammingPQ, initialNode,closedList, "Hamming distance", "123456780");
                closedList.clear();
                getSolution(manhattanPQ, initialNode, closedList, "Manhattan distance","123456780");
                closedList.clear();
                getSolution(linearConflictPQ, initialNode, closedList, "Linear conflict","123456780");
            }
            else if(gridSize == 4){
//                getSolution(hammingPQ, initialNode,closedList, "Hamming distance", "1234567891011121314150");
//                closedList.clear();
                getSolution(manhattanPQ, initialNode, closedList, "Manhattan distance","1234567891011121314150");
                closedList.clear();
                getSolution(linearConflictPQ, initialNode, closedList, "Linear conflict","1234567891011121314150");
            }
        }
        else{
            System.out.println("Not solvable");
        }
    }

    private static void getSolution(PriorityQueue<Node> openList, Node initialNode,HashMap<String, Node> closedList, String method, String solutionString){
        openList.add(initialNode);
        Node node;
        int exploredNodeCount = 1;  // it starts from 1 because we pushed initialNode to the PQ in the beginning.
        while(true){
            node = openList.poll();
            closedList.put(node.getKey(), node);
            if(node.getKey().equalsIgnoreCase(solutionString)){
                break;
            }
            ArrayList<Node> adjacentNodes = node.getAdjacentNodes();
            if(adjacentNodes != null){
                for(Node node1 : adjacentNodes){
                    if(!closedList.containsKey(node1.getKey())){
                        openList.add(node1);
                        exploredNodeCount++;
                    }
                }
            }

        }
        ArrayList<Node> pathNodes = new ArrayList<>();
        int stepCount = 0;
        while(node != null){
            pathNodes.add(node);
            stepCount++;
            node = node.previousNode;
        }

        System.out.println("Number of explored nodes using " + method + " : " + exploredNodeCount);
        System.out.println("Number of expanded node using " + method + " : " + closedList.size());
        System.out.println("Optimal step number using " + method + " : " + stepCount);
        int cnt = 1;
        for(int i = pathNodes.size()-1; i>=0; i--){
            System.out.println("Step " + cnt++ + ": ");
            pathNodes.get(i).nodePrint();
        }
        System.out.println();
    }
}

