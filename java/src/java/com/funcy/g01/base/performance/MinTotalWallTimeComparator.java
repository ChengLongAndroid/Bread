package com.funcy.g01.base.performance;

import java.util.Comparator;

public class MinTotalWallTimeComparator implements Comparator<EventInfo> {

    @Override
    public int compare(EventInfo o1, EventInfo o2) {
        if(o1.minTotalWallTime.get() > o2.minTotalWallTime.get()) {
            return -1;
        } else {
            return 1;
        }
    }

}
