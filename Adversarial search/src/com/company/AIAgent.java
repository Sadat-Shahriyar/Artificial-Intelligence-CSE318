package com.company;

public class AIAgent {
    public static int bestMove;
    public static int findMove(Board board, int depth, int maxDepth, int playerNo){
        bestMove = 0;
        Node node = new Node(new Board(board),  0, null);
        int move = alphaBeta(node,depth, maxDepth,Integer.MIN_VALUE, Integer.MAX_VALUE, true, playerNo);
//        System.out.println(node.optimalMove);
//        int mv = getValidMove(node.currentBoard,playerNo,node.optimalMove);
        return node.optimalMove;
//        return mv;
//        return bestMove;
    }
    public static int alphaBeta(Node node, int depth,int maxDepth, int alpha, int beta, boolean maximizingPlayer, int playerNo){
        if(depth == maxDepth || node.checkNodeEndGame()){
            int returnVal = node.evalFuncSix(playerNo);
            return  returnVal;
        }

        if(maximizingPlayer){
            int bestVal = Integer.MIN_VALUE;
            node.buildChildren(playerNo);
            int value;
            for(Node child : node.children){
                if(child.extraMoveEarned){
                    value = alphaBeta(child, depth+1, maxDepth, alpha, beta, true,playerNo);
                }
                else{
                    value = alphaBeta(child, depth+1, maxDepth, alpha, beta, false,3-playerNo);
                }
                if(value > bestVal){
                    if(child.parentNode != null)
                        child.parentNode.optimalMove = child.parentNodeMove;
                }
                if(bestVal < value){
                    bestVal = value;
                }
                if(alpha < bestVal){
                    alpha = bestVal;

                }
                if(beta <= alpha){
                    break;
                }
            }
            return bestVal;
        }
        else{
            int bestVal = Integer.MAX_VALUE;
            node.buildChildren(playerNo);
            int value;
            for(Node child : node.children){
                if(child.extraMoveEarned){
                    value = alphaBeta(child, depth+1, maxDepth, alpha,beta, false, playerNo);
                }
                else{
                    value = alphaBeta(child, depth+1, maxDepth, alpha,beta, true, 3-playerNo);
                }

                if(value < bestVal){
                    if(child.parentNode != null)
                        child.parentNode.optimalMove = child.parentNodeMove;
                }
                if(bestVal > value){
                    bestVal = value;
                    if(depth == 1) bestMove = child.parentNodeMove;
                }
                if(beta > bestVal){
                    beta = bestVal;
                }
                if(beta <= alpha){
                    break;
                }
            }
            return bestVal;
        }
    }

    public static int getValidMove(Board b, int player, int move){
        Board board = new Board(b);
        if(player == 1){
            int player1Bins[] = board.getPlayer1Bins();
            if(player1Bins[move-1] > 0){
                return move;
            }
            else{
                for(int i=0;i<player1Bins.length; i++){
                    if(player1Bins[i] > 0){
                        return i+1;
                    }
                }
            }
        }
        else{
            int player2Bins[] = board.getPlayer2Bins();
            if(player2Bins[6-move] > 0){
                return move;
            }
            else{
                for(int i=0;i<player2Bins.length;i++){
                    if(player2Bins[i] > 0){
                        return 6-i;
                    }
                }
            }
        }
        return move;
    }
}






















