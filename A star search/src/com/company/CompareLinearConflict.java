package com.company;

import java.util.Comparator;

public class CompareLinearConflict implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.moves + o1.getLinearConflict() > o2.moves+o2.getLinearConflict()){
            return 1;
        }
        else if(o1.moves + o1.getLinearConflict() < o2.moves+o2.getLinearConflict()){
            return -1;
        }
        return 0;
    }
}
