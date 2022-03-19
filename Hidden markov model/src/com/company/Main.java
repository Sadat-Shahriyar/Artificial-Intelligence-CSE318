package com.company;

import javax.sound.midi.Soundbank;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    int n,m,k;
        Scanner scanner = new Scanner(System.in);
//        System.out.print("n: ");
        n = scanner.nextInt();
//        System.out.print("m: ");
        m = scanner.nextInt();
//        System.out.print("k: ");
        k = scanner.nextInt();

        double grid[][] = new double[n][m];
        double initialProbability = 1.0/(n*m-k);

        int gridObstacleRow, gridObstacleColumn;
//        if(k > 0) System.out.println("obstacles positions: ");
        for(int i=0;i<k;i++){
            gridObstacleRow = scanner.nextInt();
            gridObstacleColumn = scanner.nextInt();
            grid[gridObstacleRow][gridObstacleColumn] = -1;
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] != -1){
                    grid[i][j] = initialProbability;
                }
            }
        }

        System.out.println("\n" + "A " + n + "X" + m + " grid where each cell without obstacle has a probability value = " + String.format("%.4f", initialProbability*100));
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        printGrid(grid);

        double cumulativeProbability = 0.9;
        String inp;
        String command[];
        String garbage = scanner.nextLine();
        boolean firstReading = false;
        while(true){
//            System.out.print("Command: ");
            inp = scanner.nextLine();
            command = inp.strip().split(" ");
            if(command[0].equalsIgnoreCase("R")){
                int row = Integer.parseInt(command[1]);
                int column = Integer.parseInt(command[2]);
                int sensorReading = Integer.parseInt(command[3]);

                if(!firstReading){
                    double passageOfTimeProbGrid[][] = new double[n][m];
                    for(int i=0;i<n;i++){
                        for(int j=0;j<m;j++){
                            if(grid[i][j] != -1){
                                double prob = 0.0;
                                boolean adjacent = false;
//                                for(int a=i-1; a <= i+1;a++){
//                                    for(int b=j-1; b <=j+1; b++){
//                                        double getProb = getProbabilityOfCell(grid, a,b,cumulativeProbability,adjacent);
//                                        adjacent = !adjacent;
//                                        if(getProb != -1) prob+=getProb;
//                                    }
//                                }
                                prob = getProbabilityOfCell(grid, i-1, j-1, cumulativeProbability, false) +
                                        getProbabilityOfCell(grid, i-1, j, cumulativeProbability, true)+
                                        getProbabilityOfCell(grid, i-1, j+1, cumulativeProbability, false)+
                                        getProbabilityOfCell(grid, i, j-1, cumulativeProbability, true)+
                                        getProbabilityOfCell(grid, i, j, cumulativeProbability, false)+
                                        getProbabilityOfCell(grid, i, j+1, cumulativeProbability, true)+
                                        getProbabilityOfCell(grid, i+1, j-1, cumulativeProbability, false)+
                                        getProbabilityOfCell(grid, i+1, j, cumulativeProbability, true)+
                                        getProbabilityOfCell(grid, i+1, j+1, cumulativeProbability, false);

//                                System.out.print(prob + "       ");
                                passageOfTimeProbGrid[i][j] = prob;
                            }
                            else{
                                passageOfTimeProbGrid[i][j] = -1;
                            }
                        }
                    }

                    grid = passageOfTimeProbGrid;
                }

                if (sensorReading == 1){
                    for(int i=0;i<n;i++){
                        for(int j=0;j<m;j++){
                            if(i >= row-1 && i<=row+1 && j>=column-1 && j<=column+1){
                                if(grid[i][j] != -1) grid[i][j] = grid[i][j]*0.85;
                            }
                            else{
                                if(grid[i][j] != -1) grid[i][j] = grid[i][j]*0.15;
                            }
                        }
                    }
                }
                else if(sensorReading == 0){
                    for(int i=0;i<n;i++){
                        for(int j=0;j<m;j++){
                            if(i >= row-1 && i<=row+1 && j>=column-1 && j<=column+1){
                                if(grid[i][j] != -1) grid[i][j] = grid[i][j]*0.15;
                            }
                            else{
                                if(grid[i][j] != -1) grid[i][j] = grid[i][j]*0.85;
                            }
                        }
                    }
                }


                double probSum = 0.0;
                for(int i=0; i<n; i++){
                    for(int j=0; j<m; j++){
                        if(grid[i][j] != -1) probSum += grid[i][j];
                    }
                }
                for(int i=0; i<n; i++){
                    for(int j=0; j<m; j++){
                        if(grid[i][j] != -1) grid[i][j] = grid[i][j]/probSum;
                    }
                }

                printGrid(grid);

                firstReading = false;
            }

            else if(command[0].equalsIgnoreCase("C")){
                int row = 0;
                int column = 0;
                double maxVal = grid[0][0];
                for(int i=0; i<grid.length; i++){
                    for(int j=0; j<grid[0].length; j++){
                        if(maxVal == -1 && grid[i][j] != -1) {
                            maxVal = grid[i][j];
                            row = i;
                            column = j;
                        }
                        if(grid[i][j] > maxVal){
                            maxVal = grid[i][j];
                            row = i;
                            column = j;
                        }
                    }
                }
                System.out.print("Most probable position: ");
                System.out.println("(" + row + "," + column + ")");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            }

            else if(command[0].equalsIgnoreCase("Q")){
                System.out.println("Bye casper");
                break;
            }
            else System.out.println("Unknown command");
        }
    }

    public static void printGrid(double grid[][]){
        for(int i=0;i<grid.length; i++){
            for(int j=0;j<grid[i].length; j++){
                if(grid[i][j] == -1){
                    System.out.print(String.format("%.4f", 0.0) + "\t\t");
                }
                else{
                    if(grid[i][j] * 100.0 >= 10.0) System.out.print(String.format("%.4f", grid[i][j] * 100.0) + "\t\t");
                    else System.out.print(String.format("%.4f", grid[i][j] * 100.0) + " \t\t");
                }
//                System.out.print(grid[i][j] + "         ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static double getProbabilityOfCell(double grid[][], int row, int column, double cumulativeProb, boolean adjacentCell){
        if(row >= 0 && row < grid.length && column >= 0 && column < grid[0].length){
            // the provided row and column is within the grid
            if(grid[row][column] == -1){
                //there is an obstacle in this position
                return 0;
            }
            if(adjacentCell){
                int gridsAvailable = 0;
                if(row-1 >= 0 && grid[row-1][column] != -1) gridsAvailable++;
                if(row+1 < grid.length && grid[row+1][column] != -1) gridsAvailable++;
                if(column-1 >= 0 && grid[row][column-1] != -1) gridsAvailable++;
                if(column+1 < grid[0].length && grid[row][column+1] != -1) gridsAvailable++;
                if(gridsAvailable > 0) return grid[row][column]*cumulativeProb/gridsAvailable;
                else return 0;
            }
            else {
                int gridsAvailable = 1;
                if(row-1 >= 0 && column-1>=0 && grid[row-1][column-1] != -1) gridsAvailable++;
                if(row-1 >= 0 && column+1<grid[0].length && grid[row-1][column+1] != -1) gridsAvailable++;
                if(row+1 < grid.length && column-1 >= 0 && grid[row+1][column-1] != -1) gridsAvailable++;
                if(row+1 < grid.length && column+1 < grid[0].length && grid[row+1][column+1] != -1) gridsAvailable++;
                return (1.0-cumulativeProb)*grid[row][column]/gridsAvailable;
            }
        }
        else{
            return 0;
        }
    }
}
