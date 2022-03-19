package com.company;

import java.util.Comparator;

public class CompareHammingDistance implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.moves + o1.getHammingDistance() > o2.moves+o2.getHammingDistance()){
            return 1;
        }
        else if(o1.moves + o1.getHammingDistance() < o2.moves+o2.getHammingDistance()){
            return -1;
        }
        return 0;
    }
}
